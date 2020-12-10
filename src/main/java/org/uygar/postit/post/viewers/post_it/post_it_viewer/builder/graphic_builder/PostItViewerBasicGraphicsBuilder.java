package org.uygar.postit.post.viewers.post_it.post_it_viewer.builder.graphic_builder;

import javafx.beans.value.ObservableValue;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.uygar.postit.post.PostIt;
import org.uygar.postit.post.properties.Colore;
import org.uygar.postit.post.viewers.post_it.post_it_viewer.builder.graphic_builder.task_text.BasicPostItTextWrapper;

import static org.uygar.postit.post.viewers.post_it.post_it_viewer.PostItViewer.*;

public class PostItViewerBasicGraphicsBuilder {

    private final PostIt postIt;

    private ImageView postItImage;
    private StackPane postItImageWrapper;
    private final BasicPostItTextWrapper basicPostItTextWrapper;

    public PostItViewerBasicGraphicsBuilder(PostIt postIt) {
        this.postIt = postIt;
        this.basicPostItTextWrapper = new BasicPostItTextWrapper(postIt);
        buildImageAndPostTextWrapper();
        this.postIt.coloreProperty().addListener(this::changeBasicGraphicsOnPostItColorChange);
    }

    private void buildImageAndPostTextWrapper() {
        buildImageViewByPostItColor();
        postItImageWrapper = new StackPane(postItImage, basicPostItTextWrapper);
    }

    private void buildImageViewByPostItColor() {
        postItImage = new ImageView(PostViewerImageBuilder.build(postIt.getColore()));
        postItImage.setFitWidth(POST_IT_SIZE);
        postItImage.setFitHeight(POST_IT_SIZE);
    }

    public StackPane getPostItImageWrapper() {
        return postItImageWrapper;
    }

    public void changeBasicGraphicsOnPostItColorChange(ObservableValue<? extends Colore> obs, Colore oldVal, Colore newVal) {
        postItImage.setImage(PostViewerImageBuilder.build(newVal));
        basicPostItTextWrapper.getTitleLabel().setTextFill(newVal.postItTextColor);
        basicPostItTextWrapper.getTaskTextLabel().setTextFill(newVal.postItTextColor);
        basicPostItTextWrapper.getPriorityLabel().setTextFill(newVal.postItTextColor);
    }

}