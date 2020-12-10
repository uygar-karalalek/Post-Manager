package org.uygar.postit.controllers.post.postit;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import org.uygar.postit.controllers.BaseController;
import org.uygar.postit.controllers.exception.WindowCoordinatesContainer;
import org.uygar.postit.controllers.exception.WrongFieldsException;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.post.PostIt;
import org.uygar.postit.post.properties.Colore;
import org.uygar.postit.post.viewers.post_it.PostItGridViewer;

import java.util.Optional;
import java.util.function.Predicate;

import static org.uygar.postit.data.query_utils.QueryUtils.*;

public class PostItController extends BaseController {

    public static final int RECTANGLE_COLOR_CHOICE_SIZE = 20;

    @FXML
    public BorderPane postIt;
    @FXML
    public Rectangle postItRectangle;
    @FXML
    public DatePicker dataField;
    @FXML
    public TextField titoloField, oraField, minutoField, priorityField;
    @FXML
    public TextArea compitoField;
    @FXML
    public Button annullaBtn, salvaBtn, rimuoviBtn;
    @FXML
    public HBox coloreHBox;

    public PostIt loadedPostit;
    public PostItGridViewer postItGrid;
    public SimpleObjectProperty<Colore> rectangleColor = new SimpleObjectProperty<>();
    public DataMiner miner = new DataMiner();
    private boolean modifying;

    public void init(Optional<PostIt> loadingPostIt, PostItGridViewer containerOrganizer) {
        addPostItColorChoicesAsRectangles();
        this.postItGrid = containerOrganizer;
        rectangleColor.addListener((obs, oldColor, newColor) -> postItRectangle.setFill(newColor.postItColor));
        rectangleColor.set(Colore.GIALLO);

        if (modifying = loadingPostIt.isPresent()) {
            loadValues(loadingPostIt.get());
        }
    }

    private void loadValues(PostIt postIt) {
        loadedPostit = postIt;
        rimuoviBtn.setDisable(false);
        rectangleColor.set(postIt.getColore());
        titoloField.setText(postIt.getTitolo());
        compitoField.setText(postIt.getTesto());
        dataField.setValue(postIt.getDataScadenza().toLocalDate());
        oraField.setText(String.valueOf(postIt.getDataScadenza().getHour()));
        minutoField.setText(String.valueOf(postIt.getDataScadenza().getMinute()));
        priorityField.setText(String.valueOf(postIt.getPriority()));
    }

    @FXML
    public void onSalva() throws WrongFieldsException {
        Optional<PostIt> postItFromController = modifying ?
                PostItUtils.getPostItFromControllerWhenModifying(this)
                : PostItUtils.getPostItFromControllerWhenCreating(this);

        Predicate<PostIt> trySavePostIt = this::trySavePostIt;

        if (postItFromController.isPresent() && trySavePostIt.test(postItFromController.get())) {
            closePostItEditor();
        } else {
            throw new WrongFieldsException("C'è stato un errore! Inserisci i campi giusti!",
                    new WindowCoordinatesContainer(this.postIt.getScene().getWindow()));
        }
    }

    public boolean trySavePostIt(PostIt postIt) {
        boolean operationSucceed;
        if (modifying) {
            if (operationSucceed = tryModifyPostItOnDB(miner, postIt)) {
                PostItUtils.copyBaseValuesFromByPostIts(loadedPostit, postIt);
            }
        } else {
            if (operationSucceed = tryCreateNewPostItOnDB(miner, postIt)) {
                postItGrid.add(postIt);
            }
        }
        return operationSucceed;
    }

    @FXML
    public void onAnnulla() {
        closePostItEditor();
    }

    @FXML
    public void onRimuovi() {
        tryRemovePostItWithIdFromDB(miner, loadedPostit.getId());
        postItGrid.remove(loadedPostit);
        closePostItEditor();
    }

    private void closePostItEditor() {
        this.postIt.getScene().getWindow().hide();
    }

    private void addPostItColorChoicesAsRectangles() {
        for (Colore choice : Colore.values()) {
            Rectangle postItColorChoice = new Rectangle(RECTANGLE_COLOR_CHOICE_SIZE, RECTANGLE_COLOR_CHOICE_SIZE, choice.postItColor);
            postItColorChoice.getStyleClass().add("color");

            postItColorChoice.setOnMouseClicked(mouseEvent -> rectangleColor.set(choice));
            postItColorChoice.setOnMouseEntered(mouseEvent -> postItColorChoice.setHeight(RECTANGLE_COLOR_CHOICE_SIZE * 1.4));
            postItColorChoice.setOnMouseExited(mouseEvent -> postItColorChoice.setHeight(RECTANGLE_COLOR_CHOICE_SIZE));

            this.coloreHBox.getChildren().add(postItColorChoice);
        }
    }

}