package org.uygar.postit.post.viewers.post_it;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import org.uygar.postit.controllers.post.PostController;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.data.structures.PostItContainerOrganizer;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.PostIt;
import org.uygar.postit.post.viewers.post_it.post_it_viewer.PostItViewer;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PostItGridViewer extends FlowPane {

    public static final int PADDING = 20;
    public static final int H_GAP = 30;
    public static final int V_GAP = 20;
    private final PostItContainerOrganizer postItOrganizer;

    public PostItGridViewer(Post post, DataMiner miner) {
        this.postItOrganizer = new PostItContainerOrganizer(post, miner);
        this.setHgap(H_GAP);
        this.setVgap(V_GAP);
        this.setPadding(new Insets(PADDING));
        this.setId("post_it_grid");
        init();
    }

    public void init() {
        sortAllPostIts();
    }

    public void sortVisiblePostIts() {
        List<PostIt> mappedPostIts = mappedPostItsFromChildren();
        getChildren().setAll(getSortedAndMappedPostIts(mappedPostIts));
    }

    private List<PostIt> mappedPostItsFromChildren() {
        return getChildren().stream().map(node -> ((PostItViewer) node).getPostIt()).collect(Collectors.toList());
    }

    public void sortAllPostIts() {
        setAllSortedAndMappedPostItsAsChildren();
    }

    private void setAllSortedAndMappedPostItsAsChildren() {
        getChildren().setAll(getSortedAndMappedPostIts(postItOrganizer.getSorted()));
    }

    private List<PostItViewer> getSortedAndMappedPostIts(List<PostIt> postIts) {
        this.postItOrganizer.getSortType().sort(postIts);
        return postIts.stream().map(PostItViewer::new)
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
                PostController.openPostItController(postItViewer, this);
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
        sortAllPostIts();
    }

    public void filter(Predicate<PostIt> postItPredicate) {
        sortAllPostIts();
        Predicate<Node> doNotPassesTest = node -> !postItPredicate.test(((PostItViewer) node).getPostIt());
        this.getChildren()
                .removeIf(doNotPassesTest);
    }

    public PostItContainerOrganizer getPostItOrganizer() {
        return postItOrganizer;
    }

}