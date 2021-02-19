package org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.chunks.creation_date;

import javafx.scene.layout.Pane;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.chunks.StatisticsChunkPane;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.views.base.spec.creation_date.CreationDateBarChart;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.views.base.spec.creation_date.CreationDatePieChart;
import org.uygar.postit.data.structures.PostItContainer;

import java.util.List;

public class CreationDateChunk extends StatisticsChunkPane {

    public CreationDateChunk(Pane parentPane, PostItContainer postItContainer) {
        super("Creation Date", List.of(new CreationDatePieChart(postItContainer),
                new CreationDateBarChart(postItContainer)
                ), parentPane, postItContainer);
        super.setId("creationDateChunk");
    }

}