package org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.views.base.spec.creation_date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.views.base.VerticalPostBarChart;
import org.uygar.postit.data.structures.PostItContainer;

public class CreationDateBarChart extends VerticalPostBarChart {

    public CreationDateBarChart(PostItContainer container) {
        super("Number of Post-its", "Creation Year", container);
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