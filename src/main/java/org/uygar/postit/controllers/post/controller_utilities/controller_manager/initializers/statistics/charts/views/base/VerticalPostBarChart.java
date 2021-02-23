package org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.views.base;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import org.uygar.postit.data.structures.PostItContainer;

public abstract class VerticalPostBarChart extends XYPostChart<String, Number> {

    public VerticalPostBarChart(String xAxisName, String yAxisName, PostItContainer container) {
        super(xAxisName, yAxisName, container);
    }

    @Override
    protected void initXAxis(String xAxisName) {
        this.xAxis = new CategoryAxis();
        this.xAxis.setLabel(xAxisName);
    }

    @Override
    protected void initYAxis(String yAxisName) {
        this.yAxis = new NumberAxis();
        this.yAxis.setLabel(yAxisName);
    }

    @Override
    protected void initChart() {
        this.chart = new BarChart<>(xAxis, yAxis);
        this.updateChartData();
    }

}