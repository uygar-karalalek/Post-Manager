package org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.views.base;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.views.PostChartDataProcessor;
import org.uygar.postit.data.structures.PostItContainer;

public abstract class PiePostChart extends PostChartDataProcessor {

    protected PieChart chart;

    public PiePostChart(PostItContainer container) {
        super(container);
        this.initChart();
    }

    protected abstract void initChart();

    @Override
    public PieChart getChart() {
        return chart;
    }

    @Override
    public abstract ObservableList<PieChart.Data> getData();

    @Override
    public void updateChartData() {
        // layout() method forces the hbox to layout pichart labels, that
        // without the call go to the coordinate (0,0) of the box.
        chart.layout();
        chart.setData(getData());
        chart.layout();
    }

}