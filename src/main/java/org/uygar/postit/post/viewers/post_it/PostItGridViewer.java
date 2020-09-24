package org.uygar.postit.post.viewers.post_it;

import javafx.scene.layout.FlowPane;
import org.jetbrains.annotations.NotNull;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.data.structures.PostItContainerOrganizer;
import org.uygar.postit.post.Post;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PostItGridViewer extends FlowPane {

    private PostItContainerOrganizer postItContainer;

    public PostItGridViewer(Post post, DataMiner miner) {
        postItContainer = new PostItContainerOrganizer(post, miner);
        this.setId("post_it_grid");
        init();
    }

    public void init() {
        List<PostItViewer> postItViewers = getSortetPostItViewers();
        this.getChildren().addAll(postItViewers);
    }

    public void sortPostIts() {
        this.getChildren().clear();
        this.getChildren().addAll(getSortetPostItViewers());
    }

    @NotNull
    private List<PostItViewer> getSortetPostItViewers() {
        return postItContainer.getSortedList().stream()
                .filter(Objects::nonNull)
                .map(PostItViewer::new).collect(Collectors.toList());
    }

}