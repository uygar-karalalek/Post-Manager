package org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.views.base.spec.state;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.views.base.HorizontalPostBarChart;
import org.uygar.postit.data.structures.PostItContainer;
import org.uygar.postit.post.PostIt;

import java.util.ArrayList;
import java.util.List;

public class StateBarChart extends HorizontalPostBarChart {

    public StateBarChart(PostItContainer container) {
        super("State", "Recurrence", container);
    }

    @Override
    public ObservableList<XYChart.Series<Number, String>> getData() {
        List<XYChart.Data<Number, String>> data = new ArrayList<>();

        long numberOfDonePostIts = postStatisticalData.getNumberOfPostItBasedOnStateCondition(PostIt::isFatto);
        long numberOfUnDonePostIts = postStatisticalData.getNumberOfPostItBasedOnStateCondition(PostIt::isUndone);
        long numberOfExpiredPostIts = postStatisticalData.getNumberOfPostItBasedOnStateCondition(PostIt::isExpiredAndTodo);

        data.add(new XYChart.Data<>(numberOfDonePostIts, "Done"));
        data.add(new XYChart.Data<>(numberOfUnDonePostIts, "Todo"));

        // If we have no expired post-its, then the chart does not show the expired post-its
        if (numberOfExpiredPostIts > 0) data.add(new XYChart.Data<>(numberOfExpiredPostIts, "Expired"));

        XYChart.Series<Number, String> statesData = new XYChart.Series<>();
        statesData.getData().addAll(data);

        return FXCollections.singletonObservableList(statesData);
    }

}