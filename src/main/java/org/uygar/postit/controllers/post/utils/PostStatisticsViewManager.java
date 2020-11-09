package org.uygar.postit.controllers.post.utils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.paint.Color;

public class PostStatisticsViewManager {

    public static void buildChart(PieChart pieChart, PostStatistics postStatistics) {
        pieChart.setTitle("Statistiche");
        pieChart.setData(getData(postStatistics));
        bindChartData(pieChart, postStatistics);
    }

    private static void bindChartData(PieChart pieChart, PostStatistics postStatistics) {
        ChangeListener<? super Number> change = (obs, oldVal, newVal) -> pieChart.setData(getData(postStatistics));
        postStatistics.numOfDonePercentageProperty().addListener(change);
        postStatistics.numOfUndonePercentageProperty().addListener(change);
    }

    private static ObservableList<PieChart.Data> getData(PostStatistics postStatistics) {
        ObservableList<PieChart.Data> observableData = FXCollections.observableArrayList();
        observableData.add(new PieChart.Data("Finito", postStatistics.numOfDonePercentageProperty().get()));
        observableData.add(new PieChart.Data("Da fare", postStatistics.numOfUndonePercentageProperty().get()));
        return observableData;
    }

}