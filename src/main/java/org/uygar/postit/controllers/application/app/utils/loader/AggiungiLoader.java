package org.uygar.postit.controllers.application.app.utils.loader;

import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.uygar.postit.controllers.application.AggiungiController;
import org.uygar.postit.controllers.application.FXLoader;
import org.uygar.postit.controllers.application.WindowDimensions;
import org.uygar.postit.controllers.application.app.AppController;
import org.uygar.postit.controllers.utils.ButtonDisableBinding;

public class AggiungiLoader extends WindowLoader {

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
        Stage stage = appController.initializeWindowAndGet(WindowDimensions.ADD_WINDOW_DIMENSION, Modality.APPLICATION_MODAL, aggiungiController.root);
        appController.setHidingStageEventAndShowAndWait(stage, null);
    }

    @NotNull
    private AggiungiController getPostController() {
        AggiungiController aggiungiController = (AggiungiController) FXLoader.getLoadedController("add", "app");
        aggiungiController.setPostGridViewer(appController.postGrid);
        appController.windowInitializer.fadeWindowEffect(aggiungiController.root, FADE_INIT_TIME_AGGIUNGI);
        return aggiungiController;
    }

    private void addMainStyles(AggiungiController aggiungiController) {
        appController.addStylesheetToPaneWithControllerName("app", "main", aggiungiController.root);
        appController.addStylesheetToPaneWithControllerName("add", "main", aggiungiController.root);
    }

}