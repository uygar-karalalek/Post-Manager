package org.uygar.postit.post.viewers.post.post_button;

import javafx.scene.control.Button;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Glow;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;
import org.uygar.postit.post.Post;

public class PostButton extends Button {

    private final Post post;
    private Rectangle stateRectangle;
    public static final int RECTANGLE_WIDTH = 10;
    public static final int RECTANGLE_HEIGHT = 5;

    public PostButton(Post post) {
        this.post = post;
        this.textProperty().bind(post.nameProperty());
        initRectangle();
        setFX();
    }

    private void initRectangle() {
        this.stateRectangle = new Rectangle(RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
    }

    public Post getPost() {
        return post;
    }

    private void setFX() {
        stateRectangle.setId("stateCircle");
        this.setGraphic(stateRectangle);
    }

}