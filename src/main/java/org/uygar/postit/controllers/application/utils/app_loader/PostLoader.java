package org.uygar.postit.controllers.application.utils.app_loader;

import javafx.stage.Modality;
import javafx.stage.Stage;
import org.uygar.postit.controllers.ControllerType;
import org.uygar.postit.controllers.WindowDimensions;
import org.uygar.postit.controllers.application.FXLoader;
import org.uygar.postit.controllers.application.app.AppController;
import org.uygar.postit.controllers.loader.WindowLoader;
import org.uygar.postit.controllers.post.PostController;
import org.uygar.postit.post.Post;

public class PostLoader extends WindowLoader<AppController, PostController> {

    public PostLoader(AppController controller, Post post) {
        super(controller, ControllerType.APPLICATION);
        this.loadingController = (PostController) FXLoader.getLoadedController("post", "post");
        this.loadingController.init(post, controller.dataMiner, WindowDimensions.POST_WINDOW_DIMENSION);
    }

    @Override
    public void load() {
        Stage postStage = getPostStage();
        this.loadingController.setStage(postStage);
        postStage.show();
    }

    private Stage getPostStage() {
        Stage postStage = windowInitializer
                .initializeApplicationWindowAndGet(WindowDimensions.POST_WINDOW_DIMENSION, Modality.WINDOW_MODAL, loadingController.post, true);
        postStage.setOnHidden(windowEvent -> {
            controller.postGrid.nothingSelected();
            controller.postGrid.enablePostButtonWhenFrameClosed(loadingController.loadingPost);
        });

        controller.bindStyleSheetWithControllerName("post", "post", loadingController.post);
        loadingController.setMinSizeListenerByDimensionOfStage(postStage);
        postStage.setResizable(true);

        return postStage;
    }

}