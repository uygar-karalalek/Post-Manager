package org.uygar.postit.controllers.application.filter_controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;
import org.uygar.postit.controllers.BaseController;
import org.uygar.postit.controllers.exception.WindowCoordinatesContainer;
import org.uygar.postit.controllers.exception.WrongFieldsException;
import org.uygar.postit.controllers.filter.FilterUnitContainer;
import org.uygar.postit.controllers.filter.post.PostFilter;
import org.uygar.postit.post.viewers.post.PostGridViewer;

import java.io.File;

public class FilterController extends BaseController {

    @FXML
    public GridPane filter;

    @FXML
    public CheckBox inizio, tra, contiene, finisce, ignoraMaiusc;

    @FXML
    public TextField inizioField, contieneField, finisceField;

    @FXML
    public DatePicker data1, data2;

    PostFilter postFilter;
    PostGridViewer postGridViewer;

    public void init(PostGridViewer postGridViewer) {
        this.postFilter = new PostFilter(this, new FilterUnitContainer<>());
        this.postGridViewer = postGridViewer;
        resetFields();
        bindAndInitTextFieldWithCheckBox();
        deserializeFilter();
    }

    private void bindAndInitTextFieldWithCheckBox() {
        inizioField.setDisable(!inizio.isSelected());
        data1.setDisable(!tra.isSelected());
        data2.setDisable(!tra.isSelected());
        contieneField.setDisable(!contiene.isSelected());
        finisceField.setDisable(!finisce.isSelected());

        bindTextFieldWithCheckBox(inizioField, inizio);
        bindDatePickerWithCheckBox(data1, tra);
        bindDatePickerWithCheckBox(data2, tra);
        bindTextFieldWithCheckBox(contieneField, contiene);
        bindTextFieldWithCheckBox(finisceField, finisce);
    }

    public void bindTextFieldWithCheckBox(TextField field, CheckBox box) {
        box.selectedProperty().addListener((observableValue, oldVal, newVal) -> field.setDisable(oldVal));
    }

    public void bindDatePickerWithCheckBox(DatePicker datePicker, CheckBox box) {
        box.selectedProperty().addListener((observableValue, oldVal, newVal) -> datePicker.setDisable(oldVal));
    }

    private void deserializeFilter() {
        PostFilter deserialized = (PostFilter) postFilter.deserialize();
        if (deserialized != null) {
            deserialized.setFilterController(this);
            deserialized.applyFilterToController();
        }
        postFilter = deserialized == null ? postFilter : deserialized;
    }

    @FXML
    public void onFinito() throws WrongFieldsException {
        if (fieldsNotValid())
            throwNotValidException();
        filterPostsInPostGridViewer();
        postFilter.resetUnitContainer();
        postFilter.serialize();
    }

    private void filterPostsInPostGridViewer() {
        postFilter.buildFilterSettingUnits();
        postGridViewer.filterPostsByUnionPredicates(postFilter.getResult());
    }

    private void throwNotValidException() throws WrongFieldsException {
        Window window = filter.getScene().getWindow();
        WindowCoordinatesContainer coordinates = new WindowCoordinatesContainer(window);

        throw new WrongFieldsException("Hai sbagliato a compilare i campi!", coordinates);
    }

    @FXML
    public void onReset() {
        resetFields();
        postGridViewer.filterPostsNameContaining("");
        serializedFilterFileDeleteIfExists();
    }

    public void resetFields() {
        inizio.setSelected(false);
        tra.setSelected(false);
        contiene.setSelected(false);
        finisce.setSelected(false);
        ignoraMaiusc.setSelected(true);

        inizioField.setText(null);
        data1.setValue(null);
        data2.setValue(null);
        contieneField.setText(null);
        finisceField.setText(null);
    }

    private void serializedFilterFileDeleteIfExists() {
        File file = new File("filter.ser");
        file.delete();
    }


    @FXML
    public void onAnnulla() {
        this.filter.getScene().getWindow().hide();
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

}