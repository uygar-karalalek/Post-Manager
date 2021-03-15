package org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.views.base.spec.state;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.views.base.PiePostChart;
import org.uygar.postit.data.structures.PostItContainer;
import org.uygar.postit.post.PostIt;

public class StatePieChart extends PiePostChart {

    private final long
            donePostIts = postStatisticalData.getNumberOfPostItBasedOnStateCondition(PostIt::isFatto),
            toDoPostIts = postStatisticalData.getNumberOfPostItBasedOnStateCondition(PostIt::isUndone),
            expiredPostIts = postStatisticalData.getNumberOfPostItBasedOnStateCondition(PostIt::isExpiredAndTodo);

    public StatePieChart(PostItContainer container) {
        super(container);
    }

    @Override
    protected void initChart() {
        this.chart = new PieChart();
        this.updateChartData();
    }

    @Override
    public PieChart getChart() {
        return chart;
    }

    @Override
    public ObservableList<PieChart.Data> getData() {
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList();

        list.add(new PieChart.Data("Done", donePostIts));
        list.add(new PieChart.Data("Todo", toDoPostIts));
        list.add(new PieChart.Data("Expired", expiredPostIts));
        return list;
    }

}