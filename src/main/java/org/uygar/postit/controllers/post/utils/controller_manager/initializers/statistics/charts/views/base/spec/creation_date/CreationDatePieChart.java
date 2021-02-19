package org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.views.base.spec.creation_date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.views.base.PiePostChart;
import org.uygar.postit.data.structures.PostItContainer;

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

        for (Integer year : postStatisticalData.getCreationDateYears()) {
            list.add(new PieChart.Data(year.toString(), postStatisticalData.countPostItBasedOnCreationYear(year)));
        }

        return list;
    }

}