package org.uygar.postit.post.viewers.post;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.uygar.postit.data.structures.PostContainerOrganizer;
import org.uygar.postit.post.Post;

import java.util.function.Predicate;

public class PostGridViewer extends GridPane {

    public PostContainerOrganizer postOrganizer;
    public ObjectProperty<Post> selected = new SimpleObjectProperty<>();

    private static final double POST_BUTTON_WIDTH_DIVISOR = 2.25;
    private static final double HEIGHT = 300;
    private static final double WIDTH = 500;

    private static final int POST_BUTTON_HEIGHT = 40;
    private static final int DEF_COLS = 2;

    int rowCount, colCount;

    public PostGridViewer(PostContainerOrganizer postOrganizer) {
        this.setMinSize(WIDTH, HEIGHT);
        this.setId("postGridViewer");
        this.postOrganizer = postOrganizer;
        init(post -> true);
    }

    private void init(Predicate<Post> predicate) {
        this.getChildren().clear();
        int num = 0;
        for (Post element : postOrganizer) {
            if (predicate.test(element)) {
                Button view = new PostViewer(element).getView();
                view.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> selected.set(element));
                view.setPrefSize(this.getMinWidth() / POST_BUTTON_WIDTH_DIVISOR, POST_BUTTON_HEIGHT);
                this.add(view, num % DEF_COLS, num++ / DEF_COLS);
            }
        }
        rowCount = num / DEF_COLS;
        colCount = num % DEF_COLS;
    }

    public void updateLast() {
        Post post = postOrganizer.getLastPost();
        PostViewer postViewer = new PostViewer(post);
        if (colCount == DEF_COLS) {
            colCount = 0;
            rowCount++;
        }
        postViewer.getView().setPrefSize(this.getMinWidth() / 2.25, 40);
        this.add(postViewer.getView(), colCount++, rowCount);
    }

    public void filterPostsNameContaining(String letters) {
        init(post -> post.getName().toLowerCase().contains(letters.toLowerCase()));
    }

    public void filterPostsByUnionPredicates(Predicate<Post> predicate) {
        init(predicate);
    }

}