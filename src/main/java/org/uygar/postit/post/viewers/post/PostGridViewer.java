package org.uygar.postit.post.viewers.post;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.uygar.postit.data.structures.PostContainerOrganizer;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.viewers.post.post_button.ButtonOrganizer;
import org.uygar.postit.post.viewers.post.post_button.PostButton;

import java.util.function.Predicate;

public class PostGridViewer extends GridPane {

    public PostContainerOrganizer postOrganizer;
    public ButtonOrganizer buttonOrganizer;
    public ObjectProperty<Post> selected = new SimpleObjectProperty<>();

    private static final double POST_BUTTON_WIDTH_DIVISOR = 2.25;
    private static final double HEIGHT = 300;
    private static final double WIDTH = 500;

    private static final double POST_BUTTON_HEIGHT = 60;
    private static final int DEF_COLS = 2;

    public PostGridViewer(PostContainerOrganizer postOrganizer) {
        this.setMinSize(WIDTH, HEIGHT);
        this.setId("postGridViewer");
        this.postOrganizer = postOrganizer;
        this.buttonOrganizer = new ButtonOrganizer(postOrganizer);
        this.postOrganizer.getPostList().addListener(this::onPostListChanged);

        Predicate<Post> always = post -> true;
        showPostsByCondition(always);
    }

    private void showPostsByCondition(Predicate<Post> predicate) {
        this.getChildren().clear();
        int postItNumber = 0;
        for (PostButton element : buttonOrganizer) {
            if (predicate.test(element.getPost())) {
                addAndInitPostButton(element, getColCount(postItNumber), getRowCount(postItNumber));
                postItNumber++;
            }
        }
    }

    public void onPostListChanged(ListChangeListener.Change<? extends Post> change) {
        while (change.next()) {
            if (change.wasAdded())
                updateLastWhenAdded();
            else if (change.wasRemoved())
                change.getRemoved().forEach(this::updateWhenRemoved);
        }
    }

    public void updateLastWhenAdded() {
        int postIndex = this.getChildren().size();
        int col = getColCount(postIndex);
        int row = getRowCount(postIndex);

        addAndInitPostButton(buttonOrganizer.getLastPostView(), col, row);
    }

    public void updateWhenRemoved(Post post) {
        int removedIndex = findPostIndexInViewList(post);
        this.getChildren().remove(removedIndex);
        onPostRemovedChangeButtonConstraints(removedIndex);
    }

    private void onPostRemovedChangeButtonConstraints(int postIndex) {
        if (postIndex == this.getChildren().size()) return;
        GridPane.setConstraints(this.getChildren().get(postIndex), getColCount(postIndex), getRowCount(postIndex));
        onPostRemovedChangeButtonConstraints(++postIndex);
    }

    public void filterPostsNameContaining(String letters) {
        showPostsByCondition(post -> post.getName().toLowerCase().contains(letters.toLowerCase()));
    }

    public void filterPostsByUnionPredicates(Predicate<Post> predicate) {
        showPostsByCondition(predicate);
    }

    private void addAndInitPostButton(PostButton view, int col, int row) {
        handleOnPostViewSelected(view);
        view.prefWidthProperty().bind(this.widthProperty().divide(POST_BUTTON_WIDTH_DIVISOR));
        view.prefHeightProperty().bind(this.heightProperty().multiply(POST_BUTTON_HEIGHT / 487));
        this.add(view, col, row);
    }

    private void handleOnPostViewSelected(PostButton view) {
        view.addEventHandler(MouseEvent.MOUSE_CLICKED,
                mouseEvent -> {
                    selected.set(view.getPost());
                    view.setDisable(true);
                });
    }

    public void noPostSelected() {
        this.selected.set(null);
    }

    public int findPostIndexInViewList(Post post) {
        for (int i = 0; i < this.getChildren().size(); i++) {
            Node current = this.getChildren().get(i);
            if (current instanceof PostButton) {
                PostButton postButton = (PostButton) current;
                if (postButton.getPost().equals(post))
                    return i;
            }
        }
        return -1;
    }

    public void enablePostApplicationButton(Post post) {
        this.getChildren().stream().filter(node -> {
            if (node instanceof Button)
                return ((Button) node).getText().equals(post.getName());
            return false;
        }).findFirst().ifPresent(postBtn -> postBtn.setDisable(false));
    }

    private int getColCount(int postIndex) {
        return postIndex % DEF_COLS;
    }

    private int getRowCount(int postIndex) {
        return postIndex / DEF_COLS;
    }

}