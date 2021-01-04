package org.uygar.postit.post.viewers.post_it.post_it_viewer.builder.change;

import javafx.animation.FillTransition;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.uygar.postit.post.viewers.post_it.post_it_viewer.builder.PostItMouseInteractionManager;

public class RectangleFillTransitionApplier extends PostItViewEffectApplier {

    public RectangleFillTransitionApplier(PostItMouseInteractionManager postItMouseInteractionManager) {
        super(postItMouseInteractionManager);
    }

    public void addRectangleDarkerFillTransition() {
        applyFillTransitionToRectangle(true);
    }

    public void addRectangleBrighterFillTransition() {
        applyFillTransitionToRectangle(false);
    }

    private void applyFillTransitionToRectangle(boolean reversed) {
        Color postItColor = getInteractionView().getPostIt().getColore().postItColor;
        Color brighterPostItColor = postItColor.deriveColor(1, 0.9, 1, 1);

        Color color1 = reversed ? brighterPostItColor : postItColor;
        Color color2 = reversed ? postItColor : brighterPostItColor;

        FillTransition fillTransition = new FillTransition(Duration.millis(100),
                getInteractionView().getPostItRectangle());
        fillTransition.setFromValue(color1);
        fillTransition.setToValue(color2);
        fillTransition.setCycleCount(1);
        fillTransition.play();
    }

}