package org.uygar.postit;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.uygar.postit.controllers.app.FXLoader;

import java.io.IOException;

public class Main extends Application {

    Stage stage;
    BorderPane root;
    Scene scene;

    @Override
    public void start(Stage stage) {
        this.stage = new Stage();
        System.out.println(getClass().getClassLoader().getResource("org/uygar/fxml/app/app.fxml"));
        this.root = (BorderPane) FXLoader.getLoadedParent("app", "app");
        this.scene = new Scene(root);
        addTransition();
        this.stage.setScene(scene);
        //this.stage.heightProperty().addListener(this::onHeightChanged);
        this.stage.show();
    }

    private void addTransition() {
        FadeTransition transition = new FadeTransition(Duration.seconds(1), this.root);
        transition.setFromValue(0);
        transition.setToValue(1);
        transition.play();
    }

    public void onHeightChanged(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
        HBox hBox = (HBox) root.getChildren().filtered(node -> node.getId().equals("postBox")).get(0);
        hBox.setPrefHeight(hBox.getPrefHeight() + ((double) oldVal + (double) newVal));
    }

}