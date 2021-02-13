package org.uygar.postit.controllers.post.postit.editor_manager;

import org.uygar.postit.controllers.post.postit.editor_manager.managers.*;

public class EditorInitializer {

    private final InitialValuesManager initialValuesManager;
    private final EditorColorChangeManager colorChangeManager;
    private final EditorDragManager editorDragManager;
    private final DataEditorManager dataManager;
    private final FocusManager focusManager;

    public EditorInitializer(PostItEditorManager manager) {
        this.initialValuesManager = new InitialValuesManager(manager);
        this.colorChangeManager = new EditorColorChangeManager(manager);
        this.editorDragManager = new EditorDragManager(manager);
        this.dataManager = new DataEditorManager(manager);
        this.focusManager = new FocusManager(manager);
    }

    public void initialize() {
        this.initialValuesManager.initialize();
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