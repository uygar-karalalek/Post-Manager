package org.uygar.postit.post.viewers.post_it.post_it_viewer.builder.graphic_builder.task_text;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.uygar.postit.post.PostIt;
import org.uygar.postit.post.properties.Colore;

import static org.uygar.postit.post.viewers.post_it.post_it_viewer.PostItViewer.*;

public class BasicPostItTextWrapper extends VBox {

    private final PostIt postIt;
    private Label title, taskText, priority;
    public static final double CONTENT_SPACING = POST_IT_SIZE / 4;

    private static final Insets TASK_TEXT_INSETS = new Insets(0, 10, 0, 20);

    public BasicPostItTextWrapper(PostIt postIt) {
        this.postIt = postIt;
        buildTexts();
    }

    private void buildTexts() {
        initTitleText();
        initTaskText();
        initPriorityText();
        buildTitleTaskAndPriorityTextWrapper(title, taskText, priority);
    }

    private void initTitleText() {
        title = new Label();
        title.textProperty().bind(postIt.titoloProperty());
        title.setId("postItTitle");
        title.setTextFill(postIt.getColore().postItTextColor);
        title.autosize();
    }

    private void initTaskText() {
        taskText = new Label();
        taskText.setWrapText(true);
        taskText.setId("postItText");
        taskText.textProperty().bind(postIt.testoProperty());
        taskText.setTextFill(postIt.getColore().postItTextColor);
        taskText.setPrefWidth(POST_IT_SIZE - POST_IT_TRANSPARENT_BORDER - POST_IT_LABEL_MARGIN);
    }

    private void initPriorityText() {
        priority = new Label();
        priority.setId("priorityText");
        priority.setTextFill(postIt.getColore().postItTextColor);
        priority.setTranslateY(priority.getTranslateY() - CONTENT_SPACING);
        priority.textProperty().bind(new SimpleStringProperty("Priorità: ")
                .concat(postIt.priorityProperty().asString()));
    }

    private void buildTitleTaskAndPriorityTextWrapper(Label title, Label task, Label priority) {
        ScrollPane taskTextWrapper = new ScrollPane(task);
        taskTextWrapper.setId("taskTextWrapper");
        taskTextWrapper.setPrefHeight(POST_IT_SIZE / 3);
        taskTextWrapper.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        this.getChildren().addAll(title, taskTextWrapper, priority);
        this.setSpacing(CONTENT_SPACING);
        this.setId("textWrapper");
        this.setTranslateY(20);
        VBox.setMargin(taskTextWrapper, TASK_TEXT_INSETS);
    }

    public Label getTitleLabel() {
        return title;
    }
    public Label getTaskTextLabel() {
        return taskText;
    }
    public Label getPriorityLabel() {
        return priority;
    }

}