package org.uygar.postit.controllers.application.app.utils.loader;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.uygar.postit.controllers.application.FXLoader;
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
        Stage stage = getStageModality();
        StatisticaController statisticaController = getStatisticaController();
        appController.addStylesheetToPaneWithControllerName("statistica", "main", statisticaController.root);
        initSceneAndShowStage(stage, statisticaController);
    }

    private void initSceneAndShowStage(Stage stage, StatisticaController statisticaController) {
        Scene scene = new Scene(statisticaController.root);
        stage.setScene(scene);
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

    private Stage getStageModality() {
        return appController.windowInitializer.getStageWithModality(Modality.WINDOW_MODAL, true);
    }

    private void initDisableBinding() {
        statisticaDisableBinding = new ButtonDisableBinding(appController.statisticaBtn);
        statisticaDisableBinding.disableOpenWindowButton();
    }

}