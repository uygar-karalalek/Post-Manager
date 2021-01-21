package org.uygar.postit.controllers.post.postit.editor_manager.managers;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;
import org.uygar.postit.controllers.post.postit.editor_manager.PostItEditorManager;

public class EditorDragManager extends EditorManager {

    private double initialX, initialY;

    public EditorDragManager(PostItEditorManager manager) {
        super(manager);
    }

    @Override
    public void initialize() {
        initMouseInteractionEvents();
    }

    private void initMouseInteractionEvents() {
        addTranslateEventsTo(getManager().getPostItController().propertyBox);
        addTranslateEventsTo(getManager().getPostItController().postItRectangle);
    }

    private void addTranslateEventsTo(Node property) {
        property.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onMousePressed);
        property.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::onMouseDragged);
    }

    private void onMousePressed(MouseEvent event) {
        initialX = event.getSceneX();
        initialY = event.getSceneY();
    }

    private void onMouseDragged(MouseEvent event) {
        Window window = getManager().getPostItController().postIt.getScene().getWindow();

        double nextX = event.getScreenX(), nextY = event.getScreenY();
        window.setX(nextX - initialX);
        window.setY(nextY - initialY);
    }

}