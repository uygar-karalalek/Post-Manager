package org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.views;

import javafx.collections.ObservableList;
import javafx.scene.chart.Chart;

public interface PostChart {

    Chart getChart();

    ObservableList<?> getData();

    void updateChartData();

}