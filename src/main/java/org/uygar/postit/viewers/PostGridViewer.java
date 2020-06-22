package org.uygar.postit.viewers;

import javafx.scene.layout.GridPane;
import org.uygar.postit.data.structures.PostContainerOrganizer;
import org.uygar.postit.post.Post;

public class PostGridViewer extends GridPane {

    private PostContainerOrganizer<PostViewer> postOrganizer;

    public PostGridViewer(PostContainerOrganizer<PostViewer> postOrganizer) {
        this.postOrganizer = postOrganizer;
    }
}