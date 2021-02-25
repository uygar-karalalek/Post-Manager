package org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.Tab;
import org.uygar.postit.controllers.post.PostController;
import org.uygar.postit.controllers.post.controller_utilities.controller_manager.PostControllerWrapper;
import org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.TabInitializer;
import org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.manager.ChartGroupManager;

public class PostStatisticsInitializer extends PostControllerWrapper implements TabInitializer {

    private static final double FLOW_SCROLL_STATISTICS_GAP = 10;

    private final ChartGroupManager manager;

    public PostStatisticsInitializer(PostController postController) {
        super(postController);
        this.manager = new ChartGroupManager(postController);
    }

    @Override
    public void initializeTab() {
        this.postController.statsPane.getChildren().addAll(manager.getChunks());
        this.postController.statsScrollPane.widthProperty().addListener(this::onStatisticsFlowPaneSizeChange);
        this.postController.rootTabPane.getSelectionModel().selectedItemProperty().addListener(this::onStatisticsChartClickedUpdateAllCharts);
    }

    private void onStatisticsFlowPaneSizeChange(ObservableValue<? extends Number> obs, Number oldNum, Number newNum) {
        this.postController.statsPane.setPrefWidth((double) newNum - FLOW_SCROLL_STATISTICS_GAP);
    }

    private void onStatisticsChartClickedUpdateAllCharts(ObservableValue<? extends Tab> obs, Tab oldTab, Tab newTab) {
        if (newTab.getText().equalsIgnoreCase("statistics")) {
            manager.updateAllCharts();
        }
    }

}