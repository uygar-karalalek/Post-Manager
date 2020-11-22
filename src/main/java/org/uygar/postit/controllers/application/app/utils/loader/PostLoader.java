package org.uygar.postit.controllers.application.app.utils.loader;

import javafx.stage.Modality;
import javafx.stage.Stage;
import org.uygar.postit.controllers.WindowDimensions;
import org.uygar.postit.controllers.application.FXLoader;
import org.uygar.postit.controllers.application.app.AppController;
import org.uygar.postit.controllers.post.PostController;
import org.uygar.postit.post.Post;

public class PostLoader extends WindowLoader {

    private final PostController postController;

    public PostLoader(AppController controller, Post post) {
        super(controller);
        postController = (PostController) FXLoader.getLoadedController("post", "post");
        postController.init(post, controller.dataMiner, WindowDimensions.POST_WINDOW_DIMENSION);
    }

    private Stage getPostStage(PostController postController) {
        Stage postStage = appController.initializeWindowAndGet(WindowDimensions.POST_WINDOW_DIMENSION, Modality.WINDOW_MODAL, postController.root);
        postStage.setOnHidden(windowEvent -> {
            appController.postGrid.nothingSelected();
            appController.postGrid.enablePostButtonWhenFrameClosed(postController.post);
        });

        appController.addStylesheetToPaneWithControllerName("post", "post", postController.root);
        postController.setMinSizeListenerByDimensionOfStage(postStage);
        postStage.setResizable(true);

        return postStage;
    }

    @Override
    public void load() {
        Stage postStage = getPostStage(postController);
        postStage.show();
    }

}