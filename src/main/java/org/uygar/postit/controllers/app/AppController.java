package org.uygar.postit.controllers.app;

import javafx.animation.FadeTransition;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.data.structures.PostContainerOrganizer;
import org.uygar.postit.post.Post;
import org.uygar.postit.viewers.PostGridViewer;
import org.uygar.postit.viewers.PostViewer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    @FXML
    BorderPane rootPane;

    @FXML
    Text title;

    @FXML
    Button addButton, filterButton;

    @FXML
    ScrollPane scrollPane;

    @FXML
    PostGridViewer postGrid;

    @FXML
    TextField searchField;

    DataMiner dataMiner = new DataMiner();

    PostContainerOrganizer postOrganizer = new PostContainerOrganizer(dataMiner);

    Post selectedPost;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initPostGrid();
        this.searchField.textProperty().addListener(this::onSearchChanged);
    }

    private void initPostGrid() {
        postGrid = new PostGridViewer(postOrganizer);
        this.scrollPane.setContent(postGrid);
    }

    @FXML
    public void onAddClicked() {
        double prefWidth = 366;
        double prefHeight = 285;
        AggiungiController ac = (AggiungiController) FXLoader.getLoadedController("add", "app");
        ac.setPostGridViewer(this.postGrid);
        fadedParent(ac.root, 1);
        Scene scene = new Scene(ac.root);
        Stage stage = createApplicationStage(prefWidth, prefHeight);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    public void onFilterClicked() {
        double prefWidth = 486;
        double prefHeight = 400;
        FilterController fc = (FilterController) FXLoader.getLoadedController("filter", "app");
        Stage stage = createApplicationStage(prefWidth, prefHeight);
        fc.setPostGridViewer(this.postGrid);
        fadedParent(fc.root, 1);
        Scene scene = new Scene(fc.root);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private Stage createApplicationStage(double prefWidth, double prefHeight) {
        Stage stage = new Stage();
        stage.setWidth(prefWidth);
        stage.setHeight(prefHeight);
        setStageX(prefWidth, stage);
        setStageY(prefHeight, stage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        return stage;
    }

    private void setStageX(double prefWidth, Stage stage) {
        stage.setX((getStage().getWidth() / 2 - prefWidth / 2) + getStage().getX());
    }

    private void setStageY(double prefHeight, Stage stage) {
        stage.setY((getStage().getHeight() / 2 - prefHeight / 2) + getStage().getY());
    }

    @FXML
    public void onExitClicked() {
        this.rootPane.getScene().getWindow().hide();
    }

    @FXML
    public void onAbout() {

    }

    public void fadedParent(Parent root, int seconds) {
        FadeTransition transition = new FadeTransition(Duration.seconds(seconds), root);
        transition.setFromValue(0);
        transition.setToValue(1);
        transition.play();
    }

    public void onSearchChanged(ObservableValue<? extends String> obs, String oldVal, String newVal) {
        this.postGrid.filter(newVal);
    }

    public Stage getStage() {
        return (Stage) this.rootPane.getScene().getWindow();
    }

}