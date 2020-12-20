package org.uygar.postit.post.viewers.post_it.post_button;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.jetbrains.annotations.NotNull;
import org.uygar.postit.data.structures.PostContainerOrganizer;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.viewers.post.PostViewer;

import java.util.Iterator;

public class ButtonOrganizer implements Iterable<PostButton> {

    ObservableList<PostButton> views = FXCollections.observableArrayList();
    PostContainerOrganizer posts;

    public ButtonOrganizer(PostContainerOrganizer organizer) {
        this.posts = organizer;
        init();
    }

    private void init() {
        posts.getPostList().addListener(this::onChanged);
        posts.forEach(this::addPostToButtonOrganizer);
    }

    public void onChanged(ListChangeListener.Change<? extends Post> change) {
        while (change.next()) {
            if (change.wasAdded())
                change.getAddedSubList().forEach(this::addPostToButtonOrganizer);
            else if (change.wasRemoved())
                views.removeIf(postButton ->
                        change.getRemoved().stream()
                                .anyMatch(post -> post.equals(postButton.getPost())));
        }
    }

    public void addPostToButtonOrganizer(Post post) {
        PostViewer postView = new PostViewer(post);
        views.add(postView.getView());
        addDeleteChangeListener(postView);
    }

    private void addDeleteChangeListener(PostViewer postViewer) {
        postViewer.deletedProperty().addListener((obs, oldVal, newVal) -> {
            if (postViewer.isDeleted())
                posts.getPostList().removeIf(postViewer::equals);
        });
    }

    public PostButton getLastPostView() {
        return views.get(views.size()-1);
    }

    @NotNull
    @Override
    public Iterator<PostButton> iterator() {
        return views.listIterator();
    }

}
