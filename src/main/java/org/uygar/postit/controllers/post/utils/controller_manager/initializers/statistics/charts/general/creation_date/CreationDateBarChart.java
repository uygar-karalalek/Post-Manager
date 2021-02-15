package org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.general.creation_date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.general.GeneralPostXYChart;
import org.uygar.postit.data.structures.PostItContainer;

public class CreationDateBarChart extends GeneralPostXYChart {

    public CreationDateBarChart(PostItContainer container) {
        super(container, "Creation Year", "Number of Post-its");
        chart = new BarChart<>(xAxis, yAxis);
        updateChartData();
    }

    @Override
    protected void initXAxisWithName(String axisName) {
        xAxis.setLabel(axisName);
    }

    @Override
    protected void initYAxisWithName(String axisName) {
        yAxis.setLabel(axisName);
    }

    @Override
    public ObservableList<XYChart.Series<String, Integer>> getData() {
        ObservableList<XYChart.Data<String, Integer>> data = FXCollections.observableArrayList();
        for (Integer year : postStatisticalData.getCreationDateYears()) {
            data.add(new XYChart.Data<>(year.toString(), postStatisticalData.countPostItBasedOnCreationYear(year)));
        }

        return FXCollections.singletonObservableList(new XYChart.Series<>(data));
    }

}