package org.uygar.postit.controllers.loader;

import javafx.stage.Stage;
import org.uygar.postit.controllers.ControllerType;
import org.uygar.postit.controllers.application.utils.WindowInitializer;

public abstract class WindowLoader<BASE, LOADING> implements Loader<BASE> {

    protected BASE controller;
    protected LOADING loadingController;
    protected Stage stage;
    protected WindowInitializer windowInitializer;

    public WindowLoader() {
    }

    public WindowLoader(BASE controller, ControllerType parentController) {
        this(parentController);
        this.controller = controller;
    }

    public WindowLoader(ControllerType parentController) {
        this.stage = findStageByControllerType(parentController);
        setWindowInitializerByStage(this.stage);
    }

    protected Stage findStageByControllerType(ControllerType controllerType) {
        return (Stage) Stage.getWindows().stream().filter(window ->
                window.getScene().getRoot().getId().equals(controllerType.controllerName))
                .findFirst().orElseThrow();
    }

    protected void setWindowInitializerByStage(Stage stage) {
        this.windowInitializer = new WindowInitializer(stage);
    }

}