package org.uygar.postit.controllers.post.postit.editor_manager;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import org.uygar.postit.controllers.post.postit.PostItController;
import org.uygar.postit.controllers.post.postit.PostItUtils;
import org.uygar.postit.post.PostIt;
import org.uygar.postit.post.properties.Colore;

import static org.uygar.postit.data.query_utils.QueryUtils.*;

public class PostItEditorManager {

    private final PostItController postItController;
    public SimpleObjectProperty<Colore> rectangleColor = new SimpleObjectProperty<>();

    private final EditorInitializer editorInitializer;

    public PostItEditorManager(PostItController postItController) {
        this.postItController = postItController;
        this.editorInitializer = new EditorInitializer(this);
    }

    public void initialize() {
        this.editorInitializer.initialize();
    }

    public boolean trySavePostIt(PostIt postIt) {
        return this.editorInitializer.getDataManager().trySavePostIt(postIt);
    }

    public PostItController getPostItController() {
        return postItController;
    }

    public void closePostItEditor() {
        postItController.postIt.getScene().getWindow().hide();
    }

}