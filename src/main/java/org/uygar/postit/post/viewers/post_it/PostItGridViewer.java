package org.uygar.postit.post.viewers.post_it;

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
import org.uygar.postit.post.viewers.post_it.post_it_viewer.PostItViewer;

import java.util.List;
import java.util.stream.Collectors;

public class PostItGridViewer extends FlowPane {

    private final PostItContainerOrganizer postItOrganizer;

    public PostItGridViewer(Post post, DataMiner miner) {
        this.postItOrganizer = new PostItContainerOrganizer(post, miner);
        this.setHgap(10);
        this.setVgap(10);
        this.setId("post_it_grid");
        init();
    }

    public void init() {
        setSortedAndMappedPostItsAsChildren();
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
        postItViewers.forEach(postItViewer -> postItViewer.getMainGraphic().setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY)
                PostController.openPostItController(postItViewer.getPostIt());
            else if (mouseEvent.getButton() == MouseButton.SECONDARY)
                postItViewer.changePostItAspectBasedOnStateAndSaveToDatabase(postItOrganizer.getDataMiner());
        }));
        return postItViewers;
    }

    public PostItContainerOrganizer getPostItOrganizer() {
        return postItOrganizer;
    }

}