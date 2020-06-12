package org.uygar.postit;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.uygar.postit.controllers.app.FXLoader;

import java.io.IOException;

public class Main extends Application {

    Stage stage;
    VBox root;
    Scene scene;

    @Override
    public void start(Stage stage) {
        this.stage = new Stage();
        this.stage.setWidth(898);
        this.stage.setHeight(740);
        System.out.println(getClass().getClassLoader().getResource("org/uygar/fxml/app/app.fxml"));
        this.root = (VBox) FXLoader.getLoadedParent("app", "app");
        this.scene = new Scene(root);
        this.stage.setScene(scene);
        this.stage.heightProperty().addListener(this::onHeightChanged);
        this.stage.show();
    }

    public void onHeightChanged(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
        HBox hBox = (HBox) root.getChildren().filtered(node -> node.getId().equals("postBox")).get(0);
        hBox.setPrefHeight(hBox.getPrefHeight() + ((double) oldVal + (double) newVal));
    }

}