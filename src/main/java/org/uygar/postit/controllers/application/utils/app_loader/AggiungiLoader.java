package org.uygar.postit.controllers.application.utils.app_loader;

import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.uygar.postit.controllers.application.AggiungiController;
import org.uygar.postit.controllers.application.FXLoader;
import org.uygar.postit.controllers.WindowDimensions;
import org.uygar.postit.controllers.application.app.AppController;
import org.uygar.postit.controllers.loader.WindowLoader;

public class
AggiungiLoader extends WindowLoader<AppController> {

    public static final int FADE_INIT_TIME_AGGIUNGI = 1;

    public AggiungiLoader(AppController controller) {
        super(controller);
    }

    @Override
    public void load() {
        init();
    }

    private void init() {
        AggiungiController aggiungiController = getPostController();
        addMainStyles(aggiungiController);
        initAndShowStage(aggiungiController);
    }

    private void initAndShowStage(AggiungiController aggiungiController) {
        Stage stage = controller.windowInitializer
                .initializeApplicationWindowAndGet(WindowDimensions.ADD_WINDOW_DIMENSION, Modality.APPLICATION_MODAL, aggiungiController.root);
        controller.setHidingStageEventAndShowAndWait(stage, null);
    }

    @NotNull
    private AggiungiController getPostController() {
        AggiungiController aggiungiController = (AggiungiController) FXLoader.getLoadedController("add", "app");
        aggiungiController.setPostGridViewer(controller.postGrid);
        controller.windowInitializer.fadeWindowEffect(aggiungiController.root, FADE_INIT_TIME_AGGIUNGI);
        return aggiungiController;
    }

    private void addMainStyles(AggiungiController aggiungiController) {
        controller.addStylesheetToPaneWithControllerName("app", "main", aggiungiController.root);
        controller.addStylesheetToPaneWithControllerName("add", "main", aggiungiController.root);
    }

}