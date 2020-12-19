package org.uygar.postit.controllers.post;

import javafx.fxml.FXML;
import javafx.geometry.Dimension2D;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.uygar.postit.controllers.BaseController;
import org.uygar.postit.controllers.post.utils.controller_manager.PostControllerViewManager;
import org.uygar.postit.controllers.post.utils.loader.PostItLoader;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.PostIt;
import org.uygar.postit.post.properties.Sort;
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
    public ChoiceBox<Sort> tipoOrdinamentoField;
    @FXML
    public Button postResetButton, postSaveButton, postRemoveButton;
    @FXML
    public TextField postIniziaField;
    @FXML
    public DatePicker postTraField1, postTraField2;
    @FXML
    public Button filterResetButton, filterSaveButton, filterButton;

    public DataMiner dataMiner;
    public Post loadingPost;
    public Dimension2D minDimension;
    public PostItGridViewer postItGrid;
    public PostControllerViewManager postControllerManager;

    public void init(Post fatherPost, DataMiner miner, Dimension2D initialWindowDimension) {
        post.setUserData(fatherPost);   // Identify a post pane in Stage windows
        dataMiner = miner;
        loadingPost = fatherPost;
        minDimension = initialWindowDimension;
        postControllerManager = new PostControllerViewManager(this);

        postControllerManager.initPostControllerTab();
        postControllerManager.initSettingsControllerTab();
        postControllerManager.initStatisticsControllerTab();
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
        this.rootTabPane.getScene().getWindow().hide();
    }

}