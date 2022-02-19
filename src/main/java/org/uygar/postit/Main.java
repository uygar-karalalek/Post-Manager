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

import java.util.Optional;

public class Main extends Application {

    public static final double MIN_WIDTH = 600;
    public static final double MIN_HEIGHT = 400;
    public static final double MAX_WIDTH = 1400;
    public static final double MAX_HEIGHT = 800;

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
        initSizeProperties();
        this.stage.show();
    }

    private void initSizeProperties() {
        ResizeHelper.addResizeListener(stage, MIN_WIDTH, MIN_HEIGHT, MAX_WIDTH, MAX_HEIGHT);

        Optional<Double> initialWidth = applicationProperties.getDoubleProperty("window_width");
        this.stage.setWidth(initialWidth.orElse(MIN_WIDTH));

        Optional<Double> initialHeight = applicationProperties.getDoubleProperty("window_height");
        this.stage.setHeight(initialHeight.orElse(MIN_HEIGHT));
    }

    @Override
    public void stop() {
        applicationProperties.putProperty("window_width", Double.toString(this.stage.getWidth()));
        applicationProperties.putProperty("window_height", Double.toString(this.stage.getHeight()));

        applicationProperties.putProperty("theme", appController.styleManager.getCurrentStyleCssFilePath());
        applicationProperties.storeProperties();
    }

    public static void main(String[] args) {
        launch(args);
    }

}