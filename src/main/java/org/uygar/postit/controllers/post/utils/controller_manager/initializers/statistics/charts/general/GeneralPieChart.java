package org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.general;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import org.uygar.postit.data.structures.PostItContainer;

public abstract class GeneralPieChart extends GeneralPostChart {

    protected PieChart chart;

    public GeneralPieChart(PostItContainer container) {
        super(container);
    }

    @Override
    public void updateChartData() {
        // layout() method forces the hbox to layout pichart labels, that
        // without the call go to the coordinate (0,0) of the box.

        chart.layout();
        chart.setData((ObservableList<PieChart.Data>) getData());
        chart.layout();
    }

    @Override
    public PieChart getChart() {
        return chart;
    }

}