package org.uygar.postit.controllers.app.statistica.utility.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import org.uygar.postit.data.properties.LogProperties;

import java.time.Month;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MonthFrequencyModel extends FrequencyModel {

    private SimpleStringProperty mese;

    public MonthFrequencyModel(Map.Entry<String, Integer> entry) {
        super(entry.getValue().toString());
        this.mese = new SimpleStringProperty(entry.getKey());
    }

    public MonthFrequencyModel(String frequenza, String mese) {
        super(frequenza);
        this.mese = new SimpleStringProperty(mese);
    }

    public static TableColumn<MonthFrequencyModel, String> getMonthColumn() {
        return getCol("Mese", "mese", false);
    }

    @Override
    public Map.Entry<String, String> getKeyAndValue() {
        return new AbstractMap.SimpleEntry<>(getMese(), getFrequenza());
    }

    public static class Data {
        public static ObservableList<MonthFrequencyModel> getMonthFrequencyData(LogProperties properties) {
            List<MonthFrequencyModel> frequencyModels = properties.getMonthsAndFrequencies().entrySet()
                    .stream().map(MonthFrequencyModel::new).collect(Collectors.toList());

            frequencyModels.sort(Comparator.comparing(monthFrequency -> Month.valueOf(monthFrequency.getMese())));
            return FXCollections.observableArrayList(frequencyModels);
        }
    }

    public String getMese() {
        return mese.get();
    }

    public SimpleStringProperty meseProperty() {
        return mese;
    }

    public void setMese(String mese) {
        this.mese.set(mese);
    }
}