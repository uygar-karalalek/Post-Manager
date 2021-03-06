package org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.views.base.spec.priority;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.views.base.HorizontalPostBarChart;
import org.uygar.postit.data.structures.PostItContainer;
import org.uygar.postit.post.PostIt;

import java.time.Month;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Predicate;

public class PriorityBarChart extends HorizontalPostBarChart {

    public PriorityBarChart(PostItContainer container) {
        super("priority average", "month", container);
    }

    @Override
    public ObservableList<XYChart.Series<Number, String>> getData() {
        ObservableList<XYChart.Data<Number, String>> data = FXCollections.observableArrayList();

        Map<String, Integer> creationDateMonthValues = postStatisticalData.getCreationDateMonthsAndValues();

        // I reverse first & second months because the horizontal chart adds the last elements on top
        // and this is not a very comfortable view.
        Comparator<String> monthComparator = (firstMonth, secondMonth) -> Month.valueOf(secondMonth).compareTo(Month.valueOf(firstMonth));

        creationDateMonthValues.keySet().stream().sorted(monthComparator).forEach(keyEntry -> {
            long numberOfThatMonthPostIt = creationDateMonthValues.get(keyEntry);
            int sumOfPostItPriority = postStatisticalData.getSumOfPostField(PostIt::getPriority);
            double avgPriorityOnThatMonth = sumOfPostItPriority * 1.0 / numberOfThatMonthPostIt;

            data.add(new XYChart.Data<>(avgPriorityOnThatMonth, keyEntry));
        });

        return FXCollections.singletonObservableList(new XYChart.Series<>(data));
    }

}