package org.uygar.postit.controllers.app.statistica.utility;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.jetbrains.annotations.NotNull;
import org.uygar.postit.data.properties.LogProperties;

import java.time.Month;
import java.util.*;

public class StatisticaUtils {

    @NotNull
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

    @NotNull
    public static CategoryAxis getMeseAxis() {
        CategoryAxis axis = new CategoryAxis();
        axis.setLabel("Mesi");
        for (Month element : Month.values())
            axis.getCategories().add(element.name());
        return axis;
    }

    @NotNull
    public static List<XYChart.Data<Number, Number>> getHourFrequence(LogProperties properties) {
        List<XYChart.Data<Number, Number>> dataList = new ArrayList<>();

        properties.getHoursAndFrequencies().forEach((hour, frequence) -> {
            if (frequence != 0)
                dataList.add(new XYChart.Data<>(hour, frequence));
        });

        return dataList;
    }

    @NotNull
    public static List<XYChart.Data<String, Number>> getMonthFrequence(LogProperties properties) {
        List<XYChart.Data<String, Number>> dataList = new ArrayList<>();
        for (Month month : Month.values()) {
            String nTimes = properties.getStringProperty(month.name());
            double frequence = Double.parseDouble(nTimes);
            dataList.add(new XYChart.Data<>(month.name(), frequence / 10));
        }
        return dataList;
    }

}