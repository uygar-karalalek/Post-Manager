package org.uygar.postit.controllers.application.filter;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;
import org.uygar.postit.controllers.application.app.AppController;
import org.uygar.postit.controllers.application.exception.WrongFieldsException;
import org.uygar.postit.controllers.application.filter.util.FilterBuilder;
import org.uygar.postit.controllers.application.filter.util.FilterSerializer;
import org.uygar.postit.viewers.PostGridViewer;

import java.io.File;

import static org.uygar.postit.controllers.application.exception.WrongFieldsException.*;

public class FilterController {

    @FXML
    public GridPane root;

    @FXML
    public CheckBox inizio, tra, contiene, finisce, ignoraMaiusc;

    @FXML
    public TextField inizioField, contieneField, finisceField;

    @FXML
    public DatePicker data1, data2;

    PostGridViewer postGridViewer;

    public void init(PostGridViewer postGridViewer) {
        this.postGridViewer = postGridViewer;
        resetFields();
        bindProperties();
        addCheckListeners();
        deserializeFilter();
    }

    private void bindProperties() {
        bindProperties(inizioField.disableProperty(), inizio.selectedProperty().not());
        bindProperties(data1.disableProperty(), tra.selectedProperty().not());
        bindProperties(data2.disableProperty(), tra.selectedProperty().not());
        bindProperties(contieneField.disableProperty(), contiene.selectedProperty().not());
        bindProperties(finisceField.disableProperty(), finisce.selectedProperty().not());
    }

    private void addCheckListeners() {
        addCheckChangeListenerToCheckBox(inizio, inizioField);
        addCheckChangeListenerToCheckBox(contiene, contieneField);
        addCheckChangeListenerToCheckBox(finisce, finisceField);
    }

    private void deserializeFilter() {
        FilterSerializer filter = FilterSerializer.deserialize();
        if (filter != null)
            filter.applyFilter(this);
    }

    public void bindProperties(BooleanProperty property1, BooleanBinding property2) {
        property1.bind(property2);
    }

    public void addCheckChangeListenerToCheckBox(CheckBox box, TextField field) {
        box.selectedProperty().addListener((observableValue, oldVal, newVal) -> {
            if (newVal) field.setText("");
            else field.setText(null);
        });
    }

    @FXML
    public void onFinito() throws WrongFieldsException {
        if (fieldsNotValid())
            throwNotValidException();

        filterPostsInPostGridViewer();

        FilterSerializer.serialize(getFilterSerializer());
    }

    private void filterPostsInPostGridViewer() {
        FilterBuilder builder = getFilterBuilder();
        postGridViewer.filterPostsByUnionPredicates(builder.unifiedPredicates());
    }

    private void throwNotValidException() throws WrongFieldsException {
        Window window = root.getScene().getWindow();
        WindowCoordinates coordinates = new WindowCoordinates(window);

        double windowXDividedBy = coordinates.getWindowXDividedBy(10);
        double windowYDividedBy = coordinates.getWindowYDividedBy(4);

        throw new WrongFieldsException("Hai sbagliato a compilare i campi!", windowXDividedBy, windowYDividedBy);
    }

    @FXML
    public void onReset() {
        resetFields();
        postGridViewer.filterPostsNameContaining("");
        fileReset();
    }

    public void resetFields() {
        inizio.setSelected(false);
        tra.setSelected(false);
        contiene.setSelected(false);
        finisce.setSelected(false);

        inizioField.setText(null);
        data1.setValue(null);
        data2.setValue(null);
        contieneField.setText(null);
        finisceField.setText(null);
    }

    private void fileReset() {
        File file = new File("filter.ser");
        if (file.exists())
            file.delete();
    }

    @FXML
    public void onAnnulla() {
        this.root.getScene().getWindow().hide();
    }

    public boolean fieldsNotValid() {
        return !isTraValid();
    }

    public boolean isTraValid() {
        boolean valid = true;
        if (tra.isSelected())
            valid = data1.getValue() != null && data2.getValue() != null;
        return valid;
    }

    public FilterBuilder getFilterBuilder() {
        return new FilterBuilder(
                inizioField.getText(),
                contieneField.getText(),
                finisceField.getText(),
                data1.getValue(),
                data2.getValue(),
                ignoraMaiusc.isSelected(),
                inizio.isSelected(),
                tra.isSelected(),
                contiene.isSelected(),
                finisce.isSelected());
    }

    public FilterSerializer getFilterSerializer() {
        return new FilterSerializer(inizioField.getText(), contieneField.getText(),
                finisceField.getText(), ignoraMaiusc.isSelected(),
                data1.getValue(), data2.getValue());
    }

}