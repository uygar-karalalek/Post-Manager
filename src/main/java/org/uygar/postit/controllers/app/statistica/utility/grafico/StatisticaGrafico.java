package org.uygar.postit.controllers.app.statistica.utility.grafico;

import org.uygar.postit.data.properties.LogProperties;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StatisticaGrafico implements Statistica {

    private Collection<Integer> values;

    public StatisticaGrafico(TipoGrafico tipoGrafico, LogProperties properties) {
        this.values = tipoGrafico.getFrequence(properties);
    }

    @Override
    public int getModa() {
        ConcurrentHashMap<Integer, Integer> valueFrequences = new ConcurrentHashMap<>();

        values.forEach(element -> {
            if (valueFrequences.containsKey(element))
                valueFrequences.put(element, valueFrequences.get(element)+1);
            else
                valueFrequences.put(element, 0);
        });

        return valueFrequences.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).
                orElseThrow();
    }

    @Override
    public int getMin() {
        return values.stream().min(Integer::compareTo).orElseThrow();
    }

    @Override
    public int getMax() {
        return valuesStream().max().orElseThrow();
    }

    @Override
    public double getMedia() {
        return valuesStream().average().orElseThrow();
    }

    private IntStream valuesStream() {
        return values.stream().mapToInt(Integer::intValue);
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

}