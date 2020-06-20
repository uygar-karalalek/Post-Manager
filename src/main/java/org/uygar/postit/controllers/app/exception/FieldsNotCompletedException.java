package org.uygar.postit.controllers.app.exception;

import javafx.scene.control.Alert;

public class FieldsNotCompletedException extends Exception{

    public FieldsNotCompletedException(String message, double x, double y) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Attenzione!");
        alert.setHeaderText("Errore rilevato.");
        alert.setContentText(message);
        alert.setX(x);
        alert.setY(y);
        alert.showAndWait();
    }

}