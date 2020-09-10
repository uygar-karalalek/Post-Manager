package org.uygar.postit.controllers.app.statistica.utility.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import org.uygar.postit.data.properties.LogProperties;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HourFrequencyModel extends FrequencyModel {

    private SimpleStringProperty ora;

    private HourFrequencyModel(Map.Entry<Integer, Integer> entry) {
        super(entry.getValue().toString());
        this.ora = new SimpleStringProperty(entry.getKey().toString()+":00");
    }

    public HourFrequencyModel(String ora, String frequenza) {
        super(frequenza);
        this.ora = new SimpleStringProperty(ora);
    }

    public static TableColumn<HourFrequencyModel, String> getHourColumn() {
        return getCol("Ora", "ora", false);
    }

    @Override
    public Map.Entry<String, String> getKeyAndValue() {
        return new AbstractMap.SimpleEntry<>(getOra(), getFrequenza());
    }

    public static class Data {
        public static ObservableList<HourFrequencyModel> getHourFrequencyData(LogProperties properties) {
            List<HourFrequencyModel> frequencyModels = properties.getHoursAndFrequencies().entrySet()
                    .stream().map(HourFrequencyModel::new).collect(Collectors.toList());

            return FXCollections.observableArrayList(frequencyModels);
        }
    }

    public String getOra() {
        return ora.get().substring(0, ora.get().indexOf(':'));
    }

    public SimpleStringProperty oraProperty() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora.set(ora);
    }

}