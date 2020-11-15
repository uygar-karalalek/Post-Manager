package org.uygar.postit.controllers.application.app.utils.loader;

import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.uygar.postit.controllers.application.FXLoader;
import org.uygar.postit.controllers.WindowDimensions;
import org.uygar.postit.controllers.application.app.AppController;
import org.uygar.postit.controllers.application.statistica.StatisticaController;
import org.uygar.postit.controllers.utils.ButtonDisableBinding;

public class StatisticaLoader extends WindowLoader {

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
        appController.addStylesheetToPaneWithControllerName("statistica", "main", statisticaController.root);
        showStage(getWindow(statisticaController));
    }

    private void showStage(Stage stage) {
        stage.setOnHiding(statisticaDisableBinding::closedByEventClosed);
        stage.showAndWait();
    }

    @NotNull
    private StatisticaController getStatisticaController() {
        StatisticaController statisticaController = (StatisticaController) FXLoader.getLoadedController("statistica", "app");
        statisticaController.setLogProperties(appController.properties);
        statisticaController.init();
        return statisticaController;
    }

    private Stage getWindow(StatisticaController statisticaController) {
        return appController.initializeWindowAndGet(WindowDimensions.STATISTICA_WINDOW_DIMENSION, Modality.WINDOW_MODAL, statisticaController.root);
    }

    private void initDisableBinding() {
        statisticaDisableBinding = new ButtonDisableBinding(appController.statisticaBtn);
        statisticaDisableBinding.disableOpenWindowButton();
    }

}