package org.uygar.postit.controllers.app.statistica;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.jetbrains.annotations.NotNull;
import org.uygar.postit.data.properties.LogProperties;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StatisticaUtils {

    @NotNull
    public static NumberAxis getOreAxis() {
        NumberAxis oreScatter = new NumberAxis();
        oreScatter.setLabel("Ore");
        oreScatter.setLowerBound(0);
        oreScatter.setUpperBound(23);
        oreScatter.setMinorTickLength(1);
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
        IntStream.iterate(0, n -> n+1).limit(23).forEachOrdered(value -> {
            String nTimes = properties.getStringProperty(value + "");
            if (!nTimes.equals("0"))
                dataList.add(new XYChart.Data<>(value, Integer.valueOf(nTimes)));
        });
        return dataList;
    }


    @NotNull
    public static List<XYChart.Data<String, Number>> getStringHourFrequence(LogProperties properties) {
        List<XYChart.Data<String, Number>> dataList = new ArrayList<>();
        for (int i = 0; i < 23; i++) {
            String nTimes = properties.getStringProperty(i + "");
            if (nTimes.equals("0"))
                continue;
            dataList.add(new XYChart.Data<>(Integer.toString(i), Integer.valueOf(nTimes)));
        }
        return dataList;
    }

    @NotNull
    public static List<XYChart.Data<String, Number>> getMonthFrequence(LogProperties properties) {
        List<XYChart.Data<String, Number>> dataList = new ArrayList<>();
        for (Month month : Month.values()) {
            String nTimes = properties.getStringProperty(month.name());
            double frequence = Double.parseDouble(nTimes);
            dataList.add(new XYChart.Data<>(month.name(), frequence/10));
        }
        return dataList;
    }

}