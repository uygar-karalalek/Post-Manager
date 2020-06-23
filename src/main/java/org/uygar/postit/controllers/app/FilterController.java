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
    public void onFinito() {
        boolean ignoraMaiusc = this.ignoraMaiusc.isSelected();
        Predicate<Post> inizio = post -> true, tra = post -> true, contiene = post -> true, finisce = post -> true;
    }

    public void setPostGridViewer(PostGridViewer postGridViewer) {
        this.postGridViewer = postGridViewer;
    }

}