package org.uygar.postit.controllers.post.controller_utilities;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

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