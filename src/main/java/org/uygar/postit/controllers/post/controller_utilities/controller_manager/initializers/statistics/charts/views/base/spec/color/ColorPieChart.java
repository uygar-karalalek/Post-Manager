package org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.views.base.spec.color;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.views.base.PiePostChart;
import org.uygar.postit.data.structures.PostItContainer;

import java.util.Map;

public class ColorPieChart extends PiePostChart {

    public ColorPieChart(PostItContainer container) {
        super(container);
    }

    @Override
    protected void initChart() {
        this.chart = new PieChart();
        this.updateChartData();
    }

    @Override
    public ObservableList<PieChart.Data> getData() {
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList();

        Map<String, Integer> colorCounts = postStatisticalData.getColorCounts();
        for (String keyEntry : colorCounts.keySet())
            list.add(new PieChart.Data(keyEntry, colorCounts.get(keyEntry)));

        return list;
    }

}