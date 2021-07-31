package org.uygar.postit.controllers.application.import_controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import org.uygar.postit.controllers.BaseController;
import org.uygar.postit.controllers.application.import_controller.import_controller_utils.views.PostListItem;
import org.uygar.postit.controllers.exception.WindowCoordinatesContainer;
import org.uygar.postit.controllers.exception.WrongFieldsException;
import org.uygar.postit.data.properties.PostProperties;
import org.uygar.postit.data.recoveries.post.recovery_db.Recovery;
import org.uygar.postit.data.recoveries.post.recovery_folder.RecoveryFolder;
import org.uygar.postit.data.recoveries.post.recovery_folder.reader.RecoveryReader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ImportController extends BaseController implements Initializable {

    @FXML
    public BorderPane import_root_pane;

    @FXML
    public TextField default_source_folder;

    @FXML
    public ListView<PostListItem> post_list;

    @FXML
    public TextField post_recovery_folder;


    public PostProperties postProperties = new PostProperties();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String lastFolder = postProperties.getStringProperty("lastFolder");
        default_source_folder.setText(lastFolder);
    }

    @FXML
    public void onSetDefaultFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File chosenDir = directoryChooser.showDialog(this.getStage());

        if (chosenDir != null) {
            default_source_folder.setText(chosenDir.getAbsolutePath());
            postProperties.putProperty("lastFolder", default_source_folder.getText());
            updateList();
        }
    }

    @FXML
    public void onPostRecoveryFolder() {

    }

    private void updateList() {
        File dir = new File(this.default_source_folder.getText());
        this.post_list.getItems().clear();

        if (RecoveryReader.exists(dir.getAbsolutePath())) {
            // ENTER IN THIS SECTION MEANS THAT THE USER CHOOSED
            // DIRECTLY THE POST RECOVERY FOLDER
            try (RecoveryReader reader = new RecoveryReader(dir.getAbsolutePath()) ){
                PostListItem postListItem = new PostListItem(reader);
                this.post_list.getItems().add(postListItem);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // TODO : CREATE SINGLE LIST POST
        } else {
            // ENTER IN THIS SECTION MEANS THAT ALL THE FOLDERS (UNDER THE FIRST LAYER!)
            // THAT CONTAIN POST RECOVERY FILES WILL BE IMPORTED

            for (File current : dir.listFiles()) {
                try (RecoveryReader currentReader = new RecoveryReader(current.getAbsolutePath())) {
                    PostListItem postListItem = new PostListItem(currentReader);
                    this.post_list.getItems().add(postListItem);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private File getDefaultFolder() {
        return new File(default_source_folder.getText());
    }

}