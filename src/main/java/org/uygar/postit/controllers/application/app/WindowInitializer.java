package org.uygar.postit.controllers.application.app;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.scene.Parent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

class WindowInitializer {

    private final AppController appController;

    public WindowInitializer(AppController appController) {
        this.appController = appController;
    }

    Stage getStageWithModality(double prefWidth, double prefHeight, Modality modality) {
        Stage stage = getStageWithModality(modality, false);
        stage.setWidth(prefWidth);
        stage.setHeight(prefHeight);
        setStageX(prefWidth, stage);
        setStageY(prefHeight, stage);
        stage.setResizable(false);
        return stage;
    }

    Stage getStageWithModality(Modality modality, boolean resizable) {
        Stage stage = new Stage();
        stage.initModality(modality);
        stage.setResizable(resizable);
        return stage;
    }

    void fadeWindowEffect(Parent root, double seconds) {
        FadeTransition transition = new FadeTransition(Duration.seconds(seconds), root);
        transition.setInterpolator(Interpolator.EASE_BOTH);
        transition.setFromValue(0);
        transition.setToValue(1);
        transition.play();
    }

    void setStageX(double prefWidth, Stage stage) {
        stage.setX((appController.getStage().getWidth() / 2 - prefWidth / 2) + appController.getStage().getX());
    }

    void setStageY(double prefHeight, Stage stage) {
        stage.setY((appController.getStage().getHeight() / 2 - prefHeight / 2) + appController.getStage().getY());
    }

}
