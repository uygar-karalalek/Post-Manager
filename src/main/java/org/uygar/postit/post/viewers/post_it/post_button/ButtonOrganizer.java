package org.uygar.postit.post.viewers.post_it.post_button;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import org.jetbrains.annotations.NotNull;
import org.uygar.postit.data.structures.PostContainerOrganizer;
import org.uygar.postit.data.structures.PostItContainerOrganizer;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.viewers.post.PostViewer;

import java.util.Iterator;

public class ButtonOrganizer implements Iterable<PostButton> {

    ObservableList<PostButton> views = FXCollections.observableArrayList();

    public ButtonOrganizer(PostContainerOrganizer organizer) {
        init(organizer.getPostList());
    }

    private void init(ObservableList<Post> posts) {
        posts.forEach(post -> views.add(getPostViewByPost(post)));
        posts.addListener(this::onChanged);
    }

    public void onChanged(ListChangeListener.Change<? extends Post> change) {
        while (change.next()) {
            if (change.wasAdded())
                change.getAddedSubList()
                        .forEach(post -> views.add(getPostViewByPost(post)));
            else if (change.wasRemoved())
                views.removeIf(postButton ->
                        change.getRemoved().stream()
                                .anyMatch(post -> post.equals(postButton.getPost())));
        }
    }

    public PostButton getLastPostView() {
        return views.get(views.size()-1);
    }

    public PostButton getPostViewByPost(Post element) {
        return new PostViewer(element).getView();
    }

    @NotNull
    @Override
    public Iterator<PostButton> iterator() {
        return views.listIterator();
    }

}
