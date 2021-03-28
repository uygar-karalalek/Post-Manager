package org.uygar.postit.controllers.post.controller_utilities.loader.postit_responsabilities;

import javafx.stage.WindowEvent;
import org.uygar.postit.controllers.post.controller_utilities.loader.PostItCreator;

public class ResponseResponsibilities extends Responsibilities {

    public ResponseResponsibilities(PostItCreator postItCreator) {
        super(postItCreator);
    }

    public void onStageShowingLowOpacityOfOtherPostIts(WindowEvent event) {
        postItCreator.getPostItGridViewer().getChildren().stream().filter
                (node -> node != postItCreator.getPostItViewer()).forEach(node -> node.setOpacity(0.6));
    }

    public void onStageResetOpacityOfOtherPostIts(WindowEvent event) {
        postItCreator.getPostItGridViewer().getChildren().forEach(node -> node.setOpacity(1));
    }

}