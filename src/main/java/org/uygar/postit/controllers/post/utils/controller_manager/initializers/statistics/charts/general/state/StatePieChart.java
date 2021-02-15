package org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.general.state;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.general.GeneralPieChart;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.general.GeneralPostChart;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.statistical_data.PostStatisticalData;
import org.uygar.postit.data.structures.PostItContainer;
import org.uygar.postit.post.PostIt;

public class StatePieChart extends GeneralPieChart {

    public StatePieChart(PostItContainer container) {
        super(container);
        chart = new PieChart();
        updateChartData();
        chart.layout();
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