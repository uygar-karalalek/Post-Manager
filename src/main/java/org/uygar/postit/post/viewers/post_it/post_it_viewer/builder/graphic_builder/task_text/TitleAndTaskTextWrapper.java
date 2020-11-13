package org.uygar.postit.post.viewers.post_it.post_it_viewer.builder.graphic_builder.task_text;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.uygar.postit.post.PostIt;

import static org.uygar.postit.post.viewers.post_it.post_it_viewer.PostItViewer.*;

public class TitleAndTaskTextWrapper extends VBox {

    private final PostIt postIt;

    private static final Insets TASK_TEXT_INSETS = new Insets(0, 10, 0, 20);

    public TitleAndTaskTextWrapper(PostIt postIt) {
        this.postIt = postIt;
        buildTexts();
    }

    private void buildTexts() {
        Label title = new Label();
        Label taskText = new Label();

        title.textProperty().bind(postIt.titoloProperty());
        title.setId("postItTitle");
        title.setTextFill(postIt.getColore().postItTextColor);
        title.autosize();

        taskText.setWrapText(true);
        taskText.setId("postItText");
        taskText.textProperty().bind(postIt.testoProperty());
        taskText.setTextFill(postIt.getColore().postItTextColor);
        taskText.setPrefWidth(POST_IT_SIZE - POST_IT_TRANSPARENT_BORDER - POST_IT_LABEL_MARGIN);

        buildTitleAndTaskTextWrapper(title, taskText);
    }

    private void buildTitleAndTaskTextWrapper(Label title, Label task) {
        ScrollPane taskTextWrapper = new ScrollPane(task);
        taskTextWrapper.setId("taskTextWrapper");
        taskTextWrapper.setPrefHeight(POST_IT_SIZE / 3);
        taskTextWrapper.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        this.getChildren().addAll(title, taskTextWrapper);
        this.setSpacing(POST_IT_SIZE / 4);
        this.setId("textWrapper");
        VBox.setMargin(taskTextWrapper, TASK_TEXT_INSETS);
    }

}