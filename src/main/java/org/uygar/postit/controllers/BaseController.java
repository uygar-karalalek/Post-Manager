package org.uygar.postit.controllers;

import javafx.stage.Stage;

public abstract class BaseController {

    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}