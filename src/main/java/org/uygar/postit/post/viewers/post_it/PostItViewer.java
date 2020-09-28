package org.uygar.postit.post.viewers.post_it;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.uygar.postit.post.PostIt;

import java.time.LocalDateTime;

public class PostItViewer extends VBox {

    private PostIt postIt;
    private StackPane scadenzaTextWrapper;
    private ImageView postItImage;
    public static final int SINGLE_POST_IT_SIZE = 250;

    public PostItViewer(PostIt postIt) {
        this.postIt = postIt;
        buildImageViewByPostItColor();
        buildScadenzaLabel();
        this.getChildren().add(postItImage);
        this.setId("post_it");
        setOnPostItHover();
    }

    private void buildScadenzaLabel() {
        LocalDateTime fine = postIt.getDataFine();

        String wellFormattedDate = fine.getDayOfMonth()+"/"+fine.getMonthValue()+"/"+fine.getYear();
        String wellFormattedHour = fine.getHour() + ":" + fine.getMinute();

        String scadenza = "Scadenza: " + wellFormattedDate +
                " alle " + wellFormattedHour;

        Text text = new Text(scadenza);
        text.setId("scadenzaText");
        this.scadenzaTextWrapper = new StackPane(text);
        this.scadenzaTextWrapper.setId("scadenzaTextWrapper");
    }

    private void buildImageViewByPostItColor() {
        postItImage = new ImageView(PostViewerImageBuilder.build(postIt.getColore()));
        postItImage.setFitWidth(SINGLE_POST_IT_SIZE);
        postItImage.setFitHeight(SINGLE_POST_IT_SIZE);
    }

    public PostIt getPostIt() {
        return postIt;
    }

    private void setOnPostItHover() {
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