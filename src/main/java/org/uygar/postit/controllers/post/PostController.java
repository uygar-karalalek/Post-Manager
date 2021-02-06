package org.uygar.postit.controllers.post;

import javafx.fxml.FXML;
import javafx.geometry.Dimension2D;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.apache.commons.lang.StringUtils;
import org.uygar.postit.controllers.BaseController;
import org.uygar.postit.controllers.exception.WindowCoordinatesContainer;
import org.uygar.postit.controllers.exception.WrongFieldsException;
import org.uygar.postit.controllers.filter.FilterUnitContainer;
import org.uygar.postit.controllers.filter.postit.PostItFilter;
import org.uygar.postit.controllers.post.utils.controller_manager.PostTabManager;
import org.uygar.postit.controllers.post.utils.loader.PostItLoader;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.data.query_utils.QueryUtils;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.PostIt;
import org.uygar.postit.post.viewers.post_it.PostItGridViewer;

import java.io.File;

public class PostController extends BaseController {

    @FXML
    public VBox post;
    @FXML
    public TabPane rootTabPane;
    @FXML
    public ScrollPane gridFatherScroll;
    @FXML
    public VBox vBoxOperationsContainer;
    @FXML
    public Label postTitle;
    @FXML
    public PieChart pieChart;
    @FXML
    public TextField srcBar;

    @FXML
    public TextField nomePostField;
    @FXML
    public SplitMenuButton tipoOrdinamentoField;
    @FXML
    public Button postResetButton, postSaveButton, postRemoveButton;
    @FXML
    public TextField postItTitleContains, postItPriorityField, postItTitleBegins;
    @FXML
    public DatePicker postTraField1, postTraField2;
    @FXML
    public Button filterResetButton, filterSaveButton, filterButton;

    public Post loadedPost;
    public DataMiner dataMiner;
    public Dimension2D minDimension;
    public PostItFilter postItFilter;
    public PostItGridViewer postItGrid;
    public PostTabManager postTabManager;

    public void init(Post fatherPost, DataMiner miner, Dimension2D initialWindowDimension) {
        post.setUserData(fatherPost);   // Identify a post pane in Stage windows

        dataMiner = miner;
        loadedPost = fatherPost;
        minDimension = initialWindowDimension;
        postTabManager = new PostTabManager(this);
        postTabManager.initPostControllerTab();
        postTabManager.initSettingsControllerTab();
        postTabManager.initStatisticsControllerTab();

        postItFilter = new PostItFilter(this, new FilterUnitContainer<>());
        deserializePostItFilter();
    }

    private void deserializePostItFilter() {
        PostItFilter deserialized = (PostItFilter) postItFilter.deserialize();
        if (deserialized != null) {
            deserialized.setFilterController(this);
            deserialized.applyFilterToController();
        }
        postItFilter = deserialized == null ? postItFilter : deserialized;
    }

    public static void openPostItController(PostIt postIt, PostItGridViewer postItGrid) {
        PostItLoader loader = new PostItLoader(postIt, postItGrid);
        loader.load();
    }

    @FXML
    public void onAggiungi() {
        openPostItController(null, this.postItGrid);
    }

    @FXML
    public void onOrdina() {
        postItGrid.sortFilteredPostIts();
    }

    @FXML
    public void onSettings() {
        rootTabPane.getSelectionModel().selectNext();
    }

    @FXML
    public void onExit() {
        exitFromPost();
    }

    @FXML
    public void onSavePostSettings() throws WrongFieldsException {
        postTabManager.postTabInitializer.changePostBasedOnSettings();
        rootTabPane.getSelectionModel().selectPrevious();
    }

    @FXML
    public void onResetPostSettings() {
        postTabManager.postSettingsInitializer.setInitialFields();
    }

    @FXML
    public void onRemovePost() {
        exitFromPost();
        loadedPost.setDeleted(true);
        QueryUtils.tryRemovePostFromDB(dataMiner, loadedPost);
    }

    @FXML
    public void onFilter() throws WrongFieldsException {
        if (fieldsAreNotValid())
            throw new WrongFieldsException("Devi inserire le date in modo corretto!",
                    new WindowCoordinatesContainer(this.post.getScene().getWindow()));
        else {
            postItFilter.resetUnitContainer();
            postItFilter.buildFilterSettingUnits();
            postItGrid.filter(postItFilter.getResult());
            rootTabPane.getSelectionModel().selectPrevious();
            postItFilter.serialize();
        }
    }

    @FXML
    public void onSaveFilter() {
        postItFilter.buildFilterSettingUnits();
        postItFilter.serialize();
    }

    @FXML
    public void onFilterReset() {
        resetFields();
        postItGrid.filter(postIt -> true);
        serializedFilterFileDeleteIfExists();
    }

    private void resetFields() {
        this.postItTitleContains.setText("");
        this.postItPriorityField.setText("");
        this.postItTitleBegins.setText("");
        this.postTraField1.setValue(null);
        this.postTraField2.setValue(null);
    }

    private void serializedFilterFileDeleteIfExists() {
        File file = new File("postit_filter.ser");
        file.delete();
    }

    private void exitFromPost() {
        this.rootTabPane.getScene().getWindow().hide();
    }

    public boolean fieldsAreNotValid() {
        boolean firstCondition = this.postTraField1.getValue() != null && this.postTraField2.getValue() == null;
        boolean secondCondition = this.postTraField1.getValue() == null && this.postTraField2.getValue() != null;

        boolean thirdCondition = isNotBlank(this.postItPriorityField.getText()) &&
                isNotNumeric(this.postItPriorityField.getText());

        return firstCondition || secondCondition || thirdCondition;
    }

    public boolean isNotBlank(String string) {
        return !string.isBlank();
    }

    private boolean isNotNumeric(String number) {
        return !StringUtils.isNumeric(number);
    }

}