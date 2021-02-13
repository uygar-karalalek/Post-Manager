package org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts;

import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import org.uygar.postit.data.structures.PostItContainer;

public abstract class GeneralPostXYChart extends GeneralPostChart {

    protected XYChart<String, Number> chart;
    protected final CategoryAxis xAxis;
    protected final NumberAxis yAxis;

    public GeneralPostXYChart(PostItContainer container, String xAxisName, String yAxisName) {
        super(container);

        xAxis = new CategoryAxis();
        initXAxisWithName(xAxisName);

        yAxis = new NumberAxis();
        initYAxisWithName(yAxisName);
    }

    public void updateChartData() {
        chart.setData((ObservableList<XYChart.Series<String, Number>>) getData());
    }

}