package org.uygar.postit.controllers.app.filter;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;
import org.uygar.postit.controllers.app.exception.WrongFieldsException;
import org.uygar.postit.post.Post;
import org.uygar.postit.viewers.PostGridViewer;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class FilterController implements Initializable {

    @FXML
    public GridPane root;

    @FXML
    public CheckBox inizio, tra, contiene, finisce, ignoraMaiusc;

    @FXML
    public TextField inizioField, contieneField, finisceField;

    @FXML
    public DatePicker data1, data2;

    PostGridViewer postGridViewer;

    Filter filter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
    }

    public void init() {
        reset();
        bindProperties();
        addCheckListeners();
        deserializeFilter();
    }

    private void deserializeFilter() {
        Filter filter = Filter.deserialize();
        if (filter != null)
            filter.applyFilter(this);
    }

    private void bindProperties() {
        bindProperties(inizioField.disableProperty(), inizio.selectedProperty().not());
        bindProperties(data1.disableProperty(), tra.selectedProperty().not());
        bindProperties(data2.disableProperty(), tra.selectedProperty().not());
        bindProperties(contieneField.disableProperty(), contiene.selectedProperty().not());
        bindProperties(finisceField.disableProperty(), finisce.selectedProperty().not());
    }

    private void addCheckListeners() {
        checkListener(inizio, inizioField);
        checkListener(contiene, contieneField);
        checkListener(finisce, finisceField);
    }

    public void bindProperties(BooleanProperty property1, BooleanBinding property2) {
        property1.bind(property2);
    }

    public void checkListener(CheckBox box, TextField field) {
        box.selectedProperty().addListener((observableValue, oldVal, newVal) -> {
            if (newVal) field.setText("");
            else field.setText(null);
        });
    }

    @FXML
    public void onFinito() throws WrongFieldsException {
        Window window = root.getScene().getWindow();
        double x = window.getX() + window.getWidth() / 10;
        double y = window.getY() + window.getHeight() / 4;

        if (!areFieldsValid())
            throw new WrongFieldsException("Hai sbagliato a compilare i campi!", x, y);

        boolean ignoraMaiusc = this.ignoraMaiusc.isSelected();
        Predicate<Post> inizio, tra, contiene, finisce;
        inizio = tra = contiene = finisce = post -> true;

        String textInizio = getTextFromField(inizioField, ignoraMaiusc),
                textContiene = getTextFromField(contieneField, ignoraMaiusc),
                textFinisce = getTextFromField(finisceField, ignoraMaiusc);
        LocalDate data1 = this.data1.getValue(), data2 = this.data2.getValue();

        // Making filter controls
        if (this.inizio.isSelected() && ignoraMaiusc)
            inizio = post -> post.getName().toLowerCase().indexOf(textInizio) == 0;
        else if (this.inizio.isSelected()) inizio = post -> post.getName().indexOf(textInizio) == 0;

        if (this.tra.isSelected())
            tra = post -> (post.getCreationDate().toLocalDate().isAfter(data1) || post.getCreationDate().toLocalDate().isEqual(data1)) &&
                    (post.getCreationDate().toLocalDate().isBefore(data2) || post.getCreationDate().toLocalDate().isAfter(data1) &&
                            post.getCreationDate().toLocalDate().isEqual(data2));

        if (this.contiene.isSelected() && ignoraMaiusc)
            contiene = post -> post.getName().toLowerCase().contains(textContiene);
        else if (this.contiene.isSelected()) contiene = post -> post.getName().contains(textContiene);

        if (this.finisce.isSelected() && ignoraMaiusc)
            finisce = post -> post.getName().toLowerCase().endsWith(textFinisce);
        else if (this.finisce.isSelected()) finisce = post -> post.getName().endsWith(textFinisce);

        // Filter contents
        postGridViewer.filter(inizio.and(tra).and(contiene).and(finisce));

        initFilter();
        Filter.serialize(filter);
    }

    @FXML
    public void onReset() {
        reset();
        postGridViewer.filter("");
        fileReset();
    }

    public void reset() {
        selectedValue(inizio, false);
        selectedValue(tra, false);
        selectedValue(contiene, false);
        selectedValue(finisce, false);
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

    public String getTextFromField(TextField field, boolean ignore) {
        String text = field.getText();
        if (text != null)
            text = ignore ? text.toLowerCase() : text;
        return text;
    }

    public void setPostGridViewer(PostGridViewer postGridViewer) {
        this.postGridViewer = postGridViewer;
    }

    public boolean areFieldsValid() {
        return isTraValid();
    }

    public boolean isTraValid() {
        boolean valid = true;
        if (tra.isSelected()) {
            valid = data1.getValue() != null && data2.getValue() != null
            && (data1.getValue().isBefore(data2.getValue()) || data1.getValue().isEqual(data2.getValue()));
        }
        return valid;
    }

    public void selectedValue(CheckBox checkBox, boolean value) {
        checkBox.selectedProperty().set(value);
    }

    public void initFilter() {
        filter = new Filter(inizioField.getText(), contieneField.getText(),
                finisceField.getText(), ignoraMaiusc.isSelected(),
                data1.getValue(), data2.getValue());
    }

}