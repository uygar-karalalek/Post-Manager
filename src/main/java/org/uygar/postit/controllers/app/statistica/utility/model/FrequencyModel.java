package org.uygar.postit.controllers.app.statistica.utility.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.util.Map;

public abstract class FrequencyModel {

    protected SimpleStringProperty frequenza;

    public FrequencyModel(String frequenza) {
        this.frequenza = new SimpleStringProperty(frequenza);
    }

    protected static <T> TableColumn<T, String> getCol(String columnAlias, String propName, boolean editable) {
        TableColumn<T, String> column = new TableColumn<>(columnAlias);
        column.setCellValueFactory(new PropertyValueFactory<>(propName));
        column.setSortable(false);
        if (editable)
            column.setCellFactory(TextFieldTableCell.forTableColumn());
        return column;
    }

    public static <T> TableColumn<T, String> getFrequencyColumn() {
        return getCol("Frequenza", "frequenza", true);
    }

    public String getFrequenza() {
        return frequenza.get();
    }

    public SimpleStringProperty frequenzaProperty() {
        return frequenza;
    }

    public void setFrequenza(String frequenza) {
        this.frequenza.set(frequenza);
    }

    public abstract Map.Entry<String, String> getKeyAndValue();

}