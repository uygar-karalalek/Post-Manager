package org.uygar.postit.controllers.application.app;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
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
import org.uygar.postit.controllers.application.FXLoader;
import org.uygar.postit.controllers.application.WindowDimensions;
import org.uygar.postit.controllers.application.app.utils.WindowInitializer;
import org.uygar.postit.controllers.application.app.utils.loader.AggiungiLoader;
import org.uygar.postit.controllers.application.app.utils.loader.FilterLoader;
import org.uygar.postit.controllers.application.app.utils.loader.StatisticaLoader;
import org.uygar.postit.controllers.application.app.utils.loader.WindowLoader;
import org.uygar.postit.controllers.post.PostController;
import org.uygar.postit.controllers.utils.ButtonDisableBinding;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.data.properties.LogProperties;
import org.uygar.postit.data.structures.PostContainerOrganizer;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.viewers.post.PostGridViewer;

import java.net.URL;
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
    public WindowInitializer windowInitializer = new WindowInitializer(this);
    public WindowLoader statisticaLoader, filterLoader, aggiungiLoader;

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
        PostController postController = (PostController) FXLoader.getLoadedController("post", "post");
        postController.init(post, dataMiner, WindowDimensions.POST_WINDOW_DIMENSION);

        Stage postStage = initializeWindowAndGet(WindowDimensions.POST_WINDOW_DIMENSION, Modality.WINDOW_MODAL, postController.root);
        postStage.setOnHidden(windowEvent -> {
            this.postGrid.nothingSelected();
            this.postGrid.enablePostViewByPost(post);
        });

        addStylesheetToPaneWithControllerName("post", "post", postController.root);

        postController.setMinSizeByDimensionOfStage(postStage);
        postStage.setResizable(true);
        postStage.show();
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

    public Stage initializeWindowAndGet(Dimension2D dimension, Modality modality, Parent root) {
        Stage stage = windowInitializer.getStageWithModality(dimension.getWidth(), dimension.getHeight(), modality);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        return stage;
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

    public void addStylesheetToPaneWithControllerName(String controllerName, String pkgName, Parent pane) {
        String stdPath = "org/uygar/stylesheets/" + pkgName + "/";
        String endPath = controllerName + "_" + getCurrentStyleColorFileName();
        pane.getStylesheets().add(stdPath + endPath);
    }

    private String getCurrentStyleColorFileName() {
        String current = getCurrentStyleCssFilePath();
        return current.substring(current.lastIndexOf('_') + 1);
    }

    public void setTheme(String cssFilePath) {
        this.rootPane.getStylesheets().setAll(cssFilePath);
    }

    public void setLogProperties(LogProperties properties) {
        this.properties = properties;
    }


}