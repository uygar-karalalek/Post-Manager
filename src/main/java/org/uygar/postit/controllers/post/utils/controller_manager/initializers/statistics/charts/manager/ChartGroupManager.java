package org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.manager;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import org.uygar.postit.controllers.post.PostController;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.StateChart;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.chunks.StatisticsChunk;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.chunks.StatisticsChunkPane;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.chunks.state.StateChunk;

import java.util.ArrayList;
import java.util.List;

public class ChartGroupManager {

    private final List<StatisticsChunkPane> chunks;

    public ChartGroupManager(PostController postController) {
        this.chunks = new ArrayList<>();
        this.chunks.add(new StateChunk(postController.postItGrid.getPostItOrganizer()));
    }

    public void updateAllCharts() {
        chunks.forEach(StatisticsChunk::updateCharts);
    }

    public List<StatisticsChunkPane> getChunks() {
        return chunks;
    }

}