package org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.views.base.spec.creation_date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.views.base.PiePostChart;
import org.uygar.postit.data.structures.PostItContainer;

import java.time.Month;
import java.util.Map;

public class CreationDatePieChart extends PiePostChart {

    public CreationDatePieChart(PostItContainer container) {
        super(container);
    }

    @Override
    protected void initChart() {
        this.chart = new PieChart();
        updateChartData();
    }

    @Override
    public ObservableList<PieChart.Data> getData() {
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList();

        Map<String, Integer> creationDateMonthValues = postStatisticalData.getCreationDateMonthsAndValues();
        for (String keyEntry : creationDateMonthValues.keySet())
            list.add(new PieChart.Data(keyEntry, creationDateMonthValues.get(keyEntry)));

        return list;
    }

}