package org.uygar.postit.controllers.post;

import javafx.fxml.FXML;
import javafx.geometry.Dimension2D;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.uygar.postit.controllers.BaseController;
import org.uygar.postit.controllers.post.utils.controller_manager.PostTabManager;
import org.uygar.postit.controllers.post.utils.loader.PostItLoader;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.data.query_utils.QueryUtils;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.PostIt;
import org.uygar.postit.post.viewers.post_it.PostItGridViewer;

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
    public TextField postIniziaField;
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
        System.out.println("LOADING: " + loadedPost);
        minDimension = initialWindowDimension;
        postTabManager = new PostTabManager(this);

        postTabManager.initPostControllerTab();
        postTabManager.initSettingsControllerTab();
        postTabManager.initStatisticsControllerTab();
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
        rootTabPane.getSelectionModel().selectNext();
        postItGrid.sortPostIts();
    }

    @FXML
    public void onExit() {
        exitFromPost();
    }

    @FXML
    public void onSavePostSettings() {
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

    private void exitFromPost() {
        this.rootTabPane.getScene().getWindow().hide();
    }

}