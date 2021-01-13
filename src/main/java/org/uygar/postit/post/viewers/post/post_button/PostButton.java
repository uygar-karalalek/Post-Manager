package org.uygar.postit.post.viewers.post.post_button;

import javafx.animation.*;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import org.uygar.postit.post.Post;

public class PostButton extends Button {

    private final Post post;

    public PostButton(Post post) {
        this.post = post;
        this.textProperty().bind(post.nameProperty());
    }

    public Post getPost() {
        return post;
    }

}