package org.uygar.postit.controllers.application.import_controller.import_controller_utils.manager;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import org.uygar.postit.controllers.application.import_controller.ImportController;
import org.uygar.postit.controllers.application.import_controller.import_controller_utils.views.recovery_post_list_view.RecoveryListCellFactory;
import org.uygar.postit.data.recoveries.post.recovery_folder.reader.RecoveryPostReader;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.function.Consumer;

public class ImportControllerManager extends ImportManager {

    private final ImportInitializer initializer;

    public ImportControllerManager(ImportController importController) {
        super(importController);
        initializer = this.new ImportInitializer();
    }

    public void chooseSpecificFolder() {
        chooseFolder(chosenFile -> {
            String absolutePath = chosenFile.getAbsolutePath();
            importController.post_recovery_field.setText(absolutePath);
            importController.applicationProperties.putSpecificFolderProperty(absolutePath);
            importController.applicationProperties.storeProperties();
            updateSpecificSourceList();
        });
    }

    public void chooseDefaultFolder() {
        chooseFolder(chosenDir ->
                {
                    String absolutePath = chosenDir.getAbsolutePath();
                    importController.default_source_folder.setText(absolutePath);
                    updateDefaultSourceList();
                    updateDefaultFolderProperty();
                }
        );
    }

    public void chooseFolder(Consumer<File> onFileFound) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File chosenDir = directoryChooser.showDialog(importController.getStage());

        if (chosenDir != null) onFileFound.accept(chosenDir);
    }

    public File getDefaultFolder() {
        return new File(importController.default_source_folder.getText());
    }

    public void updateDefaultFolderProperty() {
        importController.applicationProperties.putProperty("defaultImportFolder", importController.default_source_folder.getText());
        importController.applicationProperties.storeProperties();
    }

    public void updateDefaultSourceList() {
        ListView<RecoveryPostReader> listView = importController.post_list;
        TextField sourceField = importController.default_source_folder;
        updateList(listView, sourceField);
    }

    public void updateSpecificSourceList() {
        ListView<RecoveryPostReader> listView = importController.specific_folder_list;
        TextField sourceField = importController.post_recovery_field;
        updateList(listView, sourceField);
    }

    public void updateList(ListView<RecoveryPostReader> listView, TextField folderTextField) {
        if (folderTextField.getText() == null
                || folderTextField.getText().isBlank()) return;

        File dir = getDefaultFolder();
        listView.getItems().clear();

        if (RecoveryPostReader.existsPostRecoveryFile(dir.getAbsolutePath())) {
            // ENTER IN THIS SECTION MEANS THAT THE USER CHOOSED
            // DIRECTLY THE POST RECOVERY FOLDER
            tryAddPostListItem(listView, dir);
        } else {
            // ENTER IN THIS SECTION MEANS THAT ALL THE FOLDERS (UNDER THE FIRST LAYER!)
            // THAT CONTAIN POST RECOVERY FILES WILL BE IMPORTED
            Arrays.stream(dir.listFiles()).forEach(currDir -> {
                tryAddPostListItem(listView, currDir);
            });
        }
    }

    public void tryAddPostListItem(ListView<RecoveryPostReader> listView, File file) {
        try (RecoveryPostReader reader = new RecoveryPostReader(file.getAbsolutePath())) {
            listView.getItems().add(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class ImportInitializer {

        public void initialize() {
            String lastFolder = importController.applicationProperties.getStringProperty("defaultImportFolder");
            importController.default_source_folder.setText(lastFolder);

            RecoveryListCellFactory recoveryFactoryCallBack =
                    new RecoveryListCellFactory(importController.postGridViewer,
                            importController.postIts);

            importController.post_list.setCellFactory(recoveryFactoryCallBack);

            updateDefaultSourceList();
            updateSpecificSourceList();
        }

    }

}