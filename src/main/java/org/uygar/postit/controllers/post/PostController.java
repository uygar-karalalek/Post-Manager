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
import org.uygar.postit.controllers.post.postit.editor_manager.managers.InitialValuesManager;
import org.uygar.postit.controllers.post.utils.controller_manager.PostTabManager;
import org.uygar.postit.controllers.post.utils.loader.PostItLoader;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.data.query_utils.QueryUtils;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.PostIt;
import org.uygar.postit.post.viewers.post_it.PostItGridViewer;

import java.io.File;
import java.time.LocalDate;

import static org.uygar.postit.controllers.post.postit.editor_manager.managers.InitialValuesManager.*;
import static org.uygar.postit.controllers.post.postit.editor_manager.managers.InitialValuesManager.isPriorityValid;

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

    @FXML
    public FlowPane statsPane;

    public Post loadedPost;
    public DataMiner dataMiner;
    public Dimension2D minDimension;
    public PostItFilter postItFilter;
    public PostItGridViewer postItGrid;
    public PostTabManager postTabManager;

    public void init(Post fatherPost, DataMiner miner, Dimension2D initialWindowDimension) {
        this.post.setUserData(fatherPost);   // Identify a post pane in Stage windows

        this.dataMiner = miner;
        this.loadedPost = fatherPost;
        this.minDimension = initialWindowDimension;
        this.postTabManager = new PostTabManager(this);
        this.postTabManager.initPostControllerTabs();

        this.postItFilter = new PostItFilter(this, new FilterUnitContainer<>());
        this.deserializePostItFilter();
    }

    private void deserializePostItFilter() {
        PostItFilter deserialized = (PostItFilter) this.postItFilter.deserialize();
        if (deserialized != null) {
            deserialized.setFilterController(this);
            deserialized.applyFilterToController();
        }
        this.postItFilter = deserialized == null ? this.postItFilter : deserialized;
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
        this.postItGrid.sortVisiblePostIts();
    }

    @FXML
    public void onSettings() {
        this.rootTabPane.getSelectionModel().selectNext();
    }

    @FXML
    public void onExit() {
        this.exitFromPost();
    }

    @FXML
    public void onSavePostSettings() throws WrongFieldsException {
        this.postTabManager.postTabInitializer.changePostBasedOnSettings();
        this.rootTabPane.getSelectionModel().selectPrevious();
    }

    @FXML
    public void onResetPostSettings() {
        this.postTabManager.postSettingsInitializer.setInitialFields();
    }

    @FXML
    public void onRemovePost() {
        this.exitFromPost();
        this.loadedPost.setDeleted(true);
        QueryUtils.tryRemovePostFromDB(dataMiner, loadedPost);
    }

    @FXML
    public void onFilter() throws WrongFieldsException {
        if (fieldsAreNotValid())
            throw new WrongFieldsException("Devi inserire le date in modo corretto!",
                    new WindowCoordinatesContainer(this.post.getScene().getWindow()));
        this.postItFilter.resetUnitContainer();
        this.postItFilter.buildFilterSettingUnits();
        this.postItGrid.filter(postItFilter.getResult());
        this.rootTabPane.getSelectionModel().selectPrevious();
        this.postItFilter.serialize();
    }

    @FXML
    public void onSaveFilter() throws WrongFieldsException {
        if (fieldsAreNotValid())
            throw new WrongFieldsException("Devi inserire le date in modo corretto!",
                    new WindowCoordinatesContainer(this.post.getScene().getWindow()));
        this.postItFilter.buildFilterSettingUnits();
        this.postItFilter.serialize();
    }

    @FXML
    public void onFilterReset() {
        this.resetFields();
        this.postItGrid.filter(postIt -> true);
        this.serializedFilterFileDeleteIfExists();
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
        return this.datesAreNotValid() || this.priorityIsNotValid();
    }

    private boolean datesAreNotValid() {
        LocalDate firstDateValue = this.postTraField1.getValue();
        LocalDate secondDateValue = this.postTraField2.getValue();
        return (firstDateValue != null && secondDateValue == null)
                || (firstDateValue == null && secondDateValue != null);
    }

    private boolean priorityIsNotValid() {
        String priorityText = this.postItPriorityField.getText();
        return isNotBlank(priorityText) && (isNotNumeric(priorityText) || isPriorityNotValid(priorityText));
    }

    private boolean isPriorityNotValid(String priorityText) {
        return !isPriorityValid(Integer.parseInt(priorityText));
    }

    public boolean isNotBlank(String string) {
        return !string.isBlank();
    }

    private boolean isNotNumeric(String number) {
        return !StringUtils.isNumeric(number);
    }

}