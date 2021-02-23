package org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.views.base;

import javafx.collections.ObservableList;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.Chart;
import javafx.scene.chart.XYChart;
import org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.views.PostChartDataProcessor;
import org.uygar.postit.data.structures.PostItContainer;

public abstract class XYPostChart<X, Y> extends PostChartDataProcessor {

    protected Axis<X> xAxis;
    protected Axis<Y> yAxis;

    protected BarChart<X, Y> chart;

    public XYPostChart(String xAxisName, String yAxisName, PostItContainer container) {
        super(container);
        initXAxis(xAxisName);
        initYAxis(yAxisName);
        initChart();
    }

    protected abstract void initXAxis(String xAxisName);
    protected abstract void initYAxis(String yAxisName);
    protected abstract void initChart();

    @Override
    public Chart getChart() {
        return chart;
    }

    @Override
    public void updateChartData() {
        this.chart.setData(getData());
    }

    @Override
    public abstract ObservableList<XYChart.Series<X, Y>> getData();

}