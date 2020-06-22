package org.uygar.postit.viewers;

import javafx.scene.layout.GridPane;
import org.uygar.postit.data.structures.PostContainerOrganizer;
import org.uygar.postit.post.Post;

public class PostGridViewer extends GridPane {

    private PostContainerOrganizer postOrganizer;

    public PostGridViewer(PostContainerOrganizer postOrganizer) {
        this.postOrganizer = postOrganizer;
        init();
    }

    private void init() {
        int num = 0;
        for (Post element : postOrganizer)
            this.add(new PostViewer(element).getView(), num / 3, num++ % 3);
    }

    public void updateLast() {
        Post post = postOrganizer.getPostList().get(postOrganizer.getPostList().size() - 1);
        PostViewer postViewer = new PostViewer(post);
        int row = this.getRowCount(), col = this.getColumnCount();
        if (col == 4) {
            col = 0;
            row ++;
        }
        this.add(postViewer.getView(), col, row);
    }

}