package org.uygar.postit.controllers.utils;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.Event;
import javafx.scene.control.Button;

public class ButtonDisableBinding {

    private Button button;
    private BooleanProperty disablePropertiy = new SimpleBooleanProperty(false);

    public ButtonDisableBinding(Button button) {
        this.button = button;
        this.button.disableProperty().bind(disablePropertiy);
    }

    public void disableOpenWindowButton() {
        this.disablePropertiy.set(this.disablePropertiy.not().get());
    }

    public void closedByEventClosed(Event event) {
        this.disablePropertiy.set(false);
    }

}