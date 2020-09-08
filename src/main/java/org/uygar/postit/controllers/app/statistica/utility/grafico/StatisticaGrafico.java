package org.uygar.postit.controllers.app.statistica.utility.grafico;

import javafx.scene.text.Text;
import org.uygar.postit.data.properties.LogProperties;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.IntStream;

public class StatisticaGrafico implements Statistica {

    private Collection<Integer> frequencyValues;

    public StatisticaGrafico(TipoGrafico tipoGrafico, LogProperties properties) {
        this.frequencyValues = tipoGrafico.getFrequence(properties);
    }

    @Override
    public int getModa() {
        ConcurrentHashMap<Integer, Integer> valueFrequencies = new ConcurrentHashMap<>();

        frequencyValues.forEach(element -> {
            if (valueFrequencies.containsKey(element))
                valueFrequencies.put(element, valueFrequencies.get(element)+1);
            else
                valueFrequencies.put(element, 0);
        });

        return valueFrequencies.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).
                orElseThrow();
    }

    @Override
    public int getMin() {
        return frequencyValues.stream().min(Integer::compareTo).orElseThrow();
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
        return frequencyValues.stream().mapToInt(Integer::intValue);
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