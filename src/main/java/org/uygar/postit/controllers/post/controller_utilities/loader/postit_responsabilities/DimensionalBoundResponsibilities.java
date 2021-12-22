package org.uygar.postit.controllers.post.controller_utilities.loader.postit_responsabilities;

import javafx.geometry.Bounds;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.uygar.postit.controllers.WindowDimensions;
import org.uygar.postit.controllers.post.controller_utilities.loader.PostItCreator;
import org.uygar.postit.post.viewers.post_it.post_it_viewer.PostItViewer;

public class DimensionalBoundResponsibilities extends Responsibilities {

    public static final int HORIZONTAL_INVISIBLE_BORDER = 200;
    public static final int VERTICAL_INVISIBLE_BORDER = 50;

    public DimensionalBoundResponsibilities(PostItCreator postItCreator) {
        super(postItCreator);
    }

    public void initStageBounds(Stage stage) {
        setInitialBoundsToPostItStage(stage);
    }

    private void setInitialBoundsToPostItStage(Stage postItStage) {
        if (postItCreator.getPostItViewer() == null) return;
        Bounds position = postItCreator.getPostItViewer().localToScreen(postItCreator.getPostItGridViewer().getBoundsInLocal());

        double yPosition = getYPosition(position);
        postItStage.setX(position.getMinX() - HORIZONTAL_INVISIBLE_BORDER);
        postItStage.setY(yPosition);
    }

    private double getYPosition(Bounds position) {
        if (isNotCompletelyVisible(position)) return getPostItExternalYPosition();
        return getPostItInternalYPosition(position);
    }

    private boolean isNotCompletelyVisible(Bounds position) {
        double postItY = position.getMinY() + PostItViewer.POST_IT_SIZE;
        return postItY > getStageMaxY();
    }

    private double getPostItInternalYPosition(Bounds position) {
        return position.getMinY() - VERTICAL_INVISIBLE_BORDER;
    }

    // This is the one of the post-it that lies BELOW the layout!!!
    // So it is not completely shown by the scroll layout as view!
    private double getPostItExternalYPosition() {
        return getStageMaxY() - WindowDimensions.POST_IT_WINDOW_DIMENSION.getHeight() - VERTICAL_INVISIBLE_BORDER;
    }

    private double getStageMaxY() {
        double stageY = postItCreator.getPostItGridViewer().getScene().getWindow().getY();
        double stageHeight = postItCreator.getPostItGridViewer().getScene().getWindow().getHeight();
        return stageY + stageHeight;
    }

}