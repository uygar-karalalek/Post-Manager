package org.uygar.postit.post.viewers.post_it.post_it_viewer;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.data.database.queries.DMLQueryBuilder;
import org.uygar.postit.data.database.queries.Query;
import org.uygar.postit.post.PostIt;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PostItViewer extends VBox {

    private final PostIt postIt;

    private StackPane scadenzaTextWrapper;

    private final PostItViewerBasicGraphicsBuilder graphicBuilder;

    public PostItViewer(PostIt postIt) {
        this.postIt = postIt;
        this.setId("post_it");
        this.graphicBuilder = new
                PostItViewerBasicGraphicsBuilder(postIt);
        buildPostItViewer();
        if (postIt.isFatto())
            addToPostItDoneImage();
    }

    private void buildPostItViewer() {
        buildScadenzaLabel();
        addMouseListeners();
        this.getChildren().add(graphicBuilder.getPostItImageWrapper());
    }

    private void buildScadenzaLabel() {
        LocalDateTime fine = this.getPostIt().getDataFine();
        String wellFormattedDate = fine.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String wellFormattedHour = fine.format(DateTimeFormatter.ofPattern("hh:mm"));

        String scadenza = "Scadenza: " + wellFormattedDate +
                " alle " + wellFormattedHour;

        Text text = new Text(scadenza);
        text.setId("scadenzaText");
        this.scadenzaTextWrapper = new StackPane(text);
        this.scadenzaTextWrapper.setId("scadenzaTextWrapper");
    }

    public PostIt getPostIt() {
        return postIt;
    }

    private void addMouseListeners() {
        this.setOnMouseEntered(this::onPostItHover);
        this.setOnMouseExited(this::onPostItExit);
    }

    private void onPostItHover(MouseEvent event) {
        this.getChildren().add(0, scadenzaTextWrapper);
    }

    private void onPostItExit(MouseEvent event) {
        this.getChildren().remove(0);
    }

    public StackPane getMainGraphic() {
        return this.graphicBuilder.getPostItImageWrapper();
    }

    public void onDoneUndoneClicked(DataMiner miner) {
        this.postIt.setFatto(!this.postIt.isFatto());
        if (this.postIt.isFatto())
            addToPostItDoneImage();
        else
            removeFromPostItDoneImage();
        executeDoneStateWithQuery(miner);
    }

    private void addToPostItDoneImage() {
        graphicBuilder.getPostItImageWrapper()
                .getChildren().add(new ImageView("org/uygar/images/fatto.png"));
    }

    private void removeFromPostItDoneImage() {
        graphicBuilder.getPostItImageWrapper()
                .getChildren()
                .removeIf(node -> {
                    if (node instanceof ImageView) {
                        return ((ImageView) node).getImage().getUrl().endsWith("fatto.png");
                    }
                    return false;
                });
    }

    private void executeDoneStateWithQuery(DataMiner miner) {
        Query query = new DMLQueryBuilder()
                .update("postit")
                .set("done", Boolean.toString(postIt.isFatto()))
                .where("id="+postIt.getId());
        miner.tryExecute(query);
    }

}