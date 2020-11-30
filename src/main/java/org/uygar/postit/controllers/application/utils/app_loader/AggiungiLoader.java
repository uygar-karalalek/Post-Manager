package org.uygar.postit.controllers.application.utils.app_loader;

import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.uygar.postit.controllers.ControllerType;
import org.uygar.postit.controllers.application.add.AggiungiController;
import org.uygar.postit.controllers.application.FXLoader;
import org.uygar.postit.controllers.WindowDimensions;
import org.uygar.postit.controllers.application.app.AppController;
import org.uygar.postit.controllers.loader.WindowLoader;

public class AggiungiLoader extends WindowLoader<AppController, AggiungiController> {

    public static final int FADE_INIT_TIME_AGGIUNGI = 1;

    public AggiungiLoader(AppController controller) {
        super(controller, ControllerType.APPLICATION);
    }

    @Override
    public void load() {
        initAggiungiController();
        addMainStyles();
        initAndShowStage();
    }

    private void initAndShowStage() {
        Stage stage = windowInitializer.initializeApplicationWindowAndGet
                (WindowDimensions.ADD_WINDOW_DIMENSION, Modality.APPLICATION_MODAL, loadingController.add, false);
        controller.setHidingStageEventAndShowAndWait(stage, null);
    }

    @NotNull
    private void initAggiungiController() {
        this.loadingController = (AggiungiController) FXLoader.getLoadedController("add", "app");
        loadingController.setPostGridViewer(controller.postGrid);
        windowInitializer.fadeWindowEffect(loadingController.add, FADE_INIT_TIME_AGGIUNGI);
    }

    private void addMainStyles() {
        controller.bindStyleSheetWithControllerName("app", "main", loadingController.add);
        controller.bindStyleSheetWithControllerName("add", "main", loadingController.add);
    }

}