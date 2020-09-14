package org.uygar.postit.controllers.utils;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class WindowAccessManager {

    /*
     This class is created for managing
     accesses in a given window
    */

    private BooleanProperty accessorDisabled = new SimpleBooleanProperty(false);

    public WindowAccessManager(Node accessor) {
        accessor.setOnMouseClicked(this::onAccessorClicked);
        accessor.disableProperty().bind(accessorDisabled);
    }

    private void onAccessorClicked(MouseEvent event) {
        accessorDisabled.set(accessorDisabled.not().get());
    }

}