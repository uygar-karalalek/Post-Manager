package org.uygar.postit.post.viewers.post_it.post_it_viewer.builder.graphic_builder;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.uygar.postit.post.PostIt;
import org.uygar.postit.post.viewers.post_it.post_it_viewer.builder.graphic_builder.task_text.TitleAndTaskTextWrapper;

import static org.uygar.postit.post.viewers.post_it.post_it_viewer.PostItViewer.*;

public class PostItViewerBasicGraphicsBuilder {

    private final PostIt postIt;

    private ImageView postItImage;
    private StackPane postItImageWrapper;

    public PostItViewerBasicGraphicsBuilder(PostIt postIt) {
        this.postIt = postIt;
        buildImageAndPostTextWrapper();
    }

    private void buildImageAndPostTextWrapper() {
        buildImageViewByPostItColor();
        postItImageWrapper = new StackPane(postItImage,
                new TitleAndTaskTextWrapper(postIt));
    }

    private void buildImageViewByPostItColor() {
        postItImage = new ImageView(PostViewerImageBuilder.build(postIt.getColore()));
        postItImage.setFitWidth(POST_IT_SIZE);
        postItImage.setFitHeight(POST_IT_SIZE);
    }

    public StackPane getPostItImageWrapper() {
        return postItImageWrapper;
    }

}