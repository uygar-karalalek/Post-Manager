package org.uygar.postit.controllers.application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import org.uygar.postit.controllers.application.exception.WrongFieldsException;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.data.database.queries.DMLQueryBuilder;
import org.uygar.postit.data.database.queries.DQLQueryBuilder;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.properties.Sort;
import org.uygar.postit.post.viewers.post.PostGridViewer;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static org.uygar.postit.controllers.application.exception.WrongFieldsException.*;

public class AggiungiController implements Initializable {

    @FXML
    public VBox root;

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
        Sort sortingType = Sort.getSortFromName(this.sortType.getText());

        Post post = new Post(null, name, LocalDateTime.now(), sortingType);

        Window currentWindow = this.root.getScene().getWindow();
        WindowCoordinates coordinates = new WindowCoordinates(currentWindow);

        boolean fieldsNotValid = name.isEmpty() || sortingType == null;
        throwWrongFieldExceptionIf(fieldsNotValid, "Hai sbagliato ad inserire i campi!", coordinates);

        boolean notCreatedCorrectly = !tryCreateNewPost(post);
        throwWrongFieldExceptionIf(notCreatedCorrectly, "Il post esiste già!", coordinates);

        addPostToViewAndUpdate(new Post(getLastCreatedPostId(), name));
    }

    public void throwWrongFieldExceptionIf(boolean condition, String message, WindowCoordinates coordinates) throws WrongFieldsException {
        if (condition)
            throw new WrongFieldsException(message, coordinates);
    }

    public boolean tryCreateNewPost(Post post) {
        DMLQueryBuilder query = new DMLQueryBuilder();
        query.insert().into("post").values(
                "null", // visto che c'è l'auto increment
                post.getName(),
                post.getSortType().toString(),
                post.getCreationDate().toString(),
                post.getCreationDate().toString()); // l'ultima modifica è la volta in cui lo crei

        return dataMiner.tryExecute(query);
    }

    private void addPostToViewAndUpdate(Post post) {
        this.postGridViewer.postOrganizer.add(post);
        this.root.getScene().getWindow().hide();
        this.postGridViewer.updateLast();
    }

    private Integer getLastCreatedPostId() {
        DQLQueryBuilder dql = new DQLQueryBuilder();
        dql.select("MAX(id) as id").from("post");
        dataMiner.executeQuery(dql);
        String result = dataMiner.getListOfResult().get("id").get(0);
        return Integer.parseInt(result);
    }

    @FXML
    public void onAnnulla() {
        root.getScene().getWindow().hide();
    }

    public void setPostGridViewer(PostGridViewer postGridViewer) {
        this.postGridViewer = postGridViewer;
    }

}