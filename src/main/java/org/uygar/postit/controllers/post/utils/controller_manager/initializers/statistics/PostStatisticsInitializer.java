package org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.Tab;
import org.uygar.postit.controllers.post.PostController;
import org.uygar.postit.controllers.post.utils.controller_manager.PostControllerWrapper;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.TabInitializer;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.manager.ChartGroupManager;

public class PostStatisticsInitializer extends PostControllerWrapper implements TabInitializer {

    private final ChartGroupManager manager;

    public PostStatisticsInitializer(PostController postController) {
        super(postController);
        this.manager = new ChartGroupManager(postController);
    }

    @Override
    public void initializeTab() {
        this.postController.statsPane.getChildren().addAll(manager.getChunks());
        this.postController.rootTabPane.getSelectionModel().selectedItemProperty().addListener(this::onStatisticsChartClickedUpdateAllCharts);
    }

    private void onStatisticsChartClickedUpdateAllCharts(ObservableValue<? extends Tab> obs, Tab oldTab, Tab newTab) {
        if (newTab.getText().equalsIgnoreCase("statistics")) {
            manager.updateAllCharts();
        }
    }

}