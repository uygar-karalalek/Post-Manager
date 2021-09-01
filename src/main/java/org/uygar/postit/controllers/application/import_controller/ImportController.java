package org.uygar.postit.controllers.application.import_controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.uygar.postit.controllers.BaseController;
import org.uygar.postit.controllers.application.import_controller.import_controller_utils.manager.ImportControllerManager;
import org.uygar.postit.data.properties.PostManagerProperties;
import org.uygar.postit.data.recoveries.post.recovery_folder.reader.RecoveryPostReader;

import java.net.URL;
import java.util.ResourceBundle;

public class ImportController extends BaseController {

    @FXML
    public BorderPane import_root_pane;

    @FXML
    public TextField default_source_folder;

    @FXML
    public ListView<RecoveryPostReader> post_list;

    @FXML
    public TextField post_recovery_folder;

    @FXML
    public HBox choosen_folder;

    public ImportControllerManager importControllerManager = new ImportControllerManager(this);
    public PostManagerProperties applicationProperties;

    public void initialize(PostManagerProperties postManagerProperties) {
        applicationProperties = postManagerProperties;
        ImportControllerManager.ImportInitializer importInitializer = importControllerManager.new ImportInitializer();
        importInitializer.initialize();
    }

    @FXML
    public void onSetDefaultFolder() {
        importControllerManager.chooseAndSaveDefaultFolder();
    }

    @FXML
    public void onPostRecoveryFolder() {
        // TODO: Da implementare!
    }

}