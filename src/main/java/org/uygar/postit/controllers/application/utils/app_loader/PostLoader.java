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
        this.childController = (PostController) FXLoader.getLoadedController("post", "post");
        this.childController.init(post, controller.dataMiner, WindowDimensions.POST_WINDOW_DIMENSION);
    }

    @Override
    public void load() {
        Stage postStage = getPostStage();
        this.childController.setStage(postStage);
        postStage.show();
    }

    private Stage getPostStage() {
        Stage postStage = windowInitializer
                .initializeApplicationWindowAndGet(WindowDimensions.POST_WINDOW_DIMENSION, Modality.WINDOW_MODAL, childController.post, true);
        postStage.setOnHidden(windowEvent -> {
            parentController.postGrid.nothingSelected();
            parentController.postGrid.enablePostButtonWhenFrameClosed(childController.loadedPost);
        });

        parentController.styleManager.bindStyleSheetWithControllerName("post", "post", childController.post);
        childController.postTabManager.setMinSizeListenerByDimensionOfStage(postStage);
        postStage.setResizable(true);

        return postStage;
    }

}