package org.uygar.postit.controllers.post.controller_utilities.loader;

import javafx.scene.paint.Color;
import javafx.stage.*;
import org.uygar.postit.controllers.WindowDimensions;
import org.uygar.postit.controllers.application.FXLoader;
import org.uygar.postit.controllers.application.utils.WindowInitializer;
import org.uygar.postit.controllers.loader.WindowLoader;
import org.uygar.postit.controllers.post.PostController;
import org.uygar.postit.controllers.post.controller_utilities.loader.postit_responsabilities.PostItResponsibilities;
import org.uygar.postit.controllers.post.postit.PostItController;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.viewers.post_it.PostItGridViewer;
import org.uygar.postit.post.viewers.post_it.post_it_viewer.PostItViewer;

import java.util.Optional;

public class PostItCreator extends WindowLoader<PostController, PostItController> {

    private final PostItViewer postItViewer;
    private final PostItGridViewer postItGridViewer;
    private final PostItResponsibilities postItResponsibilities;

    public PostItCreator(PostItViewer postItViewer, PostItGridViewer postItGrid) {
        this.postItViewer = postItViewer;
        this.postItGridViewer = postItGrid;
        this.postItResponsibilities = new PostItResponsibilities(this);

        this.initLoadingStageBasedOnFatherPost(this.postItGridViewer.getPostItOrganizer().getFatherPost());
        this.setWindowInitializerByStage(this.parentStage);

        this.childController = (PostItController) FXLoader.getLoadedController("postit", "post");
        this.childController.init(Optional.ofNullable(postItViewer == null ? null : this.postItViewer.getPostIt()), this.postItGridViewer);
    }

    private Stage initializedPostItStage() {
        Stage postItStage = this.getWindowInitializer().
                initializeApplicationWindowAndGet(WindowDimensions.POST_IT_WINDOW_DIMENSION,
                        Modality.APPLICATION_MODAL, this.getPostItController().postIt, true);

        this.postItResponsibilities.getDimensionalBoundResponsibilities().initStageBounds(postItStage);
        postItStage.initStyle(StageStyle.TRANSPARENT);
        postItStage.getScene().setFill(Color.TRANSPARENT);

        return postItStage;
    }

    private void initLoadingStageBasedOnFatherPost(Post fatherPost) {
        this.parentStage = postItResponsibilities.getLoadingResponsibilities().searchStageWithFatherPost(fatherPost);
    }

    @Override
    public void load() {
        Stage stage = initializedPostItStage();
        this.childController.setStage(stage);
        stage.setOnShowing(this.postItResponsibilities.getResponseResponsibilities()::onStageShowingLowOpacityOfOtherPostIts);
        stage.setOnHidden(this.postItResponsibilities.getResponseResponsibilities()::onStageResetOpacityOfOtherPostIts);
        stage.showAndWait();
    }

    // TODO : Maybe these are not placed in the right way...

    public PostItViewer getPostItViewer() {
        return this.postItViewer;
    }

    public PostItGridViewer getPostItGridViewer() {
        return this.postItGridViewer;
    }

    public WindowInitializer getWindowInitializer() {
        return this.windowInitializer;
    }

    public PostItController getPostItController() {
        return this.childController;
    }

}