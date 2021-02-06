package org.uygar.postit.controllers.post.postit.editor_manager.managers;

import javafx.scene.control.SpinnerValueFactory;
import org.uygar.postit.controllers.post.postit.editor_manager.PostItEditorManager;

import java.util.stream.IntStream;

public class FieldInitialValuesManager extends EditorManager {

    public static final int MAX_PRIORITY = 10;

    public FieldInitialValuesManager(PostItEditorManager manager) {
        super(manager);
    }

    @Override
    public void initialize() {
        this.getManager().getPostItController().prioritySpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, MAX_PRIORITY));
    }

}