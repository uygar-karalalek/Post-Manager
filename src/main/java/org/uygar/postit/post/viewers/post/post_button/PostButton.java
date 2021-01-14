package org.uygar.postit.post.viewers.post.post_button;

import javafx.animation.*;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import org.uygar.postit.post.Post;

public class PostButton extends Button {

    private final Post post;
    private final Circle stateCircle;
    public static final int CIRCLE_RADIUS = 10;

    public PostButton(Post post) {
        this.post = post;
        this.textProperty().bind(post.nameProperty());
        this.stateCircle = new Circle(CIRCLE_RADIUS);
        setFX();
    }

    public Post getPost() {
        return post;
    }

    private void setFX() {
        stateCircle.setId("stateCircle");
        this.setGraphic(stateCircle);
    }

}