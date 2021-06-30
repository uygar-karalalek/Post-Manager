package org.uygar.postit.controllers.application.add_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import org.uygar.postit.controllers.BaseController;
import org.uygar.postit.controllers.exception.WindowCoordinatesContainer;
import org.uygar.postit.controllers.exception.WrongFieldsException;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.properties.Sort;
import org.uygar.postit.post.viewers.post.PostGridViewer;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static org.uygar.postit.controllers.exception.WrongFieldsException.*;
import static org.uygar.postit.data.query_utils.QueryUtils.*;

public class AggiungiController extends BaseController implements Initializable {

    @FXML
    public VBox add;

    @FXML
    TextField nomePostField;

    @FXML
    Button btnOk, btnAnnulla;

    @FXML
    SplitMenuButton sortType;

    DataMiner dataMiner = new DataMiner();

    PostGridViewer postGridViewer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initSortChoice();
    }

    private void initSortChoice() {
        for (Sort element : Sort.values()) {
            MenuItem item = new MenuItem(element.getName());
            sortType.getItems().add(item);
            item.setOnAction(this::onSortDecided);
        }
    }

    public void onSortDecided(ActionEvent event) {
        MenuItem sort = (MenuItem) event.getTarget();
        this.sortType.setText(sort.getText());
    }

    @FXML
    public void onOk() throws WrongFieldsException {
        String name = nomePostField.getText();
        Sort sortingType = Sort.getSortBasedOnName(this.sortType.getText());

        Post post = new Post(null, name, LocalDateTime.now(), sortingType);

        Window currentWindow = this.add.getScene().getWindow();
        WindowCoordinatesContainer coordinates = new WindowCoordinatesContainer(currentWindow);

        boolean fieldsNotValid = areFieldsValid(name, sortingType);
        throwWrongFieldExceptionIf(fieldsNotValid, "Hai sbagliato ad inserire i campi!", coordinates);

        boolean notCreatedCorrectly = !(tryCreateNewPostOnDB(dataMiner, post));
        throwWrongFieldExceptionIf(notCreatedCorrectly, "Il post esiste già!", coordinates);

        addPostToViewAndUpdate(new Post(getLastCreatedPostId(dataMiner), name));
    }

    private boolean areFieldsValid(String name, Sort sortingType) {
        return name.isBlank()  || name.trim().length() > 18 || sortingType == null;
    }

    private void addPostToViewAndUpdate(Post post) {
        this.postGridViewer.postOrganizer.add(post);
        this.add.getScene().getWindow().hide();
    }

    @FXML
    public void onAnnulla() {
        add.getScene().getWindow().hide();
    }

    public void setPostGridViewer(PostGridViewer postGridViewer) {
        this.postGridViewer = postGridViewer;
    }

}