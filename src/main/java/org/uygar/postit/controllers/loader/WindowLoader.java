package org.uygar.postit.controllers.loader;

import javafx.stage.Stage;
import javafx.stage.Window;
import org.uygar.postit.controllers.BaseController;
import org.uygar.postit.controllers.ControllerType;
import org.uygar.postit.controllers.application.utils.WindowInitializer;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class WindowLoader<P extends BaseController, C extends BaseController> implements Loader {

    protected P parentController;
    protected C childController;

    protected Stage parentStage;
    protected WindowInitializer windowInitializer;

    public WindowLoader() {
    }

    public WindowLoader(P controller, ControllerType parentController) {
        this(parentController);
        this.parentController = controller;
    }

    public WindowLoader(ControllerType parentController) {
        this.parentStage = findStageByControllerType(parentController);
        setWindowInitializerByStage(this.parentStage);
    }

    protected Stage findStageByControllerType(ControllerType controllerType) {
        return (Stage) Stage.getWindows().stream().filter(window ->
                window.getScene().getRoot().getId().equals(controllerType.controllerName))
                .findFirst().orElseThrow();
    }

    protected void setWindowInitializerByStage(Stage stage) {
        this.windowInitializer = new WindowInitializer(stage);
    }

    public static void hideAllControllersWindowsOfType(ControllerType controllerType) {
        Set<Window> collect = Stage.getWindows().stream().filter(window -> window.getScene()
                .getRoot().getId().equals(controllerType.controllerName)).collect(Collectors.toSet());
        collect.forEach(Window::hide);
    }

}