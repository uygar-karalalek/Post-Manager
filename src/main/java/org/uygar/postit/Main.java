package org.uygar.postit;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.uygar.postit.controllers.application.app.AppController;
import org.uygar.postit.controllers.application.FXLoader;
import org.uygar.postit.data.properties.LogProperties;
import org.uygar.postit.data.properties.PostProperties;

import java.util.Objects;

public class Main extends Application {

    Stage stage;
    Scene scene;
    PostProperties applicationProperties;
    LogProperties properties;
    AppController appController;

    @Override
    public void init() {
        initLogProperties();
        applicationProperties = new PostProperties();
        applicationProperties.loadProperties();
    }

    private void initLogProperties() {
        this.properties = new LogProperties();
        properties.addHourLog();
        properties.addMonthLog();
        properties.storeProperties();
        this.properties.updateFrequencies();
    }

    @Override
    public void start(Stage stage) {
        this.stage = new Stage();
        this.stage.initStyle(StageStyle.UNDECORATED);
        this.appController = (AppController) FXLoader.getLoadedController("app", "app");
        this.appController.setTheme(applicationProperties.getStringProperty("theme"));
        Objects.requireNonNull(this.appController).setLogProperties(properties);
        this.scene = new Scene(Objects.requireNonNull(this.appController).rootPane);
        addTransition();
        this.stage.setScene(scene);
        this.stage.setOnHiding(event -> Platform.exit());
        this.stage.show();
    }

    private void addTransition() {
        FadeTransition transition = new FadeTransition(Duration.seconds(1), this.appController.rootPane);
        transition.setFromValue(0);
        transition.setToValue(1);
        transition.play();
    }

    @Override
    public void stop() {
        applicationProperties.putProperty("theme", appController.getCurrentStyleCssFilePath());
        applicationProperties.storeProperties();
    }

}