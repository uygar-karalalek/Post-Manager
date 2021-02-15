package org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.chunks.state;

import javafx.scene.layout.StackPane;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.chunks.StatisticsChunkPane;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.general.state.StateBarChart;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.general.state.StatePieChart;
import org.uygar.postit.data.structures.PostItContainer;

import java.util.List;

public class StateChunk extends StatisticsChunkPane {

    public StateChunk(PostItContainer postItContainer) {
        super("State", List.of(
                new StateBarChart(postItContainer),
                new StatePieChart(postItContainer)
        ), postItContainer);
    }

}