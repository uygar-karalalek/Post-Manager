package org.uygar.postit.post.viewers.post_it;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.uygar.postit.controllers.post.PostController;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.data.structures.PostItContainerOrganizer;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.PostIt;
import org.uygar.postit.post.viewers.post_it.post_it_viewer.PostItViewer;
import org.uygar.postit.post.viewers.post_it.post_it_viewer.builder.graphic_builder.PostItViewerBasicGraphicsBuilder;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PostItGridViewer extends FlowPane {

    private final PostItContainerOrganizer postItOrganizer;

    public PostItGridViewer(Post post, DataMiner miner) {
        this.postItOrganizer = new PostItContainerOrganizer(post, miner);
        this.setHgap(30);
        this.setVgap(20);
        this.setId("post_it_grid");
        init();
    }

    public void init() {
        sortPostIts();
    }

    public void sortPostIts() {
        setSortedAndMappedPostItsAsChildren();
    }

    private void setSortedAndMappedPostItsAsChildren() {
        getChildren().setAll(getSortedAndMappedPostIts());
    }

    private List<PostItViewer> getSortedAndMappedPostIts() {
        return postItOrganizer.getSorted()
                .stream().map(PostItViewer::new)
                .collect(Collectors.collectingAndThen(Collectors.toList(),
                        this::addListenersToPostItViewersAndThenReturn));
    }

    private List<PostItViewer> addListenersToPostItViewersAndThenReturn(List<PostItViewer> postItViewers) {
        postItViewers.forEach(this::addListenertoPostItViewer);
        return postItViewers;
    }

    public void addListenertoPostItViewer(PostItViewer postItViewer) {
        postItViewer.getMainGraphic().setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY)
                PostController.openPostItController(postItViewer.getPostIt(), this);
            else if (mouseEvent.getButton() == MouseButton.SECONDARY)
                postItViewer.changePostItAspectBasedOnStateAndSaveToDatabase(postItOrganizer.getDataMiner());
        });
    }

    public void add(PostIt postIt) {
        PostItViewer postItViewer = new PostItViewer(postIt);
        this.postItOrganizer.add(postIt);
        this.addListenertoPostItViewer(postItViewer);
        this.getChildren().add(postItViewer);
    }

    public void remove(PostIt postIt) {
        this.postItOrganizer.remove(postIt);
        this.getChildren().removeIf(node -> {
            if (node instanceof PostItViewer)
                return (((PostItViewer) node).getPostIt().equals(postIt));
            return false;
        });
        sortPostIts();
    }

    public void filter(Predicate<PostIt> postItPredicate) {
        sortPostIts();
        Predicate<Node> doNotPassesTest = node -> !postItPredicate.test(((PostItViewer) node).getPostIt());
        this.getChildren()
                .removeIf(doNotPassesTest);
    }

    public PostItContainerOrganizer getPostItOrganizer() {
        return postItOrganizer;
    }

}