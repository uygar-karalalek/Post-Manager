package org.uygar.postit.controllers.application.app;

import javafx.beans.InvalidationListener;
import javafx.scene.Parent;
import org.uygar.postit.data.properties.LogProperties;

public class AppStyleManager extends AppManager {

    public AppStyleManager(AppController controller) {
        super(controller);
    }

    public void bindStyleSheetWithControllerName(String controllerName, String pkgName, Parent pane) {
        String stdPath = "org/uygar/stylesheets/" + pkgName + "/";
        String endPath = controllerName + "_" + getCurrentStyleColorFileName();
        pane.getStylesheets().setAll(stdPath + endPath);
        appController.application.getProperties().addListener((InvalidationListener) change -> {
            String updatedPath = controllerName + "_" + getCurrentStyleColorFileName();
            pane.getStylesheets().setAll(stdPath + updatedPath);
        });
    }

    private String getCurrentStyleColorFileName() {
        String current = getCurrentStyleCssFilePath();
        return current.substring(current.lastIndexOf('_') + 1);
    }

    public void setTheme(String cssFilePath) {
        appController.application.getStylesheets().setAll(cssFilePath);
        appController.application.getProperties().put("style", cssFilePath);
    }

    public String getCurrentStyleCssFilePath() {
        return appController.application.getStylesheets().get(0);
    }

}