package org.uygar.postit.controllers.post.utils.loader;

import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.uygar.postit.controllers.WindowDimensions;
import org.uygar.postit.controllers.application.FXLoader;
import org.uygar.postit.controllers.loader.WindowLoader;
import org.uygar.postit.controllers.post.PostController;
import org.uygar.postit.controllers.post.postit.PostItController;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.PostIt;
import org.uygar.postit.post.viewers.post_it.PostItGridViewer;

import java.util.Optional;

public class PostItLoader extends WindowLoader<PostController, PostItController> {

    public PostItLoader(PostIt postIt, PostItGridViewer postItGrid) {
        initLoadingStageBasedOnFatherPost(postItGrid.getPostItOrganizer().getFatherPost());
        setWindowInitializerByStage(this.stage);
        childController = (PostItController) FXLoader.getLoadedController("postit", "post");
        childController.init(Optional.ofNullable(postIt), postItGrid);
    }

    @Override
    public void load() {
        Stage stage = getPostItStage();
        childController.setStage(stage);
        stage.showAndWait();
    }

    private Stage getPostItStage() {
        Stage postItStage = windowInitializer.
                initializeApplicationWindowAndGet(WindowDimensions.POST_IT_WINDOW_DIMENSION,
                Modality.APPLICATION_MODAL, childController.postIt, true);
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