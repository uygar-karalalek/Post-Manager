package org.uygar.postit.post.viewers.post_it.post_it_viewer.builder.change;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;
import org.uygar.postit.post.viewers.post_it.post_it_viewer.builder.PostItMouseInteractionManager;

public class ScaleTransitionApplier extends PostItViewEffectApplier {

    private static final double SCALE_FACTOR = 1.02;
    private static final long SCALE_DURATION_MILLIS = 100;

    ScaleTransitionApplier(PostItMouseInteractionManager postItMouseInteractionManager) {
        super(postItMouseInteractionManager);
    }

    void applyScaleHigherTransition() {
        applyTransition(false);
    }

    void applyScaleNormalTransition() {
        applyTransition(true);
    }

    private void applyTransition(boolean reversed) {
        double from = reversed ? SCALE_FACTOR : 1.0;
        double to = reversed ? 1.0 : SCALE_FACTOR;

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(SCALE_DURATION_MILLIS),
                getInteractionView().getPostItRectangle());

        scaleTransition.setFromX(from);
        scaleTransition.setFromY(from);
        scaleTransition.setToX(to);
        scaleTransition.setToY(to);
        scaleTransition.setInterpolator(Interpolator.EASE_BOTH);
        scaleTransition.setCycleCount(1);
        scaleTransition.play();
    }

}