package org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import org.uygar.postit.data.structures.PostItContainer;
import org.uygar.postit.post.PostIt;

import java.util.ArrayList;
import java.util.List;

public class StateChart extends GeneralPostXYChart {

    public StateChart(PostItContainer container) {
        super(container, "PostIt State", "Recurrence");
        chart = new BarChart<>(xAxis, yAxis);
        chart.setData(getData());
    }

    @Override
    public void initXAxisWithName(String axisName) {
        xAxis.setLabel(axisName);
        xAxis.setTickLabelRotation(5);
    }

    @Override
    public void initYAxisWithName(String axisName) {
        yAxis.setLabel(axisName);
    }

    @Override
    public Chart getChart() {
        return this.chart;
    }

    @Override
    public ObservableList<XYChart.Series<String, Number>> getData() {
        List<XYChart.Data<String, Number>> data = List.of(
                new XYChart.Data<>("Done",postStatisticalData.getNumberOfPostItBasedOnStateCondition(PostIt::isFatto)),
                new XYChart.Data<>("Todo", postStatisticalData.getNumberOfPostItBasedOnStateCondition(PostIt::isUndone)),
                new XYChart.Data<>("Expired", postStatisticalData.getNumberOfPostItBasedOnStateCondition(PostIt::isExpired)));

        XYChart.Series<String, Number> statesData = new XYChart.Series<>();
        statesData.getData().addAll(data);

        return FXCollections.singletonObservableList(statesData);
    }

}