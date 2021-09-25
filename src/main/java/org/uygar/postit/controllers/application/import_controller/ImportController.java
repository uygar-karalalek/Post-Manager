package org.uygar.postit.controllers.application.import_controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.uygar.postit.controllers.BaseController;
import org.uygar.postit.controllers.application.import_controller.import_controller_utils.manager.ImportControllerManager;
import org.uygar.postit.data.properties.PostManagerProperties;
import org.uygar.postit.data.recoveries.post.recovery_folder.reader.RecoveryPostReader;
import org.uygar.postit.data.structures.PostContainerOrganizer;
import org.uygar.postit.data.structures.PostItContainerOrganizer;
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
    public TextField post_recovery_field;

    @FXML
    public HBox chosen_folder;

    public PostGridViewer postGridViewer;
    public PostItContainerOrganizer postIts;

    public ImportControllerManager importControllerManager = new ImportControllerManager(this);
    public ImportControllerManager.ImportInitializer importInitializer;
    public PostManagerProperties applicationProperties;

    public void initialize(PostManagerProperties postManagerProperties,
                           PostGridViewer postGridViewer,
                           PostItContainerOrganizer postIts) {

        this.postGridViewer = postGridViewer;
        this.postIts = postIts;

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