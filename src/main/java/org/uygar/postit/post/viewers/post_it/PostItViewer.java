package org.uygar.postit.post.viewers.post_it;

import javafx.scene.image.ImageView;
import org.uygar.postit.post.PostIt;

public class PostItViewer extends ImageView {

    private PostIt postIt;
    public static final int SINGLE_POST_IT_SIZE = 250;

    public PostItViewer(PostIt postIt) {
        this.postIt = postIt;
        buildImageViewByPostItColor();
    }

    private void buildImageViewByPostItColor() {
        this.setImage(PostViewerImageBuilder.build(postIt.getColore()));
        this.setFitWidth(SINGLE_POST_IT_SIZE);
        this.setFitHeight(SINGLE_POST_IT_SIZE);
    }

    public PostIt getPostIt() {
        return postIt;
    }

}