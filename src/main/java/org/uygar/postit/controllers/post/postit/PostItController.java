package org.uygar.postit.controllers.post.postit;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import org.uygar.postit.controllers.BaseController;
import org.uygar.postit.controllers.application.utils.WindowInitializer;
import org.uygar.postit.post.PostIt;

public class PostItController extends BaseController {

    @FXML
    public BorderPane postIt;

    @FXML
    public Rectangle postItRectangle;

    @FXML
    public DatePicker dataField;

    @FXML
    public TextField titoloField, oraField, minutoField, priorityField;

    @FXML
    public TextArea compitoField;

    @FXML
    public Button annullaBtn, salvaBtn, rimuoviBtn;

    public void init(PostIt loadingPostIt) {
        postItRectangle.setFill(loadingPostIt.getColore().postItColor);
        titoloField.setText(loadingPostIt.getTitolo());
        compitoField.setText(loadingPostIt.getTesto());
        dataField.setValue(loadingPostIt.getDataScadenza().toLocalDate());
        oraField.setText(String.valueOf(loadingPostIt.getDataScadenza().getHour()));
        minutoField.setText(String.valueOf(loadingPostIt.getDataScadenza().getMinute()));
        priorityField.setText(String.valueOf(loadingPostIt.getPriority()));
    }

}