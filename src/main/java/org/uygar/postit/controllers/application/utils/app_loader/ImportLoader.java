package org.uygar.postit.controllers.application.utils.app_loader;

import javafx.stage.Modality;
import javafx.stage.Stage;
import org.uygar.postit.controllers.ControllerType;
import org.uygar.postit.controllers.WindowDimensions;
import org.uygar.postit.controllers.application.FXLoader;
import org.uygar.postit.controllers.application.app_controller.AppController;
import org.uygar.postit.controllers.application.import_controller.ImportController;
import org.uygar.postit.controllers.application.utils.ButtonDisableBinding;
import org.uygar.postit.controllers.loader.WindowLoader;
import org.uygar.postit.post.viewers.post.PostGridViewer;

public class ImportLoader extends WindowLoader<AppController, ImportController> {

    private ButtonDisableBinding importDisableBinding;

    public ImportLoader(AppController controller, PostGridViewer postGridViewer) {
        super(controller, ControllerType.APPLICATION);
    }

    @Override
    public void load() {
        init();
    }

    private void init() {
        initDisableBinding();
        initImportController();
//        parentController.styleManager.bindStyleSheetWithControllerName("import", "main", childController.import_root_pane);
        showStage(getWindow());
    }

    private void showStage(Stage stage) {
        stage.setOnHiding(importDisableBinding::enableButton);
        stage.showAndWait();
    }

    private void initImportController() {
        this.childController = (ImportController) FXLoader.getLoadedController("import", "app");
        this.childController.initialize(parentController.appProperties);
    }

    private Stage getWindow() {
        return windowInitializer.initializeApplicationWindowAndGet
                (WindowDimensions.STATISTICA_WINDOW_DIMENSION, Modality.WINDOW_MODAL, childController.import_root_pane, true);
    }

    private void initDisableBinding() {
        importDisableBinding = new ButtonDisableBinding(parentController.importButton);
        importDisableBinding.disableOpenWindowButton();
    }

}