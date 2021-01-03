package org.uygar.postit.post.viewers.post_it.post_it_viewer;

import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.data.query_utils.QueryUtils;
import org.uygar.postit.post.PostIt;
import org.uygar.postit.post.viewers.post_it.post_it_viewer.builder.PostItMouseInteractionManager;
import org.uygar.postit.post.viewers.post_it.post_it_viewer.builder.graphic_builder.PostItViewerBasicGraphicsBuilder;
import org.uygar.postit.post.viewers.post_it.post_it_viewer.builder.graphic_builder.scadenza.ScadenzaWrapper;

public class PostItViewer extends StackPane {

    private final PostIt postIt;

    private final ScadenzaWrapper scadenzaText;
    private final PostItViewerBasicGraphicsBuilder graphicBuilder;
    private final PostItMouseInteractionManager interactionManager;

    public static final double POST_IT_SIZE = 280;
    public static final double POST_IT_LABEL_MARGIN = 30;
    public static final double POST_IT_TRANSPARENT_BORDER = 30;
    public static final double POST_IT_HEIGHT_RADIUS = 30;
    public static final double POST_IT_WIDTH_RADIUS = 30;

    public PostItViewer(PostIt postIt) {
        this.postIt = postIt;
        this.setId("post_it");
        this.scadenzaText = new ScadenzaWrapper(postIt);
        this.graphicBuilder = new PostItViewerBasicGraphicsBuilder(postIt);
        this.getChildren().addAll(graphicBuilder.getPostItImageWrapper(), scadenzaText);
        this.interactionManager = new PostItMouseInteractionManager(this);
        this.interactionManager.manage();
    }

    public void changePostItAspectBasedOnStateAndSaveToDatabase(DataMiner miner) {
        this.interactionManager.changePostItAspectBasedOnState();
        QueryUtils.setDoneStateOfPostItInDatabase(miner, postIt);
    }

    public ScadenzaWrapper getScadenzaText() {
        return scadenzaText;
    }

    public Rectangle getPostItRectangle() {
        return graphicBuilder.getPostItRectangle();
    }

    public StackPane getMainGraphic() {
        return this.graphicBuilder.getPostItImageWrapper();
    }

    public PostItViewerBasicGraphicsBuilder getGraphicBuilder() {
        return graphicBuilder;
    }

    public PostIt getPostIt() {
        return postIt;
    }

}