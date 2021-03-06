package org.uygar.postit.controllers.application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Objects;

public class FXLoader {

    public static Object getLoadedController(String fxml, String pkg) {
        try {
            String initPath = "org/uygar/fxml/";
            String endPath = pkg + "/" + fxml + ".fxml";
            FXMLLoader loader = new FXMLLoader(getUrl(initPath, endPath));
            System.out.println(getUrl(initPath, endPath).toString());
            loader.load();
            return loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @NotNull
    private static URL getUrl(String initPath, String endPath) {
        System.out.println("Loading from: " + initPath + endPath);
        return Objects.requireNonNull(FXLoader.class.getClassLoader().
                getResource(initPath + endPath));
    }

}