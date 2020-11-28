package org.uygar.postit.controllers.post.postit;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import org.uygar.postit.post.PostIt;

public class PostItController {

    @FXML
    public BorderPane root;

    @FXML
    public DatePicker dataField;

    @FXML
    public TextField oraField, minutoField, priorityField;

    @FXML
    public Button annullaBtn, salvaBtn, rimuoviBtn;

    public void init(PostIt postIt) {
    }

}