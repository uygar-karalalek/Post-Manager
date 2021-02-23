package org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.views.base.spec.creation_date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.views.base.VerticalPostBarChart;
import org.uygar.postit.data.structures.PostItContainer;

import java.time.Month;
import java.util.Map;

public class CreationDateBarChart extends VerticalPostBarChart {

    public CreationDateBarChart(PostItContainer container) {
        super("Number of Post-its", "Creation Year", container);
    }

    @Override
    public ObservableList<XYChart.Series<String, Number>> getData() {
        ObservableList<XYChart.Data<String, Number>> data = FXCollections.observableArrayList();
        Map<String, Integer> creationDateMonthCount = postStatisticalData.getCreationDateMonthsAndValues();

        for (String keyEntry : creationDateMonthCount.keySet())
            data.add(new XYChart.Data<>(keyEntry, creationDateMonthCount.get(keyEntry)));

        return FXCollections.singletonObservableList(new XYChart.Series<>(data));
    }

}