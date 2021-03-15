package org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.chunks.color;

import javafx.scene.layout.Pane;
import org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.chunks.StatisticsChunkPane;
import org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.views.PostChartDataProcessor;
import org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.views.base.spec.color.ColorPieChart;
import org.uygar.postit.data.structures.PostItContainer;

import java.util.List;

public class ColoreChunk extends StatisticsChunkPane {

    public ColoreChunk(Pane parentPane, PostItContainer postItContainer) {
        super("Colors", List.of(new ColorPieChart(postItContainer)), parentPane, postItContainer);
    }

}