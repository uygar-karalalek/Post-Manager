package org.uygar.postit.controllers.post.postit.editor_manager;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import org.uygar.postit.controllers.post.postit.editor_manager.managers.*;
import org.uygar.postit.post.PostIt;
import org.uygar.postit.post.properties.Colore;

public class EditorInitializer {

    private final FieldInitialValuesManager fieldInitialValuesManager;
    private final EditorColorChangeManager colorChangeManager;
    private final EditorDragManager editorDragManager;
    private final DataEditorManager dataManager;
    private final FocusManager focusManager;

    public EditorInitializer(PostItEditorManager manager) {
        this.fieldInitialValuesManager = new FieldInitialValuesManager(manager);
        this.colorChangeManager = new EditorColorChangeManager(manager);
        this.editorDragManager = new EditorDragManager(manager);
        this.dataManager = new DataEditorManager(manager);
        this.focusManager = new FocusManager(manager);
    }

    public void initialize() {
        this.fieldInitialValuesManager.initialize();
        this.colorChangeManager.initialize();
        this.editorDragManager.initialize();
        this.dataManager.initialize();
        this.focusManager.initialize();
    }

    public EditorColorChangeManager getColorChangeManager() {
        return colorChangeManager;
    }

    public DataEditorManager getDataManager() {
        return dataManager;
    }

    public FocusManager getFocusManager() {
        return focusManager;
    }

}