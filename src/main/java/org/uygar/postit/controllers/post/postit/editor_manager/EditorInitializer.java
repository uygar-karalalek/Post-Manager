package org.uygar.postit.controllers.post.postit.editor_manager;

import org.uygar.postit.controllers.post.postit.editor_manager.managers.*;

public class EditorInitializer {

    private final InitialValuesManager initialValuesManager;
    private final EditorColorChangeManager colorChangeManager;
    private final EditorDragManager dragManager;
    private final EditorDataManager dataManager;
    private final EditorFocusManager focusManager;

    public EditorInitializer(PostItEditorManager manager) {
        this.initialValuesManager = new InitialValuesManager(manager);
        this.colorChangeManager = new EditorColorChangeManager(manager);
        this.dragManager = new EditorDragManager(manager);
        this.dataManager = new EditorDataManager(manager);
        this.focusManager = new EditorFocusManager(manager);
    }

    public void initialize() {
        this.initialValuesManager.initialize();
        this.colorChangeManager.initialize();
        this.dragManager.initialize();
        this.dataManager.initialize();
        this.focusManager.initialize();
    }

    public EditorDataManager getDataManager() {
        return dataManager;
    }

}