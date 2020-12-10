package org.uygar.postit.controllers.exception;

import javafx.scene.control.Alert;
import org.uygar.postit.controllers.WindowDimensions;

public class WrongFieldsException extends Exception {

    private final WindowCoordinatesContainer coordinatesManager;

    public WrongFieldsException(String message, WindowCoordinatesContainer coordinatesManager) {
        super(message);
        this.coordinatesManager = coordinatesManager;
        coordinatesManager.centerBasedOnAlertAndFatherWindowDimensions();
        showExceptionMessage();
    }

    private void showExceptionMessage() {
        showExceptionMessage(coordinatesManager.getWindowX(), coordinatesManager.getWindowY());
    }

    private void showExceptionMessage(double x, double y) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.getDialogPane().setPrefSize(WindowDimensions.ALERT_WINDOW_DIMENSION.getWidth(),
                WindowDimensions.ALERT_WINDOW_DIMENSION.getHeight());
        alert.setHeight(WindowDimensions.ALERT_WINDOW_DIMENSION.getHeight());
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
    }

    public static void throwWrongFieldExceptionIf(boolean condition, String message,
                                           WindowCoordinatesContainer errorViewCordinates) throws WrongFieldsException {
        if (condition)
            throw new WrongFieldsException(message, errorViewCordinates);
    }

}