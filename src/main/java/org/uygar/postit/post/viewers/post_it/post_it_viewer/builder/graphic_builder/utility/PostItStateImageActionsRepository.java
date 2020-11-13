package org.uygar.postit.post.viewers.post_it.post_it_viewer.builder.graphic_builder.utility;

import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.post.viewers.post_it.post_it_viewer.PostItViewer;

import java.time.LocalDateTime;

public class PostItStateImageActionsRepository {

    private final PostItViewer viewer;

    public PostItStateImageActionsRepository(PostItViewer viewer) {
        this.viewer = viewer;
        buildPostItImageGeneralAspect();
    }

    private void buildPostItImageGeneralAspect() {
        if (viewer.getPostIt().isFatto())
            addToPostItDoneImageIfNotExpired();
        else if (isPostItExpiredAndUndone())
            addToPostItExpiredImage();
    }

    public void addToPostItDoneImageIfNotExpired() {
        if (viewer.getPostIt().isExpired())
            removeFromPostItImageWithEndPath("expired.png");
        viewer.getGraphicBuilder().getPostItImageWrapper()
                .getChildren().add(new ImageView("org/uygar/images/fatto.png"));
    }

    public void addToPostItExpiredImage() {
        viewer.getGraphicBuilder().getPostItImageWrapper()
                .getChildren().add(new ImageView("org/uygar/images/expired.png"));
    }

    public void onPostItDeadlineChange(ObservableValue<? extends LocalDateTime> obs,
                                       LocalDateTime oldVal, LocalDateTime newVal) {
        if (isPostItExpiredAndUndone())
            addToPostItExpiredImage();
    }

    public boolean isPostItExpiredAndUndone() {
        return viewer.getPostIt().isExpired() && !viewer.getPostIt().isFatto();
    }

    public void removeFromPostItImageWithEndPath(String endPath) {
        viewer.getGraphicBuilder().getPostItImageWrapper()
                .getChildren()
                .removeIf(node -> {
                    if (node instanceof ImageView)
                        return ((ImageView) node).getImage().getUrl()
                                .endsWith(endPath);
                    return false;
                });
    }

}