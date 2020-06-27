package org.uygar.postit.controllers.app;

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

import java.net.URL;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class FilterController implements Initializable {

    @FXML
    GridPane root;

    @FXML
    CheckBox inizio, tra, contiene, finisce, ignoraMaiusc;

    @FXML
    TextField inizioField, contieneField, finisceField;

    @FXML
    DatePicker data1, data2;

    PostGridViewer postGridViewer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
    }

    public void init() {
        bindProperties(inizioField.disableProperty(), inizio.selectedProperty().not());
        bindProperties(data1.disableProperty(), tra.selectedProperty().not());
        bindProperties(data2.disableProperty(), tra.selectedProperty().not());
        bindProperties(contieneField.disableProperty(), contiene.selectedProperty().not());
        bindProperties(finisceField.disableProperty(), finisce.selectedProperty().not());
    }

    public void bindProperties(BooleanProperty property1, BooleanBinding property2) {
        property1.bind(property2);
    }

    @FXML
    public void onFinito() throws WrongFieldsException {
        Window window = root.getScene().getWindow();
        double x = window.getX() + window.getWidth() / 3;
        double y = window.getY() + window.getHeight() / 3;

        if (!areFieldsValid())
            throw new WrongFieldsException("Devi completare tutti i campi!", x, y);

        boolean ignoraMaiusc = this.ignoraMaiusc.isSelected();
        Predicate<Post> inizio = post -> true;
        Predicate<Post> tra = post -> true;
        Predicate<Post> contiene = post -> true;
        Predicate<Post> finisce = post -> true;

        String textInizio = getTextFromField(inizioField, ignoraMaiusc),
                textContiene = getTextFromField(contieneField, ignoraMaiusc),
                textFinisce = getTextFromField(finisceField, ignoraMaiusc);
        LocalDate data1 = this.data1.getValue(), data2 = this.data2.getValue();

        if (this.inizio.isSelected())
            inizio = post -> post.getName().indexOf(textInizio) == 0;

        if (this.tra.isSelected())
            tra = post -> post.getCreationDate().toLocalDate().isAfter(data1) &&
                    post.getCreationDate().toLocalDate().isBefore(data2);

        if (this.contiene.isSelected())
            contiene = post -> post.getName().contains(textContiene);

        if (this.finisce.isSelected())
            finisce = post -> post.getName().endsWith(textFinisce);

        postGridViewer.filter(inizio.and(tra).and(contiene).and(finisce));
    }

    @FXML
    public void onReset() {
        selectedValue(inizio, false);
        selectedValue(tra, false);
        selectedValue(contiene, false);
        selectedValue(finisce, false);
        inizioField.setText("");
        data1.setValue(null);
        data2.setValue(null);
        contieneField.setText("");
        finisceField.setText("");

        postGridViewer.filter("");
    }

    @FXML
    public void onAnnulla() {
        this.root.getScene().getWindow().hide();
    }

    public String getTextFromField(TextField field, boolean lower) {
        String text = field.getText();
        text = lower ? text.toLowerCase() : text;
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
        if (tra.isSelected())
            valid = data1.getValue() != null && data2.getValue() != null;
        return valid;
    }

    public void selectedValue(CheckBox checkBox, boolean value) {
        checkBox.selectedProperty().set(value);
    }

}