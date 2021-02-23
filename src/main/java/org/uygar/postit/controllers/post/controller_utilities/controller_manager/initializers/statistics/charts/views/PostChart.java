package org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.views;

import javafx.collections.ObservableList;
import javafx.scene.chart.Chart;

public interface PostChart {

    Chart getChart();

    ObservableList<?> getData();

    void updateChartData();

}