package org.uygar.postit.controllers.application.app.utils;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.scene.Parent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.uygar.postit.controllers.application.app.AppController;

public class WindowInitializer {

    private final AppController appController;

    public WindowInitializer(AppController appController) {
        this.appController = appController;
    }

    public Stage getStageWithModality(double prefWidth, double prefHeight, Modality modality) {
        Stage stage = getStageWithModality(modality, false);
        stage.setWidth(prefWidth);
        stage.setHeight(prefHeight);
        setStageX(prefWidth, stage);
        setStageY(prefHeight, stage);
        stage.setResizable(false);
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
        return (Stage) appController.rootPane.getScene().getWindow();
    }

}
