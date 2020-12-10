package org.uygar.postit.post.viewers.post_it.post_it_viewer.builder.graphic_builder.scadenza;

import javafx.beans.binding.StringExpression;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import org.uygar.postit.post.PostIt;

import java.text.Format;
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
        text = new Text();
        text.textProperty().bindBidirectional(postIt.dataScadenzaProperty(), getConverter());
        text.setId("scadenzaText");
        this.getChildren().add(text);
        this.setId("scadenzaTextWrapper");
        this.setMaxWidth(POST_IT_SIZE - POST_IT_TRANSPARENT_BORDER);
    }

    private StringConverter<LocalDateTime> getConverter() {
        return new StringConverter<>() {
            @Override
            public String toString(LocalDateTime fine) {
                String wellFormattedDate = fine.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String wellFormattedHour = fine.format(DateTimeFormatter.ofPattern("HH:mm"));
                return "Scadenza: " + wellFormattedDate + " alle " + wellFormattedHour;
            }

            @Override
            public LocalDateTime fromString(String stringDate) {
                return LocalDateTime.parse(stringDate);
            }
        };
    }


    public Text getText() {
        return text;
    }

}