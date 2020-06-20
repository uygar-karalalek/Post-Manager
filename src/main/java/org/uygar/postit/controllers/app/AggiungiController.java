package org.uygar.postit.controllers.app;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.uygar.postit.controllers.app.exception.FieldsNotCompletedException;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.properties.Sort;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AggiungiController implements Initializable {

    @FXML
    VBox root;

    @FXML
    TextField nomePostField;

    @FXML
    Button btnOk, btnAnnulla;

    @FXML
    SplitMenuButton sortType;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initSortChoice();
    }

    private void initSortChoice() {
        for (Sort element : Sort.values()) {
            MenuItem item = new MenuItem(element.getName());
            sortType.getItems().add(item);
            item.setOnAction(this::onSortDecided);
        }
    }

    public void onSortDecided(ActionEvent event) {
        MenuItem sort = (MenuItem) event.getTarget();
        this.sortType.setText(sort.getText());
    }

    @FXML
    public void onOk() throws FieldsNotCompletedException {
        String name = nomePostField.getText();
        String sortName = this.sortType.getText();
        Window currentWindow = this.root.getScene().getWindow();

        if (this.nomePostField.getText().isEmpty() || sortName.isEmpty())
            throw new FieldsNotCompletedException("Inserisci tutti i campi!", currentWindow.getX(), currentWindow.getY());

        LocalDateTime creationDate = LocalDateTime.now();
        LocalDateTime lastModifiedDate = creationDate;


    }

    @FXML
    public void onAnnulla() {
        root.getScene().getWindow().hide();
    }

}