package org.uygar.postit.controllers.post.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;

public class PostStatisticsViewManager {

    public static void buildChart(PieChart pieChart, PostStatistics postStatistics) {
        pieChart.setTitle("Statistiche");
        ObservableList<PieChart.Data> observableData = FXCollections.observableArrayList();
        observableData.add(new PieChart.Data("Finito", postStatistics.getNumOfDonePercentage()));
        observableData.add(new PieChart.Data("Da fare", postStatistics.getNumOfUndonePercentage()));
        pieChart.setData(observableData);
    }

}