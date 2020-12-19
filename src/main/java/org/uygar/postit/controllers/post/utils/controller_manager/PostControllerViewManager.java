package org.uygar.postit.controllers.post.utils.controller_manager;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Dimension2D;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.uygar.postit.controllers.BaseController;
import org.uygar.postit.controllers.post.PostController;
import org.uygar.postit.controllers.post.utils.PostStatistics;
import org.uygar.postit.controllers.post.utils.PostStatisticsViewManager;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.post.PostTabInitializer;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.settings.PostSettingsInitializer;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.viewers.post_it.PostItGridViewer;

import java.util.Locale;

public class PostControllerViewManager extends PostControllerWrapper implements PostControllerManager {

    public PostControllerViewManager(PostController postController) {
        super(postController);
    }

    @Override
    public void initPostControllerTab() {
        PostTabInitializer postTabInitializer = new PostTabInitializer(postController);
        postTabInitializer.initializeTab();
    }

    @Override
    public void initSettingsControllerTab() {
        PostSettingsInitializer postSettingsInitializer = new PostSettingsInitializer(postController);
        postSettingsInitializer.initializeTab();
    }

    @Override
    public void initStatisticsControllerTab() {
        // NOT SUPPORTED YET
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