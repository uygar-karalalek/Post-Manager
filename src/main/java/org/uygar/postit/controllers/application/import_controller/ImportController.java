package org.uygar.postit.controllers.application.import_controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.uygar.postit.controllers.BaseController;
import org.uygar.postit.controllers.application.import_controller.import_controller_utils.manager.ImportControllerManager;
import org.uygar.postit.controllers.application.import_controller.import_controller_utils.views.recovery_post_list_view.RecoveryPostListSpecificItemController;
import org.uygar.postit.data.properties.PostManagerProperties;
import org.uygar.postit.data.recoveries.post.recovery_folder.reader.RecoveryPostReader;
import org.uygar.postit.post.viewers.post.PostGridViewer;

public class ImportController extends BaseController {

    @FXML
    public BorderPane import_root_pane;

    @FXML
    public TextField default_source_folder;

    @FXML
    public ListView<RecoveryPostReader> post_list;

    @FXML
    public ListView<RecoveryPostReader> specific_folder_list;

    @FXML
    public TextField import_specific_post_recovery_field;

    @FXML
    public HBox chosen_folder;

    @FXML
    public RecoveryPostListSpecificItemController importSpecificController;

    public PostGridViewer postGridViewer;
    public ImportControllerManager importControllerManager = new ImportControllerManager(this);
    public ImportControllerManager.ImportInitializer importInitializer;
    public PostManagerProperties applicationProperties;

    public void initialize(PostManagerProperties postManagerProperties, PostGridViewer postGridViewer) {
        this.postGridViewer = postGridViewer;
        applicationProperties = postManagerProperties;
        importInitializer = importControllerManager.new ImportInitializer();
        importInitializer.initialize();
    }

    @FXML
    public void onSetDefaultFolder() {
        importControllerManager.chooseDefaultFolder();
    }

    @FXML
    public void onPostRecoveryFolder() {
        importControllerManager.chooseSpecificFolder();
    }

}