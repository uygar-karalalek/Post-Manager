package org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.views.base.spec.state;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.views.PostChartDataProcessor;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.views.base.PiePostChart;
import org.uygar.postit.data.structures.PostItContainer;
import org.uygar.postit.post.PostIt;

public class StatePieChart extends PiePostChart {

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
        list.add(new PieChart.Data("Done", postStatisticalData.getNumberOfPostItBasedOnStateCondition(PostIt::isFatto)));
        list.add(new PieChart.Data("Todo", postStatisticalData.getNumberOfPostItBasedOnStateCondition(PostIt::isUndone)));
        list.add(new PieChart.Data("Expired", postStatisticalData.getNumberOfPostItBasedOnStateCondition(PostIt::isExpiredAndTodo)));
        return list;
    }

}