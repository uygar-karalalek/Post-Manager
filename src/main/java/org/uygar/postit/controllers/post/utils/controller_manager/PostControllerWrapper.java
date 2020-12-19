package org.uygar.postit.controllers.post.utils.controller_manager;

import org.uygar.postit.controllers.post.PostController;

public class PostControllerWrapper {

    protected PostController postController;

    public PostControllerWrapper(PostController postController) {
        this.postController = postController;
    }

}