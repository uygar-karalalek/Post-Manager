package org.uygar.postit.controllers.application.exception;

import javafx.stage.Window;

public class WindowCoordinates {

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