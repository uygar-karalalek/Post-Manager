package org.uygar.postit;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.uygar.postit.controllers.application.app_controller.AppController;
import org.uygar.postit.controllers.application.FXLoader;
import org.uygar.postit.controllers.application.app_controller.app_controller_managers.ResizeHelper;
import org.uygar.postit.controllers.application.utils.WindowInitializer;
import org.uygar.postit.data.properties.LogProperties;
import org.uygar.postit.data.properties.PostManagerProperties;

public class Main extends Application {

    public static final int MIN_WIDTH = 600;
    public static final int MIN_HEIGHT = 400;
    public static final int MAX_WIDTH = 1200;
    public static final int MAX_HEIGHT = 700;

    private Stage stage;
    private Scene scene;
    private LogProperties properties;
    private AppController appController;
    private PostManagerProperties applicationProperties;

    @Override
    public void init() {
        initLogProperties();
        applicationProperties = new PostManagerProperties();
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

        this.appController.setAppProperties(applicationProperties);
        this.appController.setLogProperties(properties);

        this.appController.styleManager.setThemeFromAppProperties();

        this.scene = new Scene(this.appController.application);
        this.stage.setScene(scene);

        WindowInitializer.fadeWindowEffect(this.appController.application, 1);

        this.stage.setOnHiding(event -> Platform.exit());
        ResizeHelper.addResizeListener(stage, MIN_WIDTH, MIN_HEIGHT, MAX_WIDTH, MAX_HEIGHT);
        this.stage.show();
    }

    @Override
    public void stop() {
        applicationProperties.putProperty("theme", appController.styleManager.getCurrentStyleCssFilePath());
        applicationProperties.storeProperties();
    }

}