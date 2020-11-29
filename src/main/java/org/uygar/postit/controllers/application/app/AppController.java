package org.uygar.postit.controllers.application.app;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Dimension2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.uygar.postit.controllers.application.utils.WindowInitializer;
import org.uygar.postit.controllers.application.utils.app_loader.*;
import org.uygar.postit.controllers.application.utils.ButtonDisableBinding;
import org.uygar.postit.controllers.loader.WindowLoader;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.data.properties.LogProperties;
import org.uygar.postit.data.structures.PostContainerOrganizer;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.viewers.post.PostGridViewer;

import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    @FXML
    public BorderPane rootPane;
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
    public WindowInitializer windowInitializer;
    public WindowLoader<AppController> statisticaLoader, filterLoader, aggiungiLoader;

    public PostGridViewer postGrid;
    public DataMiner dataMiner = new DataMiner();
    public PostContainerOrganizer postOrganizer = new PostContainerOrganizer(dataMiner);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
    }

    private void init() {
        initAndRequestFocusToSearchField();
        initPostGrid();
        windowInitializer = new WindowInitializer(rootPane);
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
        Stage mainStage = (Stage) this.rootPane.getScene().getWindow();
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
            stage.setOnHiding(disableBinding::closedByEventClosed);
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
        return this.rootPane.getStylesheets().get(0);
    }

    public void bindStyleSheetWithControllerName(String controllerName, String pkgName, Parent pane) {
        String stdPath = "org/uygar/stylesheets/" + pkgName + "/";
        String endPath = controllerName + "_" + getCurrentStyleColorFileName();
        pane.getStylesheets().setAll(stdPath + endPath);
        this.rootPane.getProperties().addListener((InvalidationListener) change -> {
            String updatedPath = controllerName + "_" + getCurrentStyleColorFileName();
            pane.getStylesheets().setAll(stdPath + updatedPath);
        });
    }

    private String getCurrentStyleColorFileName() {
        String current = getCurrentStyleCssFilePath();
        return current.substring(current.lastIndexOf('_') + 1);
    }

    public void setTheme(String cssFilePath) {
        this.rootPane.getStylesheets().setAll(cssFilePath);
        this.rootPane.getProperties().put("style", cssFilePath);
    }

    public void setLogProperties(LogProperties properties) {
        this.properties = properties;
    }

}