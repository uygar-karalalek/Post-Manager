package org.uygar.postit.controllers.application.utils.app_loader;

import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.uygar.postit.controllers.application.FXLoader;
import org.uygar.postit.controllers.WindowDimensions;
import org.uygar.postit.controllers.application.app.AppController;
import org.uygar.postit.controllers.application.filter.FilterController;
import org.uygar.postit.controllers.application.utils.ButtonDisableBinding;
import org.uygar.postit.controllers.loader.WindowLoader;

public class FilterLoader extends WindowLoader<AppController> {

    public static final double FADE_INIT_TIME_FILTER = 0.4;

    public FilterLoader(AppController controller) {
        super(controller);
    }

    @Override
    public void load() {
        init();
    }

    private void init() {
        ButtonDisableBinding filterDisableBinding =
                new ButtonDisableBinding(controller.filterButton);
        filterDisableBinding.disableOpenWindowButton();
        FilterController filterController = getFilterController();
        Stage stage = getWindow(filterController);
        controller.setHidingStageEventAndShowAndWait(stage, filterDisableBinding);
    }

    @NotNull
    private FilterController getFilterController() {
        FilterController filterController = (FilterController) FXLoader.getLoadedController("filter", "app");
        filterController.init(controller.postGrid);
        controller.addStylesheetToPaneWithControllerName("filter", "main", filterController.root);
        initFilterFade(filterController);
        return filterController;
    }

    private Stage getWindow(FilterController filterController) {
        return controller.windowInitializer.
                initializeApplicationWindowAndGet(WindowDimensions.FILTER_WINDOW_DIMENSION, Modality.WINDOW_MODAL, filterController.root);
    }

    private void initFilterFade(FilterController filterController) {
        controller.windowInitializer.fadeWindowEffect(filterController.root, FADE_INIT_TIME_FILTER);
    }

}