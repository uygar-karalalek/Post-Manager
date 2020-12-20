package org.uygar.postit.controllers.application.utils.app_loader;

import javafx.stage.Modality;
import javafx.stage.Stage;
import org.uygar.postit.controllers.ControllerType;
import org.uygar.postit.controllers.application.FXLoader;
import org.uygar.postit.controllers.WindowDimensions;
import org.uygar.postit.controllers.application.app.AppController;
import org.uygar.postit.controllers.application.statistica.StatisticaController;
import org.uygar.postit.controllers.application.utils.ButtonDisableBinding;
import org.uygar.postit.controllers.loader.WindowLoader;

public class StatisticaLoader extends WindowLoader<AppController, StatisticaController> {

   private ButtonDisableBinding statisticaDisableBinding;

    public StatisticaLoader(AppController controller) {
        super(controller, ControllerType.APPLICATION);
    }

    public void load() {
        init();
    }

    private void init() {
        initDisableBinding();
        initStatisticaController();
        parentController.bindStyleSheetWithControllerName("statistica", "main", childController.statistica);
        showStage(getWindow());
    }

    private void showStage(Stage stage) {
        stage.setOnHiding(statisticaDisableBinding::enableButton);
        stage.showAndWait();
    }

    private void initStatisticaController() {
        this.childController = (StatisticaController) FXLoader.getLoadedController("statistica", "app");
        this.childController.setLogProperties(parentController.properties);
        this.childController.init();
    }

    private Stage getWindow() {
        return windowInitializer.initializeApplicationWindowAndGet
                (WindowDimensions.STATISTICA_WINDOW_DIMENSION, Modality.WINDOW_MODAL, childController.statistica, true);
    }

    private void initDisableBinding() {
        statisticaDisableBinding = new ButtonDisableBinding(parentController.statisticaBtn);
        statisticaDisableBinding.disableOpenWindowButton();
    }

}