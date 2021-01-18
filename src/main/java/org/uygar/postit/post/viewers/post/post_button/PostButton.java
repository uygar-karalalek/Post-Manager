package org.uygar.postit.post.viewers.post.post_button;

import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.util.Duration;
import org.uygar.postit.post.Post;

public class PostButton extends Button {

    private final Post post;
    private Path stateShape;

    public PostButton(Post post) {
        this.post = post;
        initShape();
        this.textProperty().bind(post.nameProperty());
    }

    private void initShape() {
        this.stateShape = new Path();

        this.stateShape.getElements().addAll(
                new MoveTo(0,0),
                new LineTo(14, 7.5),
                new LineTo(0, 15)
        );

        stateShape.setId("stateShape");
        this.setGraphic(stateShape);
    }

    public Post getPost() {
        return post;
    }

}