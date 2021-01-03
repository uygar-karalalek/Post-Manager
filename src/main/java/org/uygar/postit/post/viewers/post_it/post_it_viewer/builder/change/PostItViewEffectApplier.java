package org.uygar.postit.post.viewers.post_it.post_it_viewer.builder.change;

import javafx.animation.Transition;
import javafx.scene.paint.Color;
import org.uygar.postit.post.viewers.post_it.post_it_viewer.PostItViewer;
import org.uygar.postit.post.viewers.post_it.post_it_viewer.builder.PostItMouseInteractionManager;

public class PostItViewEffectApplier {

    private final PostItMouseInteractionManager postItMouseInteractionManager;
    private final FillUtils fillUtils;

    public PostItViewEffectApplier(PostItMouseInteractionManager postItMouseInteractionManager) {
        this.postItMouseInteractionManager = postItMouseInteractionManager;
        this.fillUtils = new FillUtils();

    }

    public PostItMouseInteractionManager getPostItMouseInteractionManager() {
        return postItMouseInteractionManager;
    }

    protected PostItViewer getInteractionView() {
        return getPostItMouseInteractionManager().getInteractionView();
    }

    public FillUtils getFillUtils() {
        return fillUtils;
    }

    public class FillUtils {

        protected Color getCurrentPostItTextColor() {
            return (Color) getInteractionView().getScadenzaText().getFill();
        }

        protected Color getCurrentPostItTextColorWithFullOpacity() {
            // Sometimes the JVM turns itself the opacity of the current text color to 0 when i cast it from paint class to color!
            // So I am constrained to add getCurrentPostItTextColorWithFullOpacity() method

            int fullOpacity = 1;
            Color color = getCurrentPostItTextColor();
            return Color.color(color.getRed(), color.getGreen(), color.getBlue(), fullOpacity);
        }

        protected Color getExpectedPostItTextColor() {
            return getInteractionView().getPostIt().getColore().postItTextColor;
        }

    }

}