package org.uygar.postit.controllers.app;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.uygar.postit.post.Post;

public class AppController {

    @FXML
    VBox rootPane;

    @FXML
    Text title;

    @FXML
    Button addButton, filterButton;

    @FXML
    GridPane postGrid;

    Post selectedPost;

    @FXML
    public void onAddClicked() {
        double prefWidth = 366;
        double prefHeight = 200;
        Parent root = FXLoader.getLoadedParent("add", "app");
        fadedParent(root, 1);
        Scene scene = new Scene(root);
        Stage stage = createApplicationStage(prefWidth, prefHeight);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    public void onFilterClicked() {
        double prefWidth = 486;
        double prefHeight = 400;
        Stage stage = createApplicationStage(prefWidth, prefHeight);
        Parent root = FXLoader.getLoadedParent("filter", "app");
        fadedParent(root, 1);
        Scene scene = new Scene(root);
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
        stage.setX((getStage().getWidth() / 2 - prefWidth/2) + getStage().getX());
    }

    private void setStageY(double prefHeight, Stage stage) {
        stage.setY((getStage().getHeight() / 2 - prefHeight/2) + getStage().getY());
    }

    @FXML
    public void onExitClicked() {
        this.postGrid.getScene().getWindow().hide();
    }

    public void fadedParent(Parent root, int seconds) {
        FadeTransition transition = new FadeTransition(Duration.seconds(seconds), root);
        transition.setFromValue(0);
        transition.setToValue(1);
        transition.play();
    }

    public Stage getStage() {
        return (Stage) this.rootPane.getScene().getWindow();
    }

}