package org.uygar.postit.controllers.post;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Dimension2D;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.uygar.postit.controllers.BaseController;
import org.uygar.postit.controllers.application.utils.WindowInitializer;
import org.uygar.postit.controllers.post.utils.PostStatistics;
import org.uygar.postit.controllers.post.utils.PostStatisticsViewManager;
import org.uygar.postit.controllers.post.utils.loader.PostItLoader;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.data.structures.PostItContainerOrganizer;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.PostIt;
import org.uygar.postit.post.viewers.post_it.PostItGridViewer;

public class PostController extends BaseController {

    private static final float SCROLL_SPEED = 0.001f;

    @FXML
    public StackPane post;
    @FXML
    public TabPane rootTabPane;
    @FXML
    public ScrollPane gridFatherScroll;
    @FXML
    VBox vBoxOperationsContainer;
    @FXML
    private Label postTitle;
    @FXML
    private PieChart pieChart;

    public PostItGridViewer postItGrid;

    public Post loadingPost;

    private Dimension2D minDimension;

    public void init(Post fatherPost, DataMiner miner, Dimension2D initialWindowDimension) {
        // Identify a post pane in Stage windows
        post.setUserData(fatherPost);

        this.loadingPost = fatherPost;
        this.minDimension = initialWindowDimension;
        this.postItGrid = new PostItGridViewer(fatherPost, miner);
        PostStatisticsViewManager.buildChart(pieChart, new PostStatistics(postItGrid.getPostItOrganizer()));
        initGridPane();
        initPostTitle(fatherPost);
        this.rootTabPane.prefWidthProperty().bind(post.widthProperty());
        this.rootTabPane.prefHeightProperty().bind(post.heightProperty());
    }

    public static void openPostItController(PostIt postIt, PostItGridViewer postItGrid) {
        PostItLoader loader = new PostItLoader(postIt, postItGrid);
        loader.load();
    }

    private void initPostTitle(Post fatherPost) {
        postTitle.setText(fatherPost.getName());
        setFontSizeToTitleLabelBasedOnLength();
    }

    private void initGridPane() {
        this.gridFatherScroll.setContent(postItGrid);
        this.postItGrid.prefWidthProperty().bind(this.gridFatherScroll.widthProperty());
        this.postItGrid.prefHeightProperty().bind(this.gridFatherScroll.heightProperty());
        this.gridFatherScroll.getContent().setOnScroll(this::setOnPostItGridScroll);
        this.gridFatherScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.gridFatherScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    public void setMinSizeListenerByDimensionOfStage(Stage stage) {
        stage.widthProperty().addListener(this::onWindowWidthChangeResizePostItGrid);
        stage.heightProperty().addListener(this::onWindowHeightChangeResizePostItGrid);
    }

    private void onWindowWidthChangeResizePostItGrid(ObservableValue<? extends Number> obs, Number oldVal, Number newWidth) {
        if ((double) newWidth < minDimension.getWidth())
            this.rootTabPane.getScene().getWindow().setWidth(minDimension.getWidth());
    }

    private void onWindowHeightChangeResizePostItGrid(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
        if ((double) newVal < minDimension.getHeight())
            this.rootTabPane.getScene().getWindow().setHeight(minDimension.getHeight());
    }

    private void setOnPostItGridScroll(ScrollEvent event) {
        double deltaY = event.getDeltaY() * SCROLL_SPEED;
        gridFatherScroll.setVvalue(gridFatherScroll.getVvalue() - deltaY);
    }

    private void setFontSizeToTitleLabelBasedOnLength() {
        double defSize = 30;
        double lblTextLength = defSize / 1.25 * postTitle.getText().length();
        double ratioContainerLabel = vBoxOperationsContainer.getPrefWidth() / lblTextLength;
        ratioContainerLabel = Math.min(1, ratioContainerLabel);
        Font def = Font.font("Arial", FontWeight.EXTRA_BOLD, defSize * ratioContainerLabel);
        postTitle.setFont(def);
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
    public void onExit() {
        this.rootTabPane.getScene().getWindow().hide();
    }

}