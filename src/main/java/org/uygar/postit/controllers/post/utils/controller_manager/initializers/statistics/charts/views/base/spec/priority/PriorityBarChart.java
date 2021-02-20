package org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.views.base.spec.priority;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.views.base.HorizontalPostBarChart;
import org.uygar.postit.data.structures.PostItContainer;

public class PriorityBarChart extends HorizontalPostBarChart {

    public PriorityBarChart(PostItContainer container, String xAxisName, String yAxisName) {
        super("priority average", "month", container);
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
    public ObservableList<XYChart.Series<Number, String>> getData() {

        return null;
    }

}