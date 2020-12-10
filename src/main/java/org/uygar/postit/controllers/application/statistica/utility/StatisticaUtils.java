package org.uygar.postit.controllers.application.statistica.utility;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.jetbrains.annotations.NotNull;
import org.uygar.postit.data.properties.LogProperties;

import java.time.Month;
import java.util.*;

public class StatisticaUtils {

    public static NumberAxis getOreAxis() {
        NumberAxis oreScatter = new NumberAxis();
        oreScatter.setLabel("Ore");
        oreScatter.setLowerBound(-1);
        oreScatter.setUpperBound(24);
        oreScatter.setTickUnit(1);
        oreScatter.setAutoRanging(false);
        return oreScatter;
    }

    public static NumberAxis getFrequenzaAxis(int lower, int upper, int unit, String title) {
        NumberAxis frequenza = new NumberAxis();
        frequenza.setLabel(title);
        frequenza.setLowerBound(lower);
        frequenza.setUpperBound(upper);
        frequenza.setTickUnit(unit);
        frequenza.setAutoRanging(false);
        return frequenza;
    }

    public static CategoryAxis getMeseAxis() {
        CategoryAxis axis = new CategoryAxis();
        axis.setLabel("Mesi");
        for (Month element : Month.values())
            axis.getCategories().add(element.name());
        return axis;
    }

    public static List<XYChart.Data<Number, Number>> getHourFrequencyChartData(LogProperties properties) {
        List<XYChart.Data<Number, Number>> dataList = new ArrayList<>();

        properties.getHoursAndFrequencies().forEach((hour, frequency) -> {
            if (frequency != 0)
                dataList.add(new XYChart.Data<>(hour, frequency));
        });

        return dataList;
    }

    public static List<XYChart.Data<String, Number>> getMonthFrequenceChartData(LogProperties properties) {
        List<XYChart.Data<String, Number>> dataList = new ArrayList<>();
        for (Month month : Month.values()) {
            String nTimes = properties.getStringProperty(month.name());
            double frequency = Double.parseDouble(nTimes);
            dataList.add(new XYChart.Data<>(month.name(), frequency / 10));
        }
        return dataList;
    }

}