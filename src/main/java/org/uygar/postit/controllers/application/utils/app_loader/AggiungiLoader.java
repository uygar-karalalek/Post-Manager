package org.uygar.postit.controllers.application.utils.app_loader;

import javafx.stage.Modality;
import javafx.stage.Stage;
import org.uygar.postit.controllers.ControllerType;
import org.uygar.postit.controllers.application.add_controller.AggiungiController;
import org.uygar.postit.controllers.application.FXLoader;
import org.uygar.postit.controllers.WindowDimensions;
import org.uygar.postit.controllers.application.app_controller.AppController;
import org.uygar.postit.controllers.application.utils.WindowInitializer;
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
                (WindowDimensions.ADD_WINDOW_DIMENSION, Modality.APPLICATION_MODAL, childController.add, false);
        parentController.setHidingStageEventAndShowAndWait(stage, null);
    }

    private void initAggiungiController() {
        super.childController = (AggiungiController) FXLoader.getLoadedController("add", "app");
        childController.setPostGridViewer(parentController.postGrid);
        WindowInitializer.fadeWindowEffect(childController.add, FADE_INIT_TIME_AGGIUNGI);
    }

    private void addMainStyles() {
        parentController.styleManager.bindStyleSheetWithControllerName("app", "main", childController.add);
        parentController.styleManager.bindStyleSheetWithControllerName("add", "main", childController.add);
    }

}