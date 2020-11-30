package org.uygar.postit.controllers.application.app;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.uygar.postit.controllers.BaseController;
import org.uygar.postit.controllers.application.utils.WindowInitializer;
import org.uygar.postit.controllers.application.utils.app_loader.*;
import org.uygar.postit.controllers.application.utils.ButtonDisableBinding;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.data.properties.LogProperties;
import org.uygar.postit.data.structures.PostContainerOrganizer;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.viewers.post.PostGridViewer;

import java.util.Optional;

public class AppController extends BaseController {

    @FXML
    public BorderPane application;
    @FXML
    Text title;
    @FXML
    ScrollPane scrollPane;
    @FXML
    public Button addButton, filterButton, statisticaBtn;
    @FXML
    TextField searchField;
    @FXML
    MenuBar menuBar;

    public LogProperties properties;
    public StatisticaLoader statisticaLoader;
    public FilterLoader filterLoader;
    public AggiungiLoader aggiungiLoader;

    public PostGridViewer postGrid;
    public DataMiner dataMiner = new DataMiner();
    public PostContainerOrganizer postOrganizer = new PostContainerOrganizer(dataMiner);

    public void init() {
        initAndRequestFocusToSearchField();
        initPostGrid();
        this.searchField.textProperty().addListener(this::onSearchChanged);
    }

    private void initAndRequestFocusToSearchField() {
        searchField.setFocusTraversable(true);
        searchField.requestFocus();
    }

    private void initPostGrid() {
        postGrid = new PostGridViewer(postOrganizer);
        postGrid.selected.addListener(this::onPostSelectChanged);
        this.scrollPane.setContent(postGrid);
    }

    public void onPostSelectChanged(ObservableValue<? extends Post> v, Post oldV, Post newV) {
        Optional<Post> newPost = Optional.ofNullable(newV);
        newPost.ifPresent(this::loadPost);
    }

    private void loadPost(Post post) {
        PostLoader loader = new PostLoader(this, post);
        loader.load();
    }

    @FXML
    public void onAggiungiPostClicked() {
        aggiungiLoader = new AggiungiLoader(this);
        aggiungiLoader.load();
    }

    @FXML
    public void onOpenFilterClicked() {
        filterLoader = new FilterLoader(this);
        filterLoader.load();
    }

    @FXML
    public void onOpenStatisticaClicked() {
        statisticaLoader = new StatisticaLoader(this);
        statisticaLoader.load();
    }

    private double onClickedX, onClickedY;

    @FXML
    public void onMousePressed(MouseEvent event) {
        onClickedX = event.getX();
        onClickedY = event.getY();
    }

    @FXML
    public void onMouseDragged(MouseEvent event) {
        Stage mainStage = (Stage) this.application.getScene().getWindow();
        double xDiff = event.getScreenX() - (mainStage.getX() + onClickedX);
        double yDiff = event.getScreenY() - (mainStage.getY() + onClickedY);

        mainStage.setX(mainStage.getX() + xDiff);
        mainStage.setY(mainStage.getY() + yDiff);
    }

    @FXML
    public void onAbout() {

    }

    @FXML
    public void onExitClicked() {
        Platform.exit();
    }

    public void onSearchChanged(ObservableValue<? extends String> obs, String oldVal, String newVal) {
        this.postGrid.filterPostsNameContaining(newVal);
    }

    public void setHidingStageEventAndShowAndWait(Stage stage, ButtonDisableBinding disableBinding) {
        if (disableBinding != null)
            stage.setOnHiding(disableBinding::enableButton);
        stage.showAndWait();
    }

    @FXML
    public void onBlackStyleClicked() {
        setTheme("org/uygar/stylesheets/main/app_black.css");
    }

    @FXML
    public void onNormalStyleClicked() {
        setTheme("org/uygar/stylesheets/main/app_normal.css");
    }

    @FXML
    public void onBlueStyleClicked() {
    }

    public String getCurrentStyleCssFilePath() {
        return this.application.getStylesheets().get(0);
    }

    public void bindStyleSheetWithControllerName(String controllerName, String pkgName, Parent pane) {
        String stdPath = "org/uygar/stylesheets/" + pkgName + "/";
        String endPath = controllerName + "_" + getCurrentStyleColorFileName();
        pane.getStylesheets().setAll(stdPath + endPath);
        this.application.getProperties().addListener((InvalidationListener) change -> {
            String updatedPath = controllerName + "_" + getCurrentStyleColorFileName();
            pane.getStylesheets().setAll(stdPath + updatedPath);
        });
    }

    private String getCurrentStyleColorFileName() {
        String current = getCurrentStyleCssFilePath();
        return current.substring(current.lastIndexOf('_') + 1);
    }

    public void setTheme(String cssFilePath) {
        this.application.getStylesheets().setAll(cssFilePath);
        this.application.getProperties().put("style", cssFilePath);
    }

    public void setLogProperties(LogProperties properties) {
        this.properties = properties;
    }

}