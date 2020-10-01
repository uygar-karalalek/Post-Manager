package org.uygar.postit.post.viewers.post_it;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.uygar.postit.post.PostIt;

import java.time.LocalDateTime;

public class PostItViewer extends VBox {

    private PostIt postIt;
    private ImageView postItImage;
    private Text taskText = new Text();
    private Label titleLbl = new Label();
    private StackPane scadenzaTextWrapper;
    public static final int SINGLE_POST_IT_SIZE = 260;

    public PostItViewer(PostIt postIt) {
        this.postIt = postIt;
        this.setId("post_it");
        buildPostItViewer();
    }

    private void buildPostItViewer() {
        buildScadenzaLabel();
        addMouseListeners();
        buildImageAndPostTextWrapper();
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

    private void buildImageAndPostTextWrapper() {
        buildTexts();
        buildImageViewByPostItColor();

        VBox textWrapper = new VBox(titleLbl, taskText);
        textWrapper.setSpacing(postItImage.getFitWidth()/3);
        textWrapper.setId("textWrapper");
        StackPane imageAndTextsWrapper = new StackPane(postItImage, textWrapper);
        this.getChildren().add(imageAndTextsWrapper);
    }

    private void buildTexts() {
        titleLbl.textProperty().bind(postIt.titoloProperty());
        titleLbl.setId("postItTitle");
        titleLbl.setTextFill(postIt.getColore().postItTextColor);
        titleLbl.autosize();

        taskText.textProperty().bind(postIt.testoProperty());
        taskText.setId("postItText");
        taskText.setFill(postIt.getColore().postItTextColor);
        taskText.setWrappingWidth(this.getWidth());
    }

    private void buildImageViewByPostItColor() {
        postItImage = new ImageView(PostViewerImageBuilder.build(postIt.getColore()));
        postItImage.setFitWidth(SINGLE_POST_IT_SIZE);
        postItImage.setFitHeight(SINGLE_POST_IT_SIZE);
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