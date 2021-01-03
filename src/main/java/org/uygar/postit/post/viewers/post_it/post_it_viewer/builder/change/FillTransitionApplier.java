package org.uygar.postit.post.viewers.post_it.post_it_viewer.builder.change;

import javafx.animation.FillTransition;
import javafx.animation.Interpolator;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.uygar.postit.post.viewers.post_it.post_it_viewer.builder.PostItMouseInteractionManager;

public class FillTransitionApplier extends PostItViewEffectApplier {

    public static final int FILL_DURATION_MILLIS = 300;

    FillTransitionApplier(PostItMouseInteractionManager postItMouseInteractionManager) {
        super(postItMouseInteractionManager);
    }

    void applyFillTransition() {
        boolean postItUndoneAndCurrentColorDifferentFromExpected = getInteractionView().getPostIt().isUndone()
                && (getFillUtils().getCurrentPostItTextColor() != getFillUtils().getExpectedPostItTextColor());

        if (postItUndoneAndCurrentColorDifferentFromExpected)
            applyFillTransition(false);
    }

    void applyReversedFillTransition() {
        // May be happen that when I turn the post-it state to "Undone",
        // the text fill will be stuck on the current color (that is not transparent),
        // so I am constrained to add an "or" to the boolean expression.

        boolean postItUndoneOrCurrentColorDifferentFromTransparent = getInteractionView().getPostIt().isUndone()
                || getFillUtils().getCurrentPostItTextColor() != Color.TRANSPARENT;

        if (postItUndoneOrCurrentColorDifferentFromTransparent)
            applyFillTransition(true);
    }

    void applyFillTransition(boolean reversed) {
        Color from = reversed ? getInteractionView().getPostIt().getColore().postItTextColor : Color.TRANSPARENT;
        Color to = reversed ? Color.TRANSPARENT : getInteractionView().getPostIt().getColore().postItTextColor;

        FillTransition fillTransition = new FillTransition(Duration.millis(FILL_DURATION_MILLIS),
                getInteractionView().getScadenzaText());

        fillTransition.setFromValue(from);
        fillTransition.setToValue(to);
        fillTransition.setInterpolator(Interpolator.EASE_BOTH);
        fillTransition.setCycleCount(1);
        fillTransition.play();
    }

}