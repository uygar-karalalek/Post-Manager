package org.uygar.postit.controllers.application.import_controller.import_controller_utils.views.recovery_post_list_view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import org.uygar.postit.controllers.application.FXLoader;
import org.uygar.postit.data.recoveries.post.recovery_folder.reader.RecoveryPostReader;
import org.uygar.postit.post.Post;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class RecoveryPostListItem extends ListCell<RecoveryPostReader> {

    @FXML
    public Label date;
    @FXML
    public Label postName;
    @FXML
    public Label numOfPostIts;

    public RecoveryPostListItem() {
        this.inflateFXML();
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
    protected void updateItem(RecoveryPostReader postReader, boolean empty) {
        super.updateItem(postReader, empty);
        if (empty || postReader == null) {
            setDisable(false);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        } else {
            postName.setText(postReader.getNewPost().getName());
            numOfPostIts.setText(Integer.toString(postReader.getNewPostIts().size()));
            date.setText(postReader.getNewPost().getCreationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }

}