package org.uygar.postit.controllers.post.postit.editor_manager.managers;

import javafx.application.Platform;
import org.uygar.postit.controllers.post.postit.editor_manager.PostItEditorManager;

public class FocusManager extends EditorManager {

    public FocusManager(PostItEditorManager manager) {
        super(manager);
    }

    public void requestTaskTextFocus() {
        getManager().getPostItController().compitoField.setFocusTraversable(true);
        Platform.runLater(()-> {
            getManager().getPostItController().compitoField.requestFocus();

            // Move to the end the caret position of the task text
            getManager().getPostItController().compitoField
                    .positionCaret(getManager().getPostItController().compitoField.getText().length());
        });
    }

}