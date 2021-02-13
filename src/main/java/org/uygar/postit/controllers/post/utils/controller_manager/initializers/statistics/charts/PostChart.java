package org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts;

import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.statistical_data.PostStatisticalData;

public interface PostChart {

    void initXAxisWithName(String axisName);
    void initYAxisWithName(String axisName);

    Chart getChart();

    ObservableList<?> getData();

    void updateChartData();


}