package org.uygar.postit.controllers.post.controller_utilities.controller_manager;

import org.uygar.postit.controllers.post.PostController;

public class PostControllerWrapper {

    protected PostController postController;

    public PostControllerWrapper(PostController postController) {
        this.postController = postController;
    }

}