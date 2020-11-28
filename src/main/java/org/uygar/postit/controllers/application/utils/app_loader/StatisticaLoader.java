package org.uygar.postit.controllers.application.utils.app_loader;

import javafx.stage.Modality;
import javafx.stage.Stage;
import org.uygar.postit.controllers.application.FXLoader;
import org.uygar.postit.controllers.WindowDimensions;
import org.uygar.postit.controllers.application.app.AppController;
import org.uygar.postit.controllers.application.statistica.StatisticaController;
import org.uygar.postit.controllers.application.utils.ButtonDisableBinding;
import org.uygar.postit.controllers.loader.WindowLoader;

public class StatisticaLoader extends WindowLoader<AppController> {

   private ButtonDisableBinding statisticaDisableBinding;

    public StatisticaLoader(AppController controller) {
        super(controller);
    }

    public void load() {
        init();
    }

    private void init() {
        initDisableBinding();
        StatisticaController statisticaController = getStatisticaController();
        controller.addStylesheetToPaneWithControllerName("statistica", "main", statisticaController.root);
        showStage(getWindow(statisticaController));
    }

    private void showStage(Stage stage) {
        stage.setOnHiding(statisticaDisableBinding::closedByEventClosed);
        stage.showAndWait();
    }

    private StatisticaController getStatisticaController() {
        StatisticaController statisticaController = (StatisticaController) FXLoader.getLoadedController("statistica", "app");
        statisticaController.setLogProperties(controller.properties);
        statisticaController.init();
        return statisticaController;
    }

    private Stage getWindow(StatisticaController statisticaController) {
        return controller.windowInitializer
                .initializeApplicationWindowAndGet(WindowDimensions.STATISTICA_WINDOW_DIMENSION, Modality.WINDOW_MODAL, statisticaController.root);
    }

    private void initDisableBinding() {
        statisticaDisableBinding = new ButtonDisableBinding(controller.statisticaBtn);
        statisticaDisableBinding.disableOpenWindowButton();
    }

}