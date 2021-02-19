package org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.views;

import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.statistical_data.PostStatisticalData;
import org.uygar.postit.data.structures.PostItContainer;

public abstract class PostChartDataProcessor implements PostChart {

    protected PostStatisticalData postStatisticalData;

    public PostChartDataProcessor(PostItContainer container) {
        this.postStatisticalData = new PostStatisticalData(container);
    }

}