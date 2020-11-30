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
        this.properties.addHourLog();
        this.properties.addMonthLog();
        this.properties.storeProperties();
        this.properties.calculateLogStatistics();
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        this.stage.initStyle(StageStyle.UNDECORATED);
        this.appController = (AppController) FXLoader.getLoadedController("app", "app");
        this.appController.setStage(this.stage);
        this.appController.init();
        this.appController.setTheme(applicationProperties.getStringProperty("theme"));
        this.appController.setLogProperties(properties);
        this.scene = new Scene(Objects.requireNonNull(this.appController).application);
        addTransitionToApplicationController();
        this.stage.setScene(scene);
        this.stage.setOnHiding(event -> Platform.exit());
        this.stage.show();
    }

    private void addTransitionToApplicationController() {
        FadeTransition transition = new FadeTransition(Duration.seconds(1), this.appController.application);
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