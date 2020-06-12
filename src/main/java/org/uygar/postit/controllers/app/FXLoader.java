package org.uygar.postit.controllers.app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Objects;

public class FXLoader {

    public static Parent getLoadedParent(String fxml, String pkg) {
        try {
            String initPath = "org/uygar/fxml/";
            String endPath = pkg + "/" + fxml + ".fxml";
            Parent root = FXMLLoader.load(Objects.requireNonNull(FXLoader.class.getClassLoader().
                    getResource(initPath + endPath)));
            return root;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}