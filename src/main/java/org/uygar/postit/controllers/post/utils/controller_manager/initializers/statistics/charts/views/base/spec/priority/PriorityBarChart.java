package org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.views.base.spec.priority;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.views.base.HorizontalPostBarChart;
import org.uygar.postit.data.structures.PostItContainer;
import org.uygar.postit.post.PostIt;

import java.time.Month;
import java.util.function.Predicate;

public class PriorityBarChart extends HorizontalPostBarChart {

    public PriorityBarChart(PostItContainer container, String xAxisName, String yAxisName) {
        super("priority average", "month", container);
    }

    @Override
    protected void initXAxis(String axisName) {
        this.xAxis.setLabel(axisName);
    }

    @Override
    protected void initYAxis(String axisName) {
        this.yAxis.setLabel(axisName);
    }

    @Override
    public ObservableList<XYChart.Series<Number, String>> getData() {
        ObservableList<XYChart.Data<Number, String>> data = FXCollections.observableArrayList();

        for (Month month : postStatisticalData.getCreationDateMonths()) {

            Predicate<PostIt> condition = postIt -> postIt.getDataCreazione().getMonth() == month;

            long numberOfThatMonthPostIt = postStatisticalData.getNumberOfPostItBasedOnStateCondition(condition);
            int sumOfPostItPriority = postStatisticalData.getSumOfPostField(PostIt::getPriority);
            double avgPriorityOnThatMonth = sumOfPostItPriority * 1.0 / numberOfThatMonthPostIt;

            data.add(new XYChart.Data<>(avgPriorityOnThatMonth, month.toString()));

        }

        return FXCollections.singletonObservableList(new XYChart.Series<>(data));
    }

}