package org.uygar.postit.controllers.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import org.uygar.postit.controllers.app.exception.WrongFieldsException;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.data.database.queries.DMLQueryBuilder;
import org.uygar.postit.data.database.queries.DQLQueryBuilder;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.properties.Sort;
import org.uygar.postit.viewers.PostGridViewer;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AggiungiController implements Initializable {

    @FXML
    VBox root;

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
        Sort sortType = Sort.getSortFromName(this.sortType.getText());
        Window currentWindow = this.root.getScene().getWindow();

        double windowY = currentWindow.getY();
        double windowX = currentWindow.getX();
        if (this.nomePostField.getText().isEmpty() || sortType == null)
            throw new WrongFieldsException("Inserisci tutti i campi!", windowX, windowY);

        LocalDateTime creationDate = LocalDateTime.now();

        DMLQueryBuilder query = new DMLQueryBuilder();
        query.insert().into("post").values(
                "null", // visto che c'è l'auto increment
                name,
                sortType.toString(),
                creationDate.toString(),
                creationDate.toString()); // l'ultima modifica è la volta in cui lo crei

        if (!dataMiner.tryExecute(query))
            throw new WrongFieldsException("Il post esiste già!", windowX, windowY);

        Integer id = getMaxId();
        this.postGridViewer.postOrganizer.add(new Post(id, name, creationDate, creationDate));
        this.root.getScene().getWindow().hide();
        this.postGridViewer.updateLast();
    }

    private Integer getMaxId() {
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