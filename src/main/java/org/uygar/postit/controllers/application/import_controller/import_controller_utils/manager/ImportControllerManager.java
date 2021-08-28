package org.uygar.postit.controllers.application.import_controller.import_controller_utils.manager;

import javafx.stage.DirectoryChooser;
import org.uygar.postit.controllers.application.import_controller.ImportController;
import org.uygar.postit.controllers.application.import_controller.import_controller_utils.views.recovery_post_list_view.RecoveryListCellFactory;
import org.uygar.postit.data.recoveries.post.recovery_folder.reader.RecoveryPostReader;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ImportControllerManager extends ImportManager {

    private final ImportInitializer initializer;

    public ImportControllerManager(ImportController importController) {
        super(importController);
        initializer = this.new ImportInitializer();
    }

    public void chooseAndSaveDefaultFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File chosenDir = directoryChooser.showDialog(importController.getStage());

        if (chosenDir != null) {
            importController.default_source_folder.setText(chosenDir.getAbsolutePath());
            importController.applicationProperties.putProperty("lastFolder", importController.default_source_folder.getText());
            importController.applicationProperties.storeProperties();
            initializer.updateList();
            updateDefaultFolderProperty();
        }
    }

    public void tryAddPostListItem(File file) {
        try (RecoveryPostReader reader = new RecoveryPostReader(file.getAbsolutePath())) {
            importController.post_list.getItems().add(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getDefaultFolder() {
        return new File(importController.default_source_folder.getText());
    }

    public void updateDefaultFolderProperty() {
        importController.applicationProperties.putProperty("defaultImportFolder", importController.default_source_folder.getText());
        importController.applicationProperties.storeProperties();
    }

    public class ImportInitializer {

        public void initialize() {
            String lastFolder = importController.applicationProperties.getStringProperty("defaultImportFolder");
            importController.default_source_folder.setText(lastFolder);
            importController.post_list.setCellFactory(new RecoveryListCellFactory());
            updateList();
        }

        public void updateList() {
            if (importController.default_source_folder.getText() == null
                    || importController.default_source_folder.getText().isBlank()) return;

            File dir = getDefaultFolder();
            importController.post_list.getItems().clear();

            if (RecoveryPostReader.existsPostRecoveryFile(dir.getAbsolutePath())) {
                // ENTER IN THIS SECTION MEANS THAT THE USER CHOOSED
                // DIRECTLY THE POST RECOVERY FOLDER
                tryAddPostListItem(dir);
            } else {
                // ENTER IN THIS SECTION MEANS THAT ALL THE FOLDERS (UNDER THE FIRST LAYER!)
                // THAT CONTAIN POST RECOVERY FILES WILL BE IMPORTED
                Arrays.stream(dir.listFiles()).forEach(ImportControllerManager.this::tryAddPostListItem);
            }
        }

    }

}