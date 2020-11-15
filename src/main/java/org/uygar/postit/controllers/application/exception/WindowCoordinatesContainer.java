package org.uygar.postit.controllers.application.exception;

import javafx.stage.Window;
import org.uygar.postit.controllers.WindowDimensions;

public class WindowCoordinatesContainer {

    private double x, y;
    private final double windowWidth, windowHeight;

    public WindowCoordinatesContainer(Window window) {
        this.x = window.getX();
        this.y = window.getY();
        this.windowWidth = window.getWidth();
        this.windowHeight = window.getHeight();
    }

    public void centerBasedOnAlertAndFatherWindowDimensions() {
        this.x += (this.windowWidth / 2) - (WindowDimensions.ALERT_WINDOW_DIMENSION.getWidth() / 2);
        this.y += (this.windowHeight / 2) - (WindowDimensions.ALERT_WINDOW_DIMENSION.getHeight() / 2);
    }

    public double getWindowX() {
        return x;
    }

    public double getWindowY() {
        return y;
    }

}