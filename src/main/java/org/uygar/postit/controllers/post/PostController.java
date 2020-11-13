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
import org.uygar.postit.controllers.post.utils.PostStatistics;
import org.uygar.postit.controllers.post.utils.PostStatisticsViewManager;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.viewers.post_it.PostItGridViewer;

public class PostController {

    private static final float SCROLL_SPEED = 0.001f;

    @FXML
    public TabPane root;

    @FXML
    public ScrollPane gridFatherScroll;

    @FXML
    VBox vBoxOperationsContainer;

    @FXML
    private Label postTitle;

    @FXML
    private PieChart pieChart;

    PostItGridViewer postItGrid;

    private Dimension2D minDimension;

    public void init(Post fatherPost, DataMiner miner, Dimension2D initialWindowDimension) {
        this.minDimension = initialWindowDimension;
        this.postItGrid = new PostItGridViewer(fatherPost, miner);
        PostStatisticsViewManager.buildChart(pieChart, new PostStatistics(postItGrid.getPostItOrganizer()));
        initGridPane();
        initPostTitle(fatherPost);
    }

    private void initPostTitle(Post fatherPost) {
        postTitle.setText(fatherPost.getName());
        addDefaultFontToTitleLabel();
    }

    private void initGridPane() {
        this.gridFatherScroll.setContent(postItGrid);
        this.postItGrid.prefWidthProperty().bind(this.gridFatherScroll.widthProperty());
        this.postItGrid.prefHeightProperty().bind(this.gridFatherScroll.heightProperty());
        this.gridFatherScroll.getContent().setOnScroll(this::setOnScroll);
        this.gridFatherScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.gridFatherScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    public void setMinSizeByDimensionOfStage(Stage stage) {
        stage.widthProperty().addListener(this::gridWidthDimensionListener);
        stage.heightProperty().addListener(this::gridHeightDimensionListener);
    }

    private void gridWidthDimensionListener(ObservableValue<? extends Number> obs, Number oldVal, Number newWidth) {
        if ((double) newWidth < minDimension.getWidth())
            this.root.getScene().getWindow().setWidth(minDimension.getWidth());
    }

    private void gridHeightDimensionListener(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
        if ((double) newVal < minDimension.getHeight())
            this.root.getScene().getWindow().setHeight(minDimension.getHeight());
    }

    private void setOnScroll(ScrollEvent event) {
        double deltaY = event.getDeltaY() * SCROLL_SPEED;
        gridFatherScroll.setVvalue(gridFatherScroll.getVvalue() - deltaY);
    }

    private void addDefaultFontToTitleLabel() {
        double defSize = 30;
        double lblTextLength = defSize / 1.25 * postTitle.getText().length();
        double ratioContainerLabel = vBoxOperationsContainer.getPrefWidth() / lblTextLength;
        ratioContainerLabel = Math.min(1, ratioContainerLabel);
        Font def = Font.font("Arial", FontWeight.EXTRA_BOLD, defSize * ratioContainerLabel);
        postTitle.setFont(def);
    }

    @FXML
    public void onOrdina() {
        postItGrid.sortPostIts();
    }

    @FXML
    public void onExit() {
        this.root.getScene().getWindow().hide();
    }

}