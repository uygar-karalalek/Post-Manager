package org.uygar.postit;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.uygar.postit.controllers.app.FXLoader;
import org.uygar.postit.data.properties.LogProperties;

import java.util.Objects;

public class Main extends Application {

    Stage stage;
    BorderPane root;
    Scene scene;
    LogProperties properties;

    @Override
    public void init() {
        this.properties = new LogProperties();
        properties.addHourLog();
        properties.addMonthLog();
        properties.storeProperties();
    }

    @Override
    public void start(Stage stage) {
        this.stage = new Stage();
        this.root = (BorderPane) FXLoader.getLoadedParent("app", "app");
        this.scene = new Scene(Objects.requireNonNull(root));
        addTransition();
        this.stage.setScene(scene);
        this.stage.show();
    }

    private void addTransition() {
        FadeTransition transition = new FadeTransition(Duration.seconds(1), this.root);
        transition.setFromValue(0);
        transition.setToValue(1);
        transition.play();
    }

}