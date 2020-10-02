package org.uygar.postit.post.viewers.post_it.post_it_viewer;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import org.uygar.postit.post.PostIt;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PostItViewer extends VBox {

    private final PostIt postIt;

    private StackPane scadenzaTextWrapper;

    private final PostItViewerImageAndTextGraphicBuilder graphicBuilder;

    public PostItViewer(PostIt postIt) {
        this.postIt = postIt;
        this.setId("post_it");
        this.graphicBuilder = new
                PostItViewerImageAndTextGraphicBuilder(postIt);
        buildPostItViewer();
    }

    private void buildPostItViewer() {
        buildScadenzaLabel();
        addMouseListeners();
        this.getChildren().add(graphicBuilder.getPostItImageWrapper());
    }

    private void buildScadenzaLabel() {
        LocalDateTime fine = this.getPostIt().getDataFine();
        String wellFormattedDate = fine.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String wellFormattedHour = fine.getHour() + ":" + fine.getMinute();

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

}