package org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts;

import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.statistical_data.PostStatisticalData;
import org.uygar.postit.data.structures.PostItContainer;

public abstract class GeneralPostChart implements PostChart {

    protected PostStatisticalData postStatisticalData;

    public GeneralPostChart(PostItContainer container) {
        this.postStatisticalData = new PostStatisticalData(container);
    }

}