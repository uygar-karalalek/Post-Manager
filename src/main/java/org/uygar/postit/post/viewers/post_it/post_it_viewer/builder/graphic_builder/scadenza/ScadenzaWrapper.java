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

public class ScadenzaWrapper extends Text {

    private final PostIt postIt;

    public ScadenzaWrapper(PostIt postIt) {
        this.postIt = postIt;
        this.setDisable(true); // So it does not interfer with mouse click
        initialize();
    }

    private void initialize() {
        this.textProperty().bindBidirectional(postIt.dataScadenzaProperty(), getConverter());
        this.setId("scadenzaText");
        this.setUnderline(true);
        this.setTranslateY(-(POST_IT_SIZE/1.45));
    }

    private StringConverter<LocalDateTime> getConverter() {
        return new StringConverter<>() {
            @Override
            public String toString(LocalDateTime fine) {
                String wellFormattedDate = fine.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String wellFormattedHour = fine.format(DateTimeFormatter.ofPattern("HH:mm"));
                return "Entro: "+wellFormattedDate + " ore " + wellFormattedHour;
            }

            @Override
            public LocalDateTime fromString(String stringDate) {
                return LocalDateTime.parse(stringDate);
            }
        };
    }

}