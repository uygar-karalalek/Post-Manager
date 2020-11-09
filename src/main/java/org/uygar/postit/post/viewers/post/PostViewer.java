package org.uygar.postit.post.viewers.post;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.properties.Sort;
import org.uygar.postit.post.viewers.post_it.post_button.PostButton;

import java.time.LocalDateTime;

public class PostViewer extends Post {

    private PostButton view;

    public PostViewer(int id, String name, LocalDateTime creationDate, LocalDateTime lastModifiedDate, Sort sortType) {
        super(id, name, creationDate, lastModifiedDate, sortType);
        initView();
    }

    public PostViewer(int id, String name, LocalDateTime creationDate, LocalDateTime lastModifiedDate) {
        super(id, name, creationDate, lastModifiedDate, Sort.UNDONE);
        initView();
    }

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