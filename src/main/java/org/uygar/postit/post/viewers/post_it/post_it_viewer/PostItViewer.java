package org.uygar.postit.post.viewers.post_it.post_it_viewer;

import javafx.scene.layout.*;
import org.uygar.postit.controllers.application.FXLoader;
import org.uygar.postit.controllers.post.postit.PostItController;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.data.database.queries.DMLQueryBuilder;
import org.uygar.postit.data.database.queries.Query;
import org.uygar.postit.post.PostIt;
import org.uygar.postit.post.viewers.post_it.post_it_viewer.builder.PostItMouseInteractionManager;
import org.uygar.postit.post.viewers.post_it.post_it_viewer.builder.graphic_builder.PostItViewerBasicGraphicsBuilder;
import org.uygar.postit.post.viewers.post_it.post_it_viewer.builder.graphic_builder.scadenza.ScadenzaWrapper;

public class PostItViewer extends VBox {

    private final PostIt postIt;

    private final ScadenzaWrapper scadenzaTextWrapper;
    private final PostItViewerBasicGraphicsBuilder graphicBuilder;
    private final PostItMouseInteractionManager interactionManager;

    public static final double POST_IT_SIZE = 300;
    public static final double POST_IT_LABEL_MARGIN = 30;
    public static final double POST_IT_TRANSPARENT_BORDER = 30;

    public PostItViewer(PostIt postIt) {
        this.postIt = postIt;
        this.setId("post_it");
        scadenzaTextWrapper = new ScadenzaWrapper(postIt);
        this.graphicBuilder = new PostItViewerBasicGraphicsBuilder(postIt);
        this.getChildren().add(graphicBuilder.getPostItImageWrapper());
        this.interactionManager = new PostItMouseInteractionManager(this);
        this.interactionManager.manage();
    }

    public void changePostItAspectBasedOnStateAndSaveToDatabase(DataMiner miner) {
        this.interactionManager.changePostItAspectBasedOnState();
        executeDoneStateWithQuery(miner);
    }

    private void executeDoneStateWithQuery(DataMiner miner) {
        Query query = new DMLQueryBuilder()
                .update("postit")
                .set("done", Boolean.toString(postIt.isFatto()))
                .where("id="+postIt.getId());
        miner.tryExecute(query);
    }

    public StackPane getMainGraphic() {
        return this.graphicBuilder.getPostItImageWrapper();
    }

    public ScadenzaWrapper getScadenzaTextWrapper() {
        return scadenzaTextWrapper;
    }

    public PostItViewerBasicGraphicsBuilder getGraphicBuilder() {
        return graphicBuilder;
    }

    public PostIt getPostIt() {
        return postIt;
    }

}