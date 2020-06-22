package org.uygar.postit.controllers.app.exception;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;

public class WrongFieldsException extends Exception {

    public WrongFieldsException(String message, double x, double y) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        initAlert(message, x, y, alert);
        alert.showAndWait();
    }

    private void initAlert(String message, double x, double y, Alert alert) {
        String stylePath = "org/uygar/stylesheets/alert/alert.css";
        alert.getDialogPane().getStylesheets().add(stylePath);
        alert.setTitle("Attenzione!");
        alert.setHeaderText("Errore rilevato.");
        alert.setContentText(message);
        alert.setX(x);
        alert.setY(y);
        alert.getDialogPane().getChildren()
                .filtered(node -> node instanceof GridPane)
                .get(0).setId("gridPane");
    }

}