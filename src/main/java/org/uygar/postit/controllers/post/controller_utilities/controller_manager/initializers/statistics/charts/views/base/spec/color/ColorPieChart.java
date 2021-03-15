package org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.views.base.spec.color;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.views.base.PiePostChart;
import org.uygar.postit.data.structures.PostItContainer;
import org.uygar.postit.post.properties.Colore;

import java.util.Map;

public class ColorPieChart extends PiePostChart {

    public ColorPieChart(PostItContainer container) {
        super(container);
    }

    @Override
    protected void initChart() {
        this.chart = new PieChart();
        this.chart.setLegendVisible(false);
        this.updateChartData();
        this.initChartColors();
    }

    @Override
    public ObservableList<PieChart.Data> getData() {
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList();

        Map<String, Integer> colorCounts = postStatisticalData.getColorCounts();
        for (String keyEntry : colorCounts.keySet()) {
            PieChart.Data data = new PieChart.Data(keyEntry, colorCounts.get(keyEntry));
            list.add(data);
        }

        return list;
    }

    @Override
    public void updateChartData() {
        super.updateChartData();
        initChartColors();
    }

    private void initChartColors() {
        for (PieChart.Data data : chart.getData()) {
            // The name of the data is the same of the Color Enum element name.
            data.getNode().setStyle("-fx-pie-color: " + Colore.valueOf(data.getName()).getPostItColorWebFormat()+";");
        }
    }

}