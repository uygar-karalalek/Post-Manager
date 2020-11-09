package org.uygar.postit.post.viewers.post;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.uygar.postit.data.structures.PostContainerOrganizer;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.viewers.post_it.post_button.ButtonOrganizer;
import org.uygar.postit.post.viewers.post_it.post_button.PostButton;

import java.util.function.Predicate;

public class PostGridViewer extends GridPane {

    public PostContainerOrganizer postOrganizer;
    public ButtonOrganizer buttonOrganizer;
    public ObjectProperty<Post> selected = new SimpleObjectProperty<>();

    private static final double POST_BUTTON_WIDTH_DIVISOR = 2.25;
    private static final double HEIGHT = 300;
    private static final double WIDTH = 500;

    private static final int POST_BUTTON_HEIGHT = 40;
    private static final int DEF_COLS = 2;

    private int rowCount, colCount;

    public PostGridViewer(PostContainerOrganizer postOrganizer) {
        this.setMinSize(WIDTH, HEIGHT);
        this.setId("postGridViewer");
        this.postOrganizer = postOrganizer;
        this.buttonOrganizer = new ButtonOrganizer(postOrganizer);

        Predicate<Post> alwaysCondition = post -> true;
        showPostsByCondition(alwaysCondition);
    }

    private void showPostsByCondition(Predicate<Post> predicate) {
        this.getChildren().clear();
        int num = 0;
        for (PostButton element : buttonOrganizer)
            if (predicate.test(element.getPost()))
                addAndInitPostButton(element, num % DEF_COLS, num++ / DEF_COLS);
        rowCount = num / DEF_COLS;
        colCount = num % DEF_COLS;
    }

    public void updateLastWhenAdded() {
        if (colCount == DEF_COLS) {
            colCount = 0;
            rowCount++;
        }
        addAndInitPostButton(buttonOrganizer.getLastPostView(), colCount++, rowCount);
    }

    public void filterPostsNameContaining(String letters) {
        showPostsByCondition(post -> post.getName().toLowerCase().contains(letters.toLowerCase()));
    }

    public void filterPostsByUnionPredicates(Predicate<Post> predicate) {
        showPostsByCondition(predicate);
    }

    private void addAndInitPostButton(PostButton view, int col, int row) {
        handleOnPostViewSelected(view);
        view.setPrefSize(this.getMinWidth() / POST_BUTTON_WIDTH_DIVISOR, POST_BUTTON_HEIGHT);
        this.add(view, col, row);
    }

    private void handleOnPostViewSelected(PostButton view) {
        view.addEventHandler(MouseEvent.MOUSE_CLICKED,
                mouseEvent -> {
                    selected.set(view.getPost());
                    view.setDisable(true);
                });
    }

    public void nothingSelected() {
        this.selected.set(null);
    }

    public void enablePostButtonWhenFrameClosed(Post post) {
        this.getChildren().stream().filter(node -> {
            if (node instanceof Button)
                return ((Button) node).getText().equals(post.getName());
            return false;
        }).findFirst().ifPresent(postBtn -> postBtn.setDisable(false));
    }

}