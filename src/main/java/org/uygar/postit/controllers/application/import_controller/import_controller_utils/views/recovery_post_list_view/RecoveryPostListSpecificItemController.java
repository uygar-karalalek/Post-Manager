package org.uygar.postit.controllers.application.import_controller.import_controller_utils.views.recovery_post_list_view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.BorderPane;
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
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class RecoveryPostListSpecificItemController implements Initializable {

    @FXML
    private BorderPane root;
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

    private RecoveryPostReader reader;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.import_button.setOnAction(this::onImportClicked);
    }

    private void onImportClicked(ActionEvent event) {
        // HIDE ALL POST-IT PAGES BEFORE THE IMPORT
        Post newPost = reader.getNewPost();
        List<PostIt> newPostIts = reader.getNewPostIts();

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

    public void updateGraphicsByRecoveryPostReader() {
        if (!root.isVisible()) root.setVisible(true);

        System.out.println(root.isVisible());
        post_name.setText(reader.getNewPost().getName());
        num_of_post_its.setText(Integer.toString(reader.getNewPostIts().size()));
        date.setText(reader.getNewPost().getCreationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    public void setPostGridViewer(PostGridViewer postGridViewer) {
        if (this.postGridViewer == null)
            this.postGridViewer = postGridViewer;
    }

    public void setReader(RecoveryPostReader reader) {
        this.reader = reader;
    }

}