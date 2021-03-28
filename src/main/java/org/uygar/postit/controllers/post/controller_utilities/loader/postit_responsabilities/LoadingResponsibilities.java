package org.uygar.postit.controllers.post.controller_utilities.loader.postit_responsabilities;

import javafx.stage.Stage;
import org.uygar.postit.controllers.post.controller_utilities.loader.PostItCreator;
import org.uygar.postit.post.Post;

public class LoadingResponsibilities extends Responsibilities {

    public LoadingResponsibilities(PostItCreator postItCreator) {
        super(postItCreator);
    }

    public Stage searchStageWithFatherPost(Post fatherPost) {
        return (Stage) Stage.getWindows().stream().filter(window -> {
            Object userData = window.getScene().getRoot().getUserData();
            if (userData instanceof Post)
                return fatherPost.equals(userData);
            return false;
        }).findFirst().orElseThrow();
    }

}