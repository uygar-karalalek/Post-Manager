package org.uygar.postit.controllers.application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Objects;

public class FXLoader {

    public static Object getLoadedController(String fxmlFileName, String fxmlPackage) {
        try {
            FXMLLoader fxmlLoader = Objects.requireNonNull(getFXMLLoader(fxmlFileName, fxmlPackage));
            fxmlLoader.load();
            return fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static FXMLLoader getFXMLLoader(String fxmlFileName, String fxmlPackage) {
        String initPath = "org/uygar/fxml/";
        String endPath = fxmlPackage + "/" + fxmlFileName + ".fxml";
        return new FXMLLoader(getUrl(initPath, endPath));
    }

    @NotNull
    private static URL getUrl(String initPath, String endPath) {
        System.out.println("Loading from: " + initPath + endPath);
        return Objects.requireNonNull(FXLoader.class.getClassLoader().
                getResource(initPath + endPath));
    }

}