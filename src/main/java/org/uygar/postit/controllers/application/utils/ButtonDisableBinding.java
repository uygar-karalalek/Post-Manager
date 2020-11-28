package org.uygar.postit.controllers.application.utils;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.Event;
import javafx.scene.control.Button;

public class ButtonDisableBinding {

    private final BooleanProperty disablePropertiy = new SimpleBooleanProperty(false);

    public ButtonDisableBinding(Button button) {
        button.disableProperty().bind(disablePropertiy);
    }

    public void disableOpenWindowButton() {
        this.disablePropertiy.set(this.disablePropertiy.not().get());
    }

    public void closedByEventClosed(Event event) {
        this.disablePropertiy.set(false);
    }

}