package org.uygar.postit.controllers.application.import_controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import org.uygar.postit.controllers.BaseController;
import org.uygar.postit.controllers.application.import_controller.import_controller_utils.views.recovery_post_list_view.RecoveryListCellFactory;
import org.uygar.postit.controllers.application.import_controller.import_controller_utils.views.recovery_post_list_view.RecoveryPostListItem;
import org.uygar.postit.data.properties.PostProperties;
import org.uygar.postit.data.recoveries.post.recovery_folder.reader.RecoveryReader;
import org.uygar.postit.post.Post;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ImportController extends BaseController implements Initializable {

    @FXML
    public BorderPane import_root_pane;

    // TODO : ON PATH CHANGE, CHANGE LIST ITEMS
    // TODO : ON PATH CHANGE, CHANGE PROPERTIES FILE

    @FXML
    public TextField default_source_folder;

    // TODO : CUSTOMIZE LIST CELLS WITH CUSTOM FXML VIEW -> https://stackoverflow.com/questions/47511132/javafx-custom-listview

    @FXML
    public ListView<Post> post_list;

    @FXML
    public TextField post_recovery_folder;

    public PostProperties postProperties = new PostProperties();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String lastFolder = postProperties.getStringProperty("lastFolder");
        default_source_folder.setText(lastFolder);
        post_list.setCellFactory(new RecoveryListCellFactory());
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
        File dir = getDefaultFolder();
        this.post_list.getItems().clear();

        if (RecoveryReader.existsPostRecoveryFile(dir.getAbsolutePath())) {
            // ENTER IN THIS SECTION MEANS THAT THE USER CHOOSED
            // DIRECTLY THE POST RECOVERY FOLDER
            tryAddPostListItem(dir);
        } else {
            // ENTER IN THIS SECTION MEANS THAT ALL THE FOLDERS (UNDER THE FIRST LAYER!)
            // THAT CONTAIN POST RECOVERY FILES WILL BE IMPORTED
            Arrays.stream(dir.listFiles()).forEach(this::tryAddPostListItem);
        }
    }

    private void tryAddPostListItem(File file) {
        try (RecoveryReader reader = new RecoveryReader(file.getAbsolutePath())) {
            this.post_list.getItems().add(reader.getNewPost());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getDefaultFolder() {
        return new File(default_source_folder.getText());
    }

}