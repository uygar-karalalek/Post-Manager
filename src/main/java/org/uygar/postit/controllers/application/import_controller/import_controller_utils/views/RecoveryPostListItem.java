package org.uygar.postit.controllers.application.import_controller.import_controller_utils.views;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import org.uygar.postit.data.recoveries.post.recovery_folder.reader.RecoveryReader;
import org.uygar.postit.post.Post;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class RecoveryPostListItem extends ListCell<Post> {

    private final RecoveryReader reader;

    private Label nameLbl;
    private Label numOfPostItsLbl;
    private Label creationDateLbl;

    public RecoveryPostListItem(RecoveryReader reader) {

        this.reader = reader;
        buildItem();
        addLabelsToItemBox();
    }

    @Override
    protected void updateItem(Post post, boolean empty) {
        super.updateItem(post, empty);

        if (empty || post == null) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        } else {

        }
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