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
import org.uygar.postit.controllers.application.app.ResizeHelper;
import org.uygar.postit.controllers.application.utils.WindowInitializer;
import org.uygar.postit.controllers.loader.WindowLoader;
import org.uygar.postit.data.properties.LogProperties;
import org.uygar.postit.data.properties.PostProperties;

public class Main extends Application {

    private Stage stage;
    private Scene scene;
    private LogProperties properties;
    private AppController appController;
    private PostProperties applicationProperties;

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
        this.appController.styleManager.setTheme(applicationProperties.getStringProperty("theme"));
        this.appController.setLogProperties(properties);

        this.scene = new Scene(this.appController.application);
        this.stage.setScene(scene);

        WindowInitializer.fadeWindowEffect(this.appController.application, 1);

        this.stage.setOnHiding(event -> Platform.exit());
        ResizeHelper.addResizeListener(stage);
        this.stage.show();
    }

    @Override
    public void stop() {
        applicationProperties.putProperty("theme", appController.styleManager.getCurrentStyleCssFilePath());
        applicationProperties.storeProperties();
    }

}