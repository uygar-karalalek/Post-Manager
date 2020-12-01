package org.uygar.postit.controllers.post.utils.loader;

import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.uygar.postit.controllers.ControllerType;
import org.uygar.postit.controllers.WindowDimensions;
import org.uygar.postit.controllers.application.FXLoader;
import org.uygar.postit.controllers.loader.WindowLoader;
import org.uygar.postit.controllers.post.PostController;
import org.uygar.postit.controllers.post.postit.PostItController;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.PostIt;

public class PostItLoader extends WindowLoader<PostController, PostItController> {

    public PostItLoader(PostIt postIt, Post fatherPost) {
        initPostStageWithFatherPost(fatherPost);
        setWindowInitializerByStage(this.stage);
        loadingController = (PostItController) FXLoader.getLoadedController("postit", "post");
        loadingController.init(postIt);
    }

    @Override
    public void load() {
        Stage stage = getPostItStage();
        loadingController.setStage(stage);
        stage.showAndWait();
    }

    private Stage getPostItStage() {
        Stage postItStage = windowInitializer.initializeApplicationWindowAndGet(WindowDimensions.POST_IT_WINDOW_DIMENSION,
                        Modality.APPLICATION_MODAL, loadingController.postIt, true);
        postItStage.initStyle(StageStyle.TRANSPARENT);
        postItStage.getScene().setFill(Color.TRANSPARENT);
        return postItStage;
    }

    private void initPostStageWithFatherPost(Post fatherPost) {
        Stage.getWindows().forEach(window -> {
            Object userData = window.getScene().getRoot().getUserData();
            if (userData instanceof Post)
                if (fatherPost.equals(userData))
                    stage = (Stage) window;
        });
    }

}