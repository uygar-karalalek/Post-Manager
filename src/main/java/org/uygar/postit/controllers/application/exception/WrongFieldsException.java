package org.uygar.postit.controllers.application.exception;

import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;

public class WrongFieldsException extends Exception {

    private WindowCoordinates coordinatesManager;

    public WrongFieldsException(String message, WindowCoordinates coordinatesManager) {
        super(message);
        this.coordinatesManager = coordinatesManager;
        showExceptionMessage();
    }

    public WrongFieldsException(String message, double x, double y) {
        super(message);
        showExceptionMessage(x, y);
    }

    private void showExceptionMessage() {
        showExceptionMessage(coordinatesManager.getWindowX(), coordinatesManager.getWindowY());
    }

    private void showExceptionMessage(double x, double y) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        initAlert(this.getMessage(), x, y, alert);
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
        alert.getDialogPane().getChildren().stream().filter(node -> node instanceof GridPane)
                .findFirst().ifPresent(node -> node.setId("gridPane"));;
    }

}