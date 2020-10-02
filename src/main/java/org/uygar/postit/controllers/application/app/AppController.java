package org.uygar.postit.controllers.application.app;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Dimension2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.uygar.postit.controllers.application.AggiungiController;
import org.uygar.postit.controllers.application.FXLoader;
import org.uygar.postit.controllers.application.WindowDimensions;
import org.uygar.postit.controllers.application.filter.FilterController;
import org.uygar.postit.controllers.application.statistica.StatisticaController;
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
    Button addButton, filterButton, statisticaBtn;
    @FXML
    TextField searchField;

    ButtonDisableBinding filterDisableBinding, statisticaDisableBinding;
    LogProperties properties;
    WindowInitializer windowInitializer = new WindowInitializer(this);

    PostGridViewer postGrid;
    DataMiner dataMiner = new DataMiner();
    PostContainerOrganizer postOrganizer = new PostContainerOrganizer(dataMiner);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
    }

    private void init() {
        searchField.setFocusTraversable(true);
        searchField.requestFocus();
        initPostGrid();

        filterDisableBinding = new ButtonDisableBinding(filterButton);
        statisticaDisableBinding = new ButtonDisableBinding(statisticaBtn);

        this.searchField.textProperty().addListener(this::onSearchChanged);
    }

    private void initPostGrid() {
        postGrid = new PostGridViewer(postOrganizer);
        postGrid.selected.addListener(this::onPostSelectChanged);
        this.scrollPane.setContent(postGrid);
    }

    @FXML
    public void onAddClicked() {
        AggiungiController ac = (AggiungiController) FXLoader.getLoadedController("add", "app");
        ac.setPostGridViewer(this.postGrid);
        ac.root.getStylesheets().setAll(this.rootPane.getStylesheets().get(0), "org/uygar/stylesheets/main/add_" + getCurrentStyleColorFileName());
        windowInitializer.fadeWindowEffect(ac.root, 1);
        Stage stage = initializeWindowAndGet(WindowDimensions.ADD_WINDOW_DIMENSION, Modality.APPLICATION_MODAL, ac.root);
        setHidingStageEventAndShowAndWait(stage, filterDisableBinding);
    }

    @FXML
    public void onFilterClicked() {
        filterDisableBinding.disableOpenWindowButton();
        FilterController fc = (FilterController) FXLoader.getLoadedController("filter", "app");
        fc.init(this.postGrid);
        windowInitializer.fadeWindowEffect(fc.root, 0.4);
        Stage stage = initializeWindowAndGet(WindowDimensions.FILTER_WINDOW_DIMENSION, Modality.WINDOW_MODAL, fc.root);
        setHidingStageEventAndShowAndWait(stage, filterDisableBinding);
    }

    private void setHidingStageEventAndShowAndWait(Stage stage, ButtonDisableBinding disableBinding) {
        stage.setOnHiding(disableBinding::closedByEventClosed);
        stage.showAndWait();
    }

    @FXML
    public void onStatisticaClicked() {
        statisticaDisableBinding.disableOpenWindowButton();
        Stage stage = windowInitializer.getStageWithModality(Modality.WINDOW_MODAL, true);
        StatisticaController sc = (StatisticaController)
                FXLoader.getLoadedController("statistica", "app");
        sc.setLogProperties(properties);
        sc.init();
        sc.root.getStylesheets().setAll("org/uygar/stylesheets/main/statistica_" + getCurrentStyleColorFileName());
        Scene scene = new Scene(sc.root);
        stage.setScene(scene);
        stage.setOnHiding(statisticaDisableBinding::closedByEventClosed);
        stage.showAndWait();
    }

    private void loadPost(Post post) {
        PostController postController = (PostController) FXLoader.getLoadedController("post", "post");
        postController.init(post, dataMiner, WindowDimensions.POST_WINDOW_DIMENSION);

        Stage postStage = initializeWindowAndGet(WindowDimensions.POST_WINDOW_DIMENSION, Modality.WINDOW_MODAL, postController.root);
        postStage.setOnHidden(windowEvent -> {
            this.postGrid.nothingSelected();
            this.postGrid.enablePostViewByPost(post);
        });

        postController.setMinSizeByDimensionOfStage(postStage);
        postStage.setResizable(true);
        postStage.show();
    }

    @FXML
    public void onAbout() {

    }

    @FXML
    public void onExitClicked() {
        Platform.exit();
    }


    public Stage initializeWindowAndGet(Dimension2D dimension, Modality modality, Parent root) {
        Stage stage = windowInitializer.getStageWithModality(dimension.getWidth(), dimension.getHeight(), modality);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        return stage;
    }

    public void onSearchChanged(ObservableValue<? extends String> obs, String oldVal, String newVal) {
        this.postGrid.filterPostsNameContaining(newVal);
    }

    public void onPostSelectChanged(ObservableValue<? extends Post> v, Post oldV, Post newV) {
        Optional<Post> newPost = Optional.ofNullable(newV);
        newPost.ifPresent(this::loadPost);
    }

    public void setLogProperties(LogProperties properties) {
        this.properties = properties;
    }

    public void onBlackStyleClicked() {
        setTheme("org/uygar/stylesheets/main/app_black.css");
    }

    public void onNormalStyleClicked() { setTheme("org/uygar/stylesheets/main/app_normal.css"); }

    public void onBlueStyleClicked() {}

    public String getCurrentStyleCssFilePath() {
        return this.rootPane.getStylesheets().get(0);
    }

    private String getCurrentStyleColorFileName() {
        String current = getCurrentStyleCssFilePath();
        return current.substring(current.lastIndexOf('_') + 1);
    }

    public void setTheme(String cssFilePath) {
        this.rootPane.getStylesheets().setAll(cssFilePath);
    }

}