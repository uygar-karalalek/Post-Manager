package org.uygar.postit.controllers.application.statistica.utility.grafico;

import javafx.scene.text.Text;
import org.uygar.postit.data.properties.LogProperties;

import java.util.Collection;
import java.util.DoubleSummaryStatistics;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class StatisticaGrafico implements Statistica {

    private Collection<Integer> frequencyValues;
    private DoubleSummaryStatistics statistics;

    public StatisticaGrafico(TipoGrafico tipoGrafico, LogProperties properties) {
        this.frequencyValues = tipoGrafico.getFrequence(properties);
        statistics = frequencyValues.stream().collect(summarizingDouble(value -> value));
    }

    @Override
    public int getModa() {
        ConcurrentMap<Integer, Long> valueFrequencies = frequencyValues.stream()
                .collect(groupingByConcurrent(Integer::intValue, counting()));

        return valueFrequencies.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).
                orElseThrow();
    }

    @Override
    public int getMin() {
        return (int) statistics.getMin();
    }

    @Override
    public int getMax() {
        return (int) statistics.getMax();
    }

    @Override
    public double getMedia() {
        return statistics.getAverage();
    }

    @Override
    public String toString() {
        Function<Integer, String> valore_valori = val -> val == 1 ? val + " volta" : val + " volte";

        String max = "Valore massimo: " + valore_valori.apply(getMax());
        String min = "Valore minimo: " + valore_valori.apply(getMin());
        String moda = "Moda: " + valore_valori.apply(getModa());
        String media = String.format("Media: %.2f volte", getMedia());

        return max + "," + min + "," + moda + "," + media;
    }

    public String[] getDataArray() {
        return toString().split(",");
    }

    public Text[] getDataAsTextNodes() {
        String[] stringData = getDataArray();
        Text[] nodes = new Text[stringData.length];

        for (int i = 0; i < stringData.length; i++) {
            nodes[i] = new Text(stringData[i]);
            nodes[i].setId("textDati");
        }

        return nodes;
    }

}