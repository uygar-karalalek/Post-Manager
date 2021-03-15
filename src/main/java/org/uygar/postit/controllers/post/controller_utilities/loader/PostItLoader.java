package org.uygar.postit.controllers.post.controller_utilities.loader;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.uygar.postit.controllers.WindowDimensions;
import org.uygar.postit.controllers.application.FXLoader;
import org.uygar.postit.controllers.loader.WindowLoader;
import org.uygar.postit.controllers.post.PostController;
import org.uygar.postit.controllers.post.postit.PostItController;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.PostIt;
import org.uygar.postit.post.viewers.post_it.PostItGridViewer;
import org.uygar.postit.post.viewers.post_it.post_it_viewer.PostItViewer;

import java.util.Optional;

public class PostItLoader extends WindowLoader<PostController, PostItController> {

    public static final int HORIZONTAL_INVISIBLE_BORDER = 200;
    public static final int VERTICAL_INVISIBLE_BORDER = 50;
    private final PostItViewer postItViewer;
    private final PostItGridViewer postItGridViewer;

    public PostItLoader(PostItViewer postItViewer, PostItGridViewer postItGrid) {
        this.postItViewer = postItViewer;
        this.postItGridViewer = postItGrid;

        this.initLoadingStageBasedOnFatherPost(this.postItGridViewer.getPostItOrganizer().getFatherPost());
        this.setWindowInitializerByStage(this.stage);
        this.childController = (PostItController) FXLoader.getLoadedController("postit", "post");
        this.childController.init(Optional.ofNullable(postItViewer == null ? null : this.postItViewer.getPostIt()), this.postItGridViewer);
    }

    @Override
    public void load() {
        Stage stage = getPostItStage();
        childController.setStage(stage);
        stage.setOnShowing(this::onStageShowingLowOpacityOfOtherPostIts);
        stage.setOnHidden(this::onStageResetOpacityOfOtherPostIts);
        stage.showAndWait();
    }

    private void onStageShowingLowOpacityOfOtherPostIts(WindowEvent event) {
        postItGridViewer.getChildren().stream().filter
                (node -> node != postItViewer).forEach(node -> node.setOpacity(0.6));
    }

    private void onStageResetOpacityOfOtherPostIts(WindowEvent event) {
        postItGridViewer.getChildren().forEach(node -> node.setOpacity(1));
    }

    private Stage getPostItStage() {
        Stage postItStage = windowInitializer.
                initializeApplicationWindowAndGet(WindowDimensions.POST_IT_WINDOW_DIMENSION,
                Modality.APPLICATION_MODAL, childController.postIt, true);

        if (postItViewer != null) {
            Bounds position = postItViewer.localToScreen(postItViewer.getBoundsInLocal());

            double yPosition = Math.min(position.getMinY() - VERTICAL_INVISIBLE_BORDER,
                    postItViewer.getScene().getWindow().getHeight() - PostItViewer.POST_IT_SIZE);
            postItStage.setX(position.getMinX() - HORIZONTAL_INVISIBLE_BORDER);
            postItStage.setY(yPosition);
        }

        postItStage.initStyle(StageStyle.TRANSPARENT);
        postItStage.getScene().setFill(Color.TRANSPARENT);
        return postItStage;
    }

    private void initLoadingStageBasedOnFatherPost(Post fatherPost) {
        this.stage = searchStageWithFatherPost(fatherPost);
    }

    private Stage searchStageWithFatherPost(Post fatherPost) {
        return (Stage) Stage.getWindows().stream().filter(window -> {
            Object userData = window.getScene().getRoot().getUserData();
            if (userData instanceof Post)
                return fatherPost.equals(userData);
            return false;
        }).findFirst().orElseThrow();
    }

}