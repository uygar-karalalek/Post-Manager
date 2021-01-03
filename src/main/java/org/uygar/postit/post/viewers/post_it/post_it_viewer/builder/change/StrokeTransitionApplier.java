package org.uygar.postit.post.viewers.post_it.post_it_viewer.builder.change;

import javafx.animation.StrokeTransition;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.uygar.postit.post.viewers.post_it.post_it_viewer.builder.PostItMouseInteractionManager;

public class StrokeTransitionApplier extends PostItViewEffectApplier {

    public StrokeTransitionApplier(PostItMouseInteractionManager postItMouseInteractionManager) {
        super(postItMouseInteractionManager);
    }

    void applyStroke() {
        applyStroke(false);
    }

    void deApplyStroke() {
        applyStroke(true);
    }

    private void applyStroke(boolean reversed) {
        Color from = reversed ? Color.BLACK : Color.DIMGRAY;
        Color to = reversed ? Color.DIMGRAY : Color.BLACK;

        StrokeTransition strokeTransition = new StrokeTransition(Duration.millis(500),
                getInteractionView().getPostItRectangle());

        strokeTransition.setFromValue(from);
        strokeTransition.setToValue(to);
        strokeTransition.setCycleCount(1);
        strokeTransition.play();
    }

}