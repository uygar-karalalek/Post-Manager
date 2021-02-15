package org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.chunks.creation_date;

import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.chunks.StatisticsChunkPane;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.general.GeneralPostChart;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.general.creation_date.CreationDateBarChart;
import org.uygar.postit.data.structures.PostItContainer;

import java.util.List;

public class CreationDateChunk extends StatisticsChunkPane {

    public CreationDateChunk(PostItContainer postItContainer) {
        super("Creation Date", List.of(new CreationDateBarChart(postItContainer)), postItContainer);
    }

}