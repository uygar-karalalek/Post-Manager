package org.uygar.postit.controllers.application.exception;

import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;

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
        alert.getDialogPane().getChildren()
                .filtered(node -> node instanceof GridPane)
                .get(0).setId("gridPane");
    }

    public static class WindowCoordinates {

        private Window window;

        public WindowCoordinates(Window window) {
            this.window = window;
        }

        public double getWindowX() {
            if (window == null)
                return 0;
            return window.getX();
        }

        public double getWindowY() {
            if (window == null)
                return 0;
            return window.getX();
        }

        public double getWindowXDividedBy(int divisor) {
            return getWindowX() / divisor;
        }

        public double getWindowYDividedBy(int divisor) {
            return getWindowY() / divisor;
        }

    }

}