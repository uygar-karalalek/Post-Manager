package org.uygar.postit.controllers.application.import_controller.import_controller_utils.views.recovery_post_list_view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import org.jetbrains.annotations.NotNull;
import org.uygar.postit.controllers.ControllerType;
import org.uygar.postit.controllers.application.FXLoader;
import org.uygar.postit.controllers.loader.WindowLoader;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.data.query_utils.QueryUtils;
import org.uygar.postit.data.recoveries.post.recovery_folder.reader.RecoveryPostReader;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.PostIt;
import org.uygar.postit.post.viewers.post.PostGridViewer;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RecoveryPostListDefaultListItemController extends ListCell<RecoveryPostReader> {

    @FXML
    public Label date;
    @FXML
    public Label post_name;
    @FXML
    public Label num_of_post_its;
    @FXML
    public Button import_button;

    private DataMiner dataMiner = new DataMiner();

    private PostGridViewer postGridViewer;

    public RecoveryPostListDefaultListItemController(PostGridViewer postGridViewer) {
        this.postGridViewer = postGridViewer;
        this.inflateFXML();
        this.import_button.setOnAction(this::onImportClicked);
    }

    private void onImportClicked(ActionEvent event) {
        // HIDE ALL POST-IT PAGES BEFORE THE IMPORT
        Post newPost = this.getItem().getNewPost();
        List<PostIt> newPostIts = this.getItem().getNewPostIts();

        WindowLoader.hideAllControllersWindowsOfType(ControllerType.POSTIT);

        QueryUtils.tryCreateNewPostOnDB(dataMiner, newPost);
        int newPostID = QueryUtils.getLastCreatedPostId(dataMiner);

        // SET THE ID OF POST BECAUSE THE POST-ITS ORGANIZER RETRIEVES
        // POST-ITS FROM DB BASED ON THAT DATA OF FATHER POST!
        // SEE: controllers.post.controller_utilities.controller_manager.initializers.post.initGridPane()
        newPost.setId(newPostID);
        this.postGridViewer.postOrganizer.add(newPost);

        newPostIts.forEach(postIt -> {
            postIt.setPostFatherId(newPostID);
            QueryUtils.tryCreateNewPostItOnDB(dataMiner, postIt);
        });
    }

    private void inflateFXML() {
        try {
            FXMLLoader fxml = FXLoader.getFXMLLoader("import_default_list_item", "app");
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
            initGraphicsByRecoveryPostReader(postReader);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }

    public void initGraphicsByRecoveryPostReader(RecoveryPostReader postReader) {
        post_name.setText(postReader.getNewPost().getName());
        num_of_post_its.setText(Integer.toString(postReader.getNewPostIts().size()));
        date.setText(postReader.getNewPost().getCreationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

}