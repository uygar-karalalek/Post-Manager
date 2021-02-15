package org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.general;

import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.statistical_data.PostStatisticalData;

public interface PostChart {

    Chart getChart();

    ObservableList<?> getData();

    void updateChartData();

}