package org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.chunks.priority;

import javafx.scene.layout.Pane;
import org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.chunks.StatisticsChunkPane;
import org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.views.PostChartDataProcessor;
import org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.views.base.spec.priority.PriorityBarChart;
import org.uygar.postit.data.structures.PostItContainer;

import java.util.List;

public class PriorityChunk extends StatisticsChunkPane {

    public PriorityChunk(Pane parentPane, PostItContainer postItContainer) {
        super("Priority - month relationship", List.of(new PriorityBarChart(postItContainer)), parentPane, postItContainer);
    }

}
