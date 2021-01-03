package org.uygar.postit.post.viewers.post_it.post_it_viewer.builder.graphic_builder;

import javafx.beans.value.ObservableValue;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.uygar.postit.post.PostIt;
import org.uygar.postit.post.properties.Colore;
import org.uygar.postit.post.viewers.post_it.post_it_viewer.builder.graphic_builder.task_text.BasicPostItTextWrapper;

import static org.uygar.postit.post.viewers.post_it.post_it_viewer.PostItViewer.*;

public class PostItViewerBasicGraphicsBuilder {

    private final PostIt postIt;

    private Rectangle postItRectangle;
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
        postItImageWrapper = new StackPane(postItRectangle, basicPostItTextWrapper);
    }

    private void buildImageViewByPostItColor() {
        postItRectangle = new Rectangle(POST_IT_SIZE, POST_IT_SIZE, postIt.getColore().postItColor);
        postItRectangle.setArcHeight(POST_IT_HEIGHT_RADIUS);
        postItRectangle.setArcWidth(POST_IT_WIDTH_RADIUS);
        postItRectangle.setStrokeWidth(2);
        postItRectangle.setStroke(Color.GRAY);
    }

    public StackPane getPostItImageWrapper() {
        return postItImageWrapper;
    }

    public void changeBasicGraphicsOnPostItColorChange(ObservableValue<? extends Colore> obs, Colore oldVal, Colore newVal) {
        postItRectangle.setFill(newVal.postItColor);
        basicPostItTextWrapper.getTitleLabel().setTextFill(newVal.postItTextColor);
        basicPostItTextWrapper.getTaskTextLabel().setTextFill(newVal.postItTextColor);
        basicPostItTextWrapper.getPriorityLabel().setTextFill(newVal.postItTextColor);
    }

    public Rectangle getPostItRectangle() {
        return postItRectangle;
    }

}