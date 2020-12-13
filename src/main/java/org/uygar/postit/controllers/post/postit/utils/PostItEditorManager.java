package org.uygar.postit.controllers.post.postit.utils;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.shape.Rectangle;
import org.uygar.postit.controllers.post.postit.PostItController;
import org.uygar.postit.controllers.post.postit.PostItUtils;
import org.uygar.postit.post.PostIt;
import org.uygar.postit.post.properties.Colore;

import static org.uygar.postit.data.query_utils.QueryUtils.tryCreateNewPostItOnDB;
import static org.uygar.postit.data.query_utils.QueryUtils.tryModifyPostItOnDB;

public class PostItEditorManager {

    private static final int RECTANGLE_COLOR_CHOICE_SIZE = 20;

    private final PostItController postItController;
    public SimpleObjectProperty<Colore> rectangleColor = new SimpleObjectProperty<>();

    public PostItEditorManager(PostItController postItController) {
        this.postItController = postItController;

        rectangleColor.addListener((obs, oldColor, newColor) ->
                postItController.postItRectangle.setFill(newColor.postItColor));

        rectangleColor.set(Colore.GIALLO);
    }

    public void initialize() {
        addPosItColorChoicesAsRectangles();
        loadValuesIfModifying();
    }

    private void loadValuesIfModifying() {
        if (postItController.modifying) {

            PostIt loadedPostIt = postItController.loadedPostIt;

            rectangleColor.set(loadedPostIt.getColore());

            postItController.rimuoviBtn.setDisable(false);
            postItController.titoloField.setText(loadedPostIt.getTitolo());
            postItController.compitoField.setText(loadedPostIt.getTesto());
            postItController.dataField.setValue(loadedPostIt.getDataScadenza().toLocalDate());
            postItController.oraField.setText(String.valueOf(loadedPostIt.getDataScadenza().getHour()));
            postItController.minutoField.setText(String.valueOf(loadedPostIt.getDataScadenza().getMinute()));
            postItController.priorityField.setText(String.valueOf(loadedPostIt.getPriority()));
        }
    }

    private void addPosItColorChoicesAsRectangles() {
        for (Colore choice : Colore.values()) {
            Rectangle postItColorChoice = new Rectangle(RECTANGLE_COLOR_CHOICE_SIZE, RECTANGLE_COLOR_CHOICE_SIZE, choice.postItColor);
            postItColorChoice.getStyleClass().add("color");

            postItColorChoice.setOnMouseClicked(mouseEvent -> rectangleColor.set(choice));
            postItColorChoice.setOnMouseEntered(mouseEvent -> postItColorChoice.setHeight(RECTANGLE_COLOR_CHOICE_SIZE * 1.4));
            postItColorChoice.setOnMouseExited(mouseEvent -> postItColorChoice.setHeight(RECTANGLE_COLOR_CHOICE_SIZE));

            postItController.coloreHBox.getChildren().add(postItColorChoice);
        }
    }

    public boolean trySavePostIt(PostIt postIt) {
        boolean operationSucceed;
        if (postItController.modifying) {
            if (operationSucceed = tryModifyPostItOnDB(postItController.miner, postIt)) {
                PostItUtils.copyBaseValuesFromByPostIts(postItController.loadedPostIt, postIt);
            }
        } else {
            if (operationSucceed = tryCreateNewPostItOnDB(postItController.miner, postIt)) {
                postItController.postItGrid.add(postIt);
            }
        }
        return operationSucceed;
    }

    public void closePostItEditor() {
        postItController.postIt.getScene().getWindow().hide();
    }

}