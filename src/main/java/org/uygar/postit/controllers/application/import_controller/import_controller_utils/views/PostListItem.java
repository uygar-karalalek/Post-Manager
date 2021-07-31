package org.uygar.postit.controllers.application.import_controller.import_controller_utils.views;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.uygar.postit.data.recoveries.post.recovery_folder.reader.RecoveryReader;
import org.uygar.postit.post.Post;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class PostListItem extends HBox {

    private final RecoveryReader reader;

    private Label nameLbl;
    private Label numOfPostItsLbl;
    private Label creationDateLbl;

    public PostListItem(RecoveryReader reader) {
        this.reader = reader;
        buildItem();
        addLabelsToItemBox();
    }

    private void buildItem() {
        this.nameLbl = new Label("Name: " + reader.getNewPost().getName() + " | ");
        String numOfPostIts = Integer.toString(reader.getNewPostIts().size() - 1);
        this.numOfPostItsLbl = new Label("Num of post its: " + numOfPostIts + " | ");
        this.creationDateLbl = new Label(reader.getNewPost()
                .getCreationDate().toLocalDate().format(DateTimeFormatter.ofPattern("dd - MMM - yyyy")));
    }

    private void addLabelsToItemBox() {
        this.getChildren().add(nameLbl);
        this.getChildren().add(numOfPostItsLbl);
        this.getChildren().add(creationDateLbl);
    }

}