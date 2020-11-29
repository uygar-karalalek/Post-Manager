package org.uygar.postit.controllers.application.utils;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.geometry.Dimension2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class WindowInitializer {

    private final Pane rootPane;

    public WindowInitializer(Pane rootPane) {
        this.rootPane = rootPane;
    }

    public Stage getStageWithModality(double prefWidth, double prefHeight, Modality modality, boolean resizable) {
        Stage stage = getStageWithModality(modality, resizable);
        stage.setWidth(prefWidth);
        stage.setHeight(prefHeight);
        setStageX(prefWidth, stage);
        setStageY(prefHeight, stage);
        return stage;
    }

    public Stage getStageWithModality(Modality modality, boolean resizable) {
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

    void setStageX(double prefWidth, Stage stage) {
        stage.setX((getStage().getWidth() / 2 - prefWidth / 2) + getStage().getX());
    }

    void setStageY(double prefHeight, Stage stage) {
        stage.setY((getStage().getHeight() / 2 - prefHeight / 2) + getStage().getY());
    }

    public Stage getStage() {
        return (Stage) rootPane.getScene().getWindow();
    }

    public Stage initializeApplicationWindowAndGet(Dimension2D dimension, Modality modality, Parent root, boolean resizable) {
        Stage stage = this.getStageWithModality(dimension.getWidth(), dimension.getHeight(), modality, resizable);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        return stage;
    }

}