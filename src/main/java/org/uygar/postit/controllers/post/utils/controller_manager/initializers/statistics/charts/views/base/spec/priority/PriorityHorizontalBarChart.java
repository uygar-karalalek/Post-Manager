package org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.views.base.spec.priority;

import javafx.collections.ObservableList;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.views.base.VerticalPostBarChart;
import org.uygar.postit.data.structures.PostItContainer;

public class PriorityHorizontalBarChart extends VerticalPostBarChart {

    public PriorityHorizontalBarChart(PostItContainer container, String xAxisName, String yAxisName) {
        super(xAxisName, yAxisName, container);
    }

    @Override
    protected void initXAxis(String axisName) {
        this.xAxis.setLabel(axisName);
    }

    @Override
    protected void initYAxis(String axisName) {
        this.yAxis.setLabel(axisName);
    }

    @Override
    public ObservableList<?> getData() {
        return null;
    }

}