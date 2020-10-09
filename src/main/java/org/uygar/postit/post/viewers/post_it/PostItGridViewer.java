package org.uygar.postit.post.viewers.post_it;

import javafx.scene.layout.FlowPane;
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
        getChildren().setAll(
                getSortedAndMappedPostIts());
    }

    private List<PostItViewer> getSortedAndMappedPostIts() {
        return postItOrganizer.getSorted()
                .stream().map(PostItViewer::new)
                .collect(Collectors.toList());
    }

}