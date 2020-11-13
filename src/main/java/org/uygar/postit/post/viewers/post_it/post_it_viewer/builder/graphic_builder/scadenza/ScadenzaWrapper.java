package org.uygar.postit.post.viewers.post_it.post_it_viewer.builder.graphic_builder.scadenza;

import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.uygar.postit.post.PostIt;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.uygar.postit.post.viewers.post_it.post_it_viewer.PostItViewer.*;

public class ScadenzaWrapper extends StackPane {

    private Text text;
    private final PostIt postIt;

    public ScadenzaWrapper(PostIt postIt) {
        this.postIt = postIt;
        initialize();
    }

    private void initialize() {
        LocalDateTime fine = postIt.getDataScadenza();
        String wellFormattedDate = fine.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String wellFormattedHour = fine.format(DateTimeFormatter.ofPattern("hh:mm"));

        String scadenza = "Scadenza: " + wellFormattedDate +
                " alle " + wellFormattedHour;

        text = new Text(scadenza);
        text.setId("scadenzaText");
        this.getChildren().add(text);
        this.setId("scadenzaTextWrapper");
        this.setMaxWidth(POST_IT_SIZE - POST_IT_TRANSPARENT_BORDER);
    }

    public Text getText() {
        return text;
    }

}