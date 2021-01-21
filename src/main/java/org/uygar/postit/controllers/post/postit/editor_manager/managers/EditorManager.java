package org.uygar.postit.controllers.post.postit.editor_manager.managers;

import org.uygar.postit.controllers.post.postit.editor_manager.PostItEditorManager;

abstract class EditorManager {

    private final PostItEditorManager manager;

    public EditorManager(PostItEditorManager manager) {
        this.manager = manager;
    }

    public PostItEditorManager getManager() {
        return manager;
    }

    public abstract void initialize();

}