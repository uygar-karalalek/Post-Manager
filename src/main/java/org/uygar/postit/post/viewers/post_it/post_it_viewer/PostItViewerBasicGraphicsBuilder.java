package org.uygar.postit.post.viewers.post_it.post_it_viewer;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.uygar.postit.post.PostIt;
import org.uygar.postit.post.viewers.post_it.PostViewerImageBuilder;

public class PostItViewerBasicGraphicsBuilder {

    private final PostIt postIt;

    private static final double POST_IT_SIZE = 260;
    private static final double POST_IT_BORDER = 60;
    private static final Insets TASK_TEXT_INSETS = new Insets(0, 10, 0, 20);

    private ImageView postItImage;
    private StackPane postItImageWrapper;
    private ScrollPane taskTextWrapper;
    private Label titleLbl, taskText;

    public PostItViewerBasicGraphicsBuilder(PostIt postIt) {
        this.postIt = postIt;
        buildImageAndPostTextWrapper();
    }

    private void buildImageAndPostTextWrapper() {
        buildTexts();
        buildImageViewByPostItColor();

        VBox textWrapper = new VBox(titleLbl, taskTextWrapper);
        VBox.setMargin(taskTextWrapper, TASK_TEXT_INSETS);
        textWrapper.setSpacing(postItImage.getFitWidth() / 4);
        textWrapper.setId("textWrapper");
        postItImageWrapper = new StackPane(postItImage, textWrapper);
    }

    private void buildTexts() {
        titleLbl = new Label();
        taskText = new Label();

        titleLbl.textProperty().bind(postIt.titoloProperty());
        titleLbl.setId("postItTitle");
        titleLbl.setTextFill(postIt.getColore().postItTextColor);
        titleLbl.autosize();

        taskText.setWrapText(true);
        taskText.setId("postItText");
        taskText.textProperty().bind(postIt.testoProperty());
        taskText.setTextFill(postIt.getColore().postItTextColor);
        taskText.setPrefWidth(POST_IT_SIZE - POST_IT_BORDER);
        initTextScrollWrapper();
    }

    private void initTextScrollWrapper() {
        taskTextWrapper = new ScrollPane(taskText);
        taskTextWrapper.setId("taskTextWrapper");
        taskTextWrapper.setPrefHeight(POST_IT_SIZE / 3);
        taskTextWrapper.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
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