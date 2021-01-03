package org.uygar.postit.post.viewers.post;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.viewers.post.post_button.PostButton;

public class PostViewer extends Post {

    private PostButton view;

    public PostViewer(Post post) {
        super(post.getId(), post.getName(), post.getCreationDate(), post.getLastModifiedDate(), post.getSortType());
        initView();
    }

    public void initView() {
        this.view = new PostButton(this);
        this.view.setId("post");
        BooleanProperty selected = new SimpleBooleanProperty();
        this.view.setUserData(selected);
    }

    public PostButton getView() { return view; }

}