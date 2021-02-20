package org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.views.base;

import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.uygar.postit.data.structures.PostItContainer;

public abstract class HorizontalPostBarChart extends XYPostChart<Number, String> {

    public HorizontalPostBarChart(String xAxisName, String yAxisName, PostItContainer container) {
        super(xAxisName, yAxisName, container);
    }

    @Override
    protected void initXAxis(String xAxisName) {
        this.xAxis = new NumberAxis();
        this.xAxis.setLabel(xAxisName);
    }

    @Override
    protected void initYAxis(String yAxisName) {
        this.yAxis = new CategoryAxis();
        this.yAxis.setLabel(yAxisName);
    }

    @Override
    protected void initChart() {
        this.chart = new BarChart<>(xAxis, yAxis);
        this.updateChartData();
    }

}