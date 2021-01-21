package org.uygar.postit.controllers.post.postit.editor_manager.managers;

import javafx.application.Platform;
import javafx.scene.control.TextInputControl;
import org.uygar.postit.controllers.post.postit.editor_manager.PostItEditorManager;

public class FocusManager extends EditorManager {

    public FocusManager(PostItEditorManager manager) {
        super(manager);
    }

    @Override
    public void initialize() {
        requestTaskTextFocus();
    }

    public void requestTaskTextFocus() {
        getManager().getPostItController().titoloField.setFocusTraversable(true);
        Platform.runLater(()-> {
            getManager().getPostItController().titoloField.requestFocus();
            moveTitleTextCaretToEnd();
        });
    }

    private void moveTitleTextCaretToEnd() {
        // Move to the end the caret position of the task text
        getManager().getPostItController().titoloField
                .positionCaret(getManager().getPostItController().compitoField.getText().length());
    }

}