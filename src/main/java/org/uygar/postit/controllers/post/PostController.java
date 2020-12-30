package org.uygar.postit.controllers.post;

import javafx.fxml.FXML;
import javafx.geometry.Dimension2D;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.uygar.postit.controllers.BaseController;
import org.uygar.postit.controllers.exception.WindowCoordinatesContainer;
import org.uygar.postit.controllers.exception.WrongFieldsException;
import org.uygar.postit.controllers.post.utils.controller_manager.PostTabManager;
import org.uygar.postit.controllers.post.utils.filter.PostItFilterBuilder;
import org.uygar.postit.controllers.post.utils.filter.PostItFilterSerializer;
import org.uygar.postit.controllers.post.utils.loader.PostItLoader;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.data.query_utils.QueryUtils;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.PostIt;
import org.uygar.postit.post.viewers.post_it.PostItGridViewer;

import java.io.File;
import java.util.OptionalInt;

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
    public TextField postItTitleField, postItPriorityField;
    @FXML
    public DatePicker postTraField1, postTraField2;
    @FXML
    public Button filterResetButton, filterSaveButton, filterButton;

    public DataMiner dataMiner;
    public Post loadedPost;
    public Dimension2D minDimension;
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

        deserializePostItFilter();
    }

    private void deserializePostItFilter() {
        PostItFilterSerializer filter = PostItFilterSerializer.deserialize();
        if (filter != null)
            filter.applyFilter(this);
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
        postItGrid.sortPostIts();
    }

    @FXML
    public void onSettings() {
        rootTabPane.getSelectionModel().selectNext();
    }

    @FXML
    public void onExit() {
        PostItFilterSerializer.serialize(new PostItFilterSerializer(this.postItTitleField.getText(),
                this.postItPriorityField.getText(),
                this.postTraField1.getValue(), this.postTraField2.getValue()));

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
       // if (fieldsAreValid())
       //     postItGrid.filter(getPostItFilterBuilder().unifiedPredicates());
       // else throw new WrongFieldsException("Devi inserire le date in modo corretto!",
        //        new WindowCoordinatesContainer(this.post.getScene().getWindow()));
    }

    @FXML
    public void onSaveFilter() {
        //PostItFilterSerializer.serialize(getPostItFilterSerializer());
    }

    @FXML
    public void onFilterReset() {
        /*resetFields();
        postItGrid.filter(postIt -> true);
        deleteSerializedFileIfExists();
    */}

    private void deleteSerializedFileIfExists() {
      /*  File file = new File("postit_filter.ser");
        file.delete();
    */}

    private void resetFields() {
        this.postItTitleField.setText("");
        this.postItPriorityField.setText("");
        this.postTraField1.setValue(null);
        this.postTraField2.setValue(null);
    }

    private void exitFromPost() {
        this.rootTabPane.getScene().getWindow().hide();
    }

    public boolean fieldsAreValid() {
        System.out.println(this.postTraField1.getValue() + " - " + this.postTraField2.getValue());
        boolean firstCondition = this.postTraField1.getValue() == null && this.postTraField2.getValue() == null;
        boolean secondCondition = this.postTraField1.getValue() != null && this.postTraField1.getValue() != null;

        return firstCondition || secondCondition;
    }

    public PostItFilterBuilder getPostItFilterBuilder() {
        return new PostItFilterBuilder(this.postItTitleField.getText(), this.postItPriorityField.getText(),
                this.postTraField1.getValue(), this.postTraField2.getValue());
    }

    public PostItFilterSerializer getPostItFilterSerializer() {
        return new PostItFilterSerializer(this.postItTitleField.getText(),
                this.postItPriorityField.getText(),
                this.postTraField1.getValue(), this.postTraField2.getValue());
    }

}