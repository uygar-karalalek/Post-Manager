package org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.chunks.state;

import javafx.scene.layout.Pane;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.chunks.StatisticsChunkPane;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.views.base.spec.state.StateBarChart;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.views.base.spec.state.StatePieChart;
import org.uygar.postit.data.structures.PostItContainer;

import java.util.List;

public class StateChunk extends StatisticsChunkPane {

    public StateChunk(Pane parentPane, PostItContainer postItContainer) {
        super("State", List.of(
                new StateBarChart(postItContainer),
                new StatePieChart(postItContainer)
        ), parentPane, postItContainer);
        super.setId("stateChunk");
        super.setStyle("-fx-background-color: grey");
    }

}