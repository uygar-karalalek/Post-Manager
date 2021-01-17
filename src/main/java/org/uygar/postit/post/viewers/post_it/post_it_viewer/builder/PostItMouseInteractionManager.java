package org.uygar.postit.post.viewers.post_it.post_it_viewer.builder;

import javafx.scene.input.MouseEvent;
import org.uygar.postit.post.viewers.post_it.post_it_viewer.PostItViewer;
import org.uygar.postit.post.viewers.post_it.post_it_viewer.builder.change.ChangeListeners;
import org.uygar.postit.post.viewers.post_it.post_it_viewer.builder.graphic_builder.utility.PostItStateImageActionsRepository;

public class PostItMouseInteractionManager {

    private ChangeListeners changeListeners;
    private final PostItViewer interactionView;
    private final PostItStateImageActionsRepository postItStateImageManager;

    public PostItMouseInteractionManager(PostItViewer viewer) {
        this.postItStateImageManager = new PostItStateImageActionsRepository(viewer);
        this.interactionView = viewer;
        manage();
    }

    public void manage() {
        this.changeListeners = new ChangeListeners(this);
        this.interactionView.getPostIt()
                .dataScadenzaProperty()
                .addListener(postItStateImageManager::onPostItDeadlineChange);
        manageInteractions();
    }

    public void changePostItAspectBasedOnState() {
        this.interactionView.getPostIt().setFatto(!this.interactionView.getPostIt().isFatto());
        if (this.interactionView.getPostIt().isFatto())
            postItStateImageManager.addToPostItDoneImageIfNotExpired();
        else
            postItStateImageManager.removeFromPostItImageWithEndPath("fatto.png");
        if (postItStateImageManager.isPostItExpiredAndUndone())
            postItStateImageManager.addToPostItExpiredImage();
    }

    private void manageInteractions() {
        managePostItEnteredCursor();
        managePostItExitedCursor();
    }

    private void managePostItEnteredCursor() {
        interactionView.addEventFilter(MouseEvent.MOUSE_ENTERED, changeListeners::fillScadenzaTextWrapper);
        interactionView.addEventFilter(MouseEvent.MOUSE_ENTERED, changeListeners::scalePostItRectangleToHigherValue);
        interactionView.addEventFilter(MouseEvent.MOUSE_ENTERED, changeListeners::changeStrokePostItRectangle);
        interactionView.addEventFilter(MouseEvent.MOUSE_ENTERED, changeListeners::changeFillPostItRectangleToBrighter);
    }

    private void managePostItExitedCursor() {
        interactionView.addEventFilter(MouseEvent.MOUSE_EXITED, changeListeners::deFillScadenzaTextWrapper);
        interactionView.addEventFilter(MouseEvent.MOUSE_EXITED, changeListeners::scalePostItRectangleToNormalValue);
        interactionView.addEventFilter(MouseEvent.MOUSE_EXITED, changeListeners::changeStrokePostItRectangleToNormal);
        interactionView.addEventFilter(MouseEvent.MOUSE_EXITED, changeListeners::changeFillPostItRectangleToDarker);
    }

    public PostItViewer getInteractionView() {
        return interactionView;
    }

}