package org.uygar.postit.controllers.post.utils.loader;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.uygar.postit.controllers.WindowDimensions;
import org.uygar.postit.controllers.application.FXLoader;
import org.uygar.postit.controllers.loader.WindowLoader;
import org.uygar.postit.controllers.post.PostController;
import org.uygar.postit.controllers.post.postit.PostItController;
import org.uygar.postit.post.PostIt;

public class PostItLoader extends WindowLoader<PostController, PostItController> {

    public PostItLoader(PostIt postIt) {
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
        Stage postItStage = controller.windowInitializer.
                initializeApplicationWindowAndGet(WindowDimensions.POST_IT_WINDOW_DIMENSION,
                        Modality.APPLICATION_MODAL, loadingController.root, true);
        postItStage.initStyle(StageStyle.TRANSPARENT);
        return postItStage;
    }

}