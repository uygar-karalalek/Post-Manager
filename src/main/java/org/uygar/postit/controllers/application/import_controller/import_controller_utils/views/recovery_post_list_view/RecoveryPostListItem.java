package org.uygar.postit.controllers.application.import_controller.import_controller_utils.views.recovery_post_list_view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import org.uygar.postit.controllers.application.FXLoader;
import org.uygar.postit.data.recoveries.post.recovery_folder.reader.RecoveryReader;
import org.uygar.postit.post.Post;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class RecoveryPostListItem extends ListCell<Post> {

    @FXML
    public Label date;
    @FXML
    public Label postName;
    @FXML
    public Label numOfPostIts;

    public RecoveryPostListItem() {
        this.inflateFXML();
//        buildItem();
//        addLabelsToItemBox();
    }

    private void inflateFXML() {
        try {
            FXMLLoader fxml = FXLoader.getFXMLLoader(
                    "import_default_list_item", "app");
            fxml.setController(this);
            fxml.setRoot(this);
            fxml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateItem(Post post, boolean empty) {
        super.updateItem(post, empty);
        if (empty || post == null) {
            setDisable(false);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        } else {
            System.out.println(post.getName());
            postName.setText(post.getName());
            numOfPostIts.setText("0");
            date.setText("");

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }

//    private void buildItem() {
//        this.nameLbl = new Label("Name: " + reader.getNewPost().getName() + " | ");
//        String numOfPostIts = Integer.toString(reader.getNewPostIts().size() - 1);
//        this.numOfPostItsLbl = new Label("Num of post its: " + numOfPostIts + " | ");
//        this.creationDateLbl = new Label(reader.getNewPost()
//                .getCreationDate().toLocalDate().format(DateTimeFormatter.ofPattern("dd - MMM - yyyy")));
//    }
//
//    private void addLabelsToItemBox() {
//        this.getChildren().add(nameLbl);
//        this.getChildren().add(numOfPostItsLbl);
//        this.getChildren().add(creationDateLbl);
//    }

}