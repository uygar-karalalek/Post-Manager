package org.uygar.postit.controllers.app;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.uygar.postit.controllers.app.filter.FilterController;
import org.uygar.postit.controllers.app.statistica.StatisticaController;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.data.properties.LogProperties;
import org.uygar.postit.data.structures.PostContainerOrganizer;
import org.uygar.postit.post.Post;
import org.uygar.postit.viewers.PostGridViewer;

import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    @FXML
    public BorderPane rootPane;
    @FXML
    Text title;
    @FXML
    ScrollPane scrollPane;
    @FXML
    PostGridViewer postGrid;
    @FXML
    Button addButton, filterButton;
    @FXML
    TextField searchField;

    DataMiner dataMiner = new DataMiner();
    PostContainerOrganizer postOrganizer = new PostContainerOrganizer(dataMiner);
    LogProperties properties;
    WindowInitializer windowInitializer = new WindowInitializer();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initPostGrid();
        this.searchField.textProperty().addListener(this::onSearchChanged);
    }

    private void initPostGrid() {
        postGrid = new PostGridViewer(postOrganizer);
        postGrid.selected.addListener(this::onSelectedChange);
        this.scrollPane.setContent(postGrid);
    }

    @FXML
    public void onAddClicked() {
        Stage stage = windowInitializer.getApplicationStage(366, 285, Modality.APPLICATION_MODAL);
        AggiungiController ac = (AggiungiController) FXLoader.getLoadedController("add", "app");
        ac.setPostGridViewer(this.postGrid);
        windowInitializer.fadeWindowEffect(ac.root, 1);
        Scene scene = new Scene(ac.root);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    public void onFilterClicked() {
        FilterController fc = (FilterController) FXLoader.getLoadedController("filter", "app");
        Stage stage = windowInitializer.getApplicationStage(486, 400, Modality.WINDOW_MODAL);
        fc.setPostGridViewer(this.postGrid);
        windowInitializer.fadeWindowEffect(fc.root, 0.4);
        Scene scene = new Scene(fc.root);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    public void onExitClicked() {
        this.rootPane.getScene().getWindow().hide();
    }

    @FXML
    public void onAbout() {

    }

    @FXML
    public void onStatisticaClicked() {
        Stage stage = windowInitializer.getApplicationStage(Modality.WINDOW_MODAL, true);
        StatisticaController sc = (StatisticaController)
                FXLoader.getLoadedController("statistica", "app");
        sc.setLogProperties(properties);
        sc.init();
        Scene scene = new Scene(sc.root);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void onSearchChanged(ObservableValue<? extends String> obs, String oldVal, String newVal) {
        this.postGrid.filter(newVal);
    }

    public Stage getStage() {
        return (Stage) this.rootPane.getScene().getWindow();
    }

    public void onSelectedChange(ObservableValue<? extends Post> v, Post oldV, Post newV) {
        this.loadPost(newV);
    }

    private void loadPost(Post post) {

    }

    private class WindowInitializer {

        private Stage getApplicationStage(double prefWidth, double prefHeight, Modality modality) {
            Stage stage = getApplicationStage(modality, false);
            stage.setWidth(prefWidth);
            stage.setHeight(prefHeight);
            setStageX(prefWidth, stage);
            setStageY(prefHeight, stage);
            stage.setResizable(false);
            return stage;
        }

        private Stage getApplicationStage(Modality modality, boolean resizable) {
            Stage stage = new Stage();
            stage.initModality(modality);
            stage.setResizable(resizable);
            return stage;
        }

        public void fadeWindowEffect(Parent root, double seconds) {
            FadeTransition transition = new FadeTransition(Duration.seconds(seconds), root);
            transition.setInterpolator(Interpolator.EASE_BOTH);
            transition.setFromValue(0);
            transition.setToValue(1);
            transition.play();
        }

        private void setStageX(double prefWidth, Stage stage) {
            stage.setX((getStage().getWidth() / 2 - prefWidth / 2) + getStage().getX());
        }

        private void setStageY(double prefHeight, Stage stage) {
            stage.setY((getStage().getHeight() / 2 - prefHeight / 2) + getStage().getY());
        }

    }

    public void setLogProperties(LogProperties properties) {
        this.properties = properties;
    }

}