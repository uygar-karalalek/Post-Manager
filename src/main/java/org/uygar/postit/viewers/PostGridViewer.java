package org.uygar.postit.viewers;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import org.uygar.postit.data.structures.PostContainerOrganizer;
import org.uygar.postit.post.Post;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PostGridViewer extends GridPane {

    public PostContainerOrganizer postOrganizer;
    final static double HEIGHT = 300, WIDTH = 500;
    static final int DEF_COLS = 2;

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
                view.setPrefSize(this.getMinWidth() / 2.25, 40);
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
        System.out.println(colCount + " - " + rowCount);
        postViewer.getView().setPrefSize(this.getMinWidth() / 2.25, 40);
        this.add(postViewer.getView(), colCount++, rowCount);
    }

    public void filter(String letters) {
        init(post -> post.getName().toLowerCase().contains(letters.toLowerCase()));
    }

    public void filter(Predicate<Post> predicate) {
        init(predicate);
    }

}