package org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.views.base.spec.creation_date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.views.base.VerticalPostBarChart;
import org.uygar.postit.data.structures.PostItContainer;

import java.time.Month;

public class CreationDateBarChart extends VerticalPostBarChart {

    public CreationDateBarChart(PostItContainer container) {
        super("Number of Post-its", "Creation Year", container);
    }

    @Override
    public ObservableList<XYChart.Series<String, Number>> getData() {
        ObservableList<XYChart.Data<String, Number>> data = FXCollections.observableArrayList();
        for (Month month : postStatisticalData.getCreationDateMonths()) {
            data.add(new XYChart.Data<>(month.toString(), postStatisticalData.countPostItBasedOnCreationYear(month)));
        }

        return FXCollections.singletonObservableList(new XYChart.Series<>(data));
    }

}