package org.uygar.postit.controllers.post.postit;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import org.uygar.postit.controllers.BaseController;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.data.database.queries.DML;
import org.uygar.postit.data.database.queries.DMLQueryBuilder;
import org.uygar.postit.data.database.queries.Query;
import org.uygar.postit.post.PostIt;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

    private PostIt loadedPostit;
    // DB Order: id, priority, colore, postid, creationdate, text

    private boolean modifying;

    public void init(PostIt loadingPostIt) {
        modifying = loadingPostIt != null;
        if (modifying) {
            loadedPostit = loadingPostIt;
            postItRectangle.setFill(loadingPostIt.getColore().postItColor);
            titoloField.setText(loadingPostIt.getTitolo());
            compitoField.setText(loadingPostIt.getTesto());
            dataField.setValue(loadingPostIt.getDataScadenza().toLocalDate());
            oraField.setText(String.valueOf(loadingPostIt.getDataScadenza().getHour()));
            minutoField.setText(String.valueOf(loadingPostIt.getDataScadenza().getMinute()));
            priorityField.setText(String.valueOf(loadingPostIt.getPriority()));
        }
    }

    @FXML
    public void onSalva() {
        Query query;
        if (modifying) {
            query = new DMLQueryBuilder().update("postit")
                    .set("priority", priorityField.getText(),
                            // TODO : Campo colore -> "colore", .getText(),
                            "text", compitoField.getText(),
                            "title", titoloField.getText(),
                            "endDate", LocalDateTime.of(dataField.getValue(),
                                    LocalTime.of(Integer.parseInt(oraField.getText()),
                                            Integer.parseInt(minutoField.getText()))).toString());
        } else {
            query = new DMLQueryBuilder().insert().into("postit")
                    .values(loadedPostit.values());
        }

        DataMiner miner = new DataMiner();
        if (miner.tryExecute(query))
            postIt.getScene().getWindow().hide();
    }

    @FXML
    public void onAnnulla() {

    }

    @FXML
    public void onRimuovi() {
    }

}