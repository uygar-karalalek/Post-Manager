package org.uygar.postit.controllers.application.utils.app_loader;

import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.uygar.postit.controllers.ControllerType;
import org.uygar.postit.controllers.application.FXLoader;
import org.uygar.postit.controllers.WindowDimensions;
import org.uygar.postit.controllers.application.app.AppController;
import org.uygar.postit.controllers.application.filter.FilterController;
import org.uygar.postit.controllers.application.utils.ButtonDisableBinding;
import org.uygar.postit.controllers.application.utils.WindowInitializer;
import org.uygar.postit.controllers.loader.WindowLoader;

public class FilterLoader extends WindowLoader<AppController, FilterController> {

    public static final double FADE_INIT_TIME_FILTER = 0.4;

    public FilterLoader(AppController controller) {
        super(controller, ControllerType.APPLICATION);
    }

    @Override
    public void load() {
        ButtonDisableBinding filterDisableBinding =
                new ButtonDisableBinding(baseController.filterButton);
        filterDisableBinding.disableOpenWindowButton();
        initFilterController();
        Stage stage = getWindow();
        baseController.setHidingStageEventAndShowAndWait(stage, filterDisableBinding);
    }

    private void initFilterController() {
        this.loadingController = (FilterController) FXLoader.getLoadedController("filter", "app");
        this.loadingController.init(baseController.postGrid);
        baseController.bindStyleSheetWithControllerName("filter", "main", loadingController.filter);
        initFilterFade();
    }

    private Stage getWindow() {
        return windowInitializer.
                initializeApplicationWindowAndGet(WindowDimensions.FILTER_WINDOW_DIMENSION, Modality.WINDOW_MODAL, loadingController.filter, false);
    }

    private void initFilterFade() {
        WindowInitializer.fadeWindowEffect(loadingController.filter, FADE_INIT_TIME_FILTER);
    }

}