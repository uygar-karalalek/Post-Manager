package org.uygar.postit.controllers.post.postit.editor_manager.managers;

import javafx.scene.control.SpinnerValueFactory;
import org.uygar.postit.controllers.post.postit.editor_manager.PostItEditorManager;

import java.util.stream.IntStream;

public class InitialValuesManager extends EditorManager {

    public static final int MAX_PRIORITY = 10;
    public static final int MIN_PRIORITY = 1;

    public InitialValuesManager(PostItEditorManager manager) {
        super(manager);
    }

    @Override
    public void initialize() {
        this.getManager().getPostItController().prioritySpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(MIN_PRIORITY, MAX_PRIORITY));
    }

    public static boolean isPriorityValid(Integer priority) {
        return priority != null && (priority >= MIN_PRIORITY && priority <= MAX_PRIORITY);
    }

}