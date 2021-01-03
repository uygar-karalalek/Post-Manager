package org.uygar.postit.post.viewers.post_it.post_it_viewer.builder.change;

import javafx.animation.FillTransition;
import javafx.animation.Interpolator;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.uygar.postit.post.viewers.post_it.post_it_viewer.PostItViewer;
import org.uygar.postit.post.viewers.post_it.post_it_viewer.builder.PostItMouseInteractionManager;

public class ChangeListeners {

    private final FillTransitionApplier fillTransitionApplier;
    private final ScaleTransitionApplier scaleTransitionApplier;
    private final StrokeTransitionApplier strokeTransitionApplier;

    public ChangeListeners(PostItMouseInteractionManager postItMouseInteractionManager) {
        this.fillTransitionApplier = new FillTransitionApplier(postItMouseInteractionManager);
        this.scaleTransitionApplier = new ScaleTransitionApplier(postItMouseInteractionManager);
        this.strokeTransitionApplier = new StrokeTransitionApplier(postItMouseInteractionManager);
    }

    public void fillScadenzaTextWrapper(MouseEvent event) {
        fillTransitionApplier.applyFillTransition();
    }

    public void deFillScadenzaTextWrapper(MouseEvent event) {
        fillTransitionApplier.applyReversedFillTransition();
    }

    public void scalePostItRectangleToHigherValue(MouseEvent event) {
        scaleTransitionApplier.applyScaleHigherTransition();
    }

    public void scalePostItRectangleToNormalValue(MouseEvent event) {
        scaleTransitionApplier.applyScaleNormalTransition();
    }

    public void changeStrokePostItRectangle(MouseEvent event) {
        strokeTransitionApplier.applyStroke();
    }

    public void changeStrokePostItRectangleToNormal(MouseEvent event) {
        strokeTransitionApplier.deApplyStroke();
    }

}
