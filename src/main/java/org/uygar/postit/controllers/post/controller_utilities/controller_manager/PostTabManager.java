package org.uygar.postit.controllers.post.controller_utilities.controller_manager;

import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;
import org.uygar.postit.controllers.post.PostController;
import org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.post.PostTabInitializer;
import org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.settings.PostSettingsInitializer;
import org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.PostStatisticsInitializer;

public class PostTabManager extends PostControllerWrapper implements PostControllerManager {

    public PostTabInitializer postTabInitializer;
    public PostSettingsInitializer postSettingsInitializer;
    public PostStatisticsInitializer postStatisticsInitializer;

    public PostTabManager(PostController postController) {
        super(postController);
    }

    @Override
    public void initPostControllerTabs() {
        initPostControllerTab();
        initSettingsControllerTab();
        initStatisticsControllerTab();
    }

    private void initPostControllerTab() {
        this.postTabInitializer = new PostTabInitializer(postController);
        this.postTabInitializer.initializeTab();
    }

    private void initSettingsControllerTab() {
        this.postSettingsInitializer = new PostSettingsInitializer(postController);
        this.postSettingsInitializer.initializeTab();
    }

    private void initStatisticsControllerTab() {
        this.postStatisticsInitializer = new PostStatisticsInitializer(postController);
        this.postStatisticsInitializer.initializeTab();
    }

    public void setMinSizeListenerByDimensionOfStage(Stage stage) {
        stage.widthProperty().addListener(this::onWindowWidthChangeResizePostItGrid);
        stage.heightProperty().addListener(this::onWindowHeightChangeResizePostItGrid);
    }

    private void onWindowWidthChangeResizePostItGrid(ObservableValue<? extends Number> obs, Number oldVal, Number newWidth) {
        if ((double) newWidth < postController.minDimension.getWidth())
            postController.rootTabPane.getScene().getWindow().setWidth(postController.minDimension.getWidth());
    }

    private void onWindowHeightChangeResizePostItGrid(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
        if ((double) newVal < postController.minDimension.getHeight())
            postController.rootTabPane.getScene().getWindow().setHeight(postController.minDimension.getHeight());
    }

}