package org.uygar.postit.controllers.app;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
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
        for (Sort element : Sort.values())
            sortType.getItems().addAll(new MenuItem(element.toString()));
    }

    public void onOk() {
        String name = nomePostField.getText();
        LocalDateTime creationDate = LocalDateTime.now();

    }

    public void onAnnulla() {
        root.getScene().getWindow().hide();
    }
}