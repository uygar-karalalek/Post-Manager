package org.uygar.postit.post.viewers.post_it.post_button;

import javafx.scene.control.Button;
import org.uygar.postit.post.Post;

public class PostButton extends Button {

    private final Post post;

    public PostButton(Post post) {
        this.textProperty().bind(post.nameProperty());
        this.post = post;
    }

    public Post getPost() {
        return post;
    }

}