package org.uygar.postit.controllers.post.postit.utils;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import org.uygar.postit.controllers.post.postit.PostItController;
import org.uygar.postit.controllers.post.postit.PostItUtils;
import org.uygar.postit.post.PostIt;
import org.uygar.postit.post.properties.Colore;

import static org.uygar.postit.data.query_utils.QueryUtils.*;

public class PostItEditorManager {

    private static final int RECTANGLE_COLOR_CHOICE_SIZE = 20;

    private final PostItController postItController;
    public SimpleObjectProperty<Colore> rectangleColor = new SimpleObjectProperty<>();

    public PostItEditorManager(PostItController postItController) {
        this.postItController = postItController;

        rectangleColor.addListener(this::onPostItColorChange);

        rectangleColor.set(Colore.GIALLO);
    }

    public void onPostItColorChange(ObservableValue<? extends Colore> obs, Colore oldColore, Colore newColore) {
        postItController.postItRectangle.setFill(newColore.postItColor);

        double red = newColore.postItTextColor.getRed() * 255;
        double green = newColore.postItTextColor.getGreen() * 255;
        double blue = newColore.postItTextColor.getBlue() * 255;

        String fieldColor = "-fx-text-fill: rgb(" + red + ", " + green + ", " + blue +");";
        postItController.titoloField.setStyle(fieldColor);
        // I DON'T KNOW WHY JAVAFX DOES ALSO AN ALIGNMENT OVERRIDE
        postItController.titoloField.setAlignment(Pos.CENTER);
        postItController.compitoField.setStyle(fieldColor);
    }

    public void initialize() {
        requestTaskTextFocus();
        addPosItColorChoicesAsRectangles();
        loadValuesIfModifying();
    }

    private void requestTaskTextFocus() {
        postItController.compitoField.setFocusTraversable(true);
        Platform.runLater(()-> {
            postItController.compitoField.requestFocus();

            // Move to the end the caret position of the task text
            postItController.compitoField
                    .positionCaret(postItController.compitoField.getText().length());
        });
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
        HBox colorsContainer = new HBox();
        colorsContainer.setMaxHeight(RECTANGLE_COLOR_CHOICE_SIZE);
        colorsContainer.setId("colorsContainer");

        for (Colore choice : Colore.values()) {
            Rectangle postItColorChoice = new Rectangle(RECTANGLE_COLOR_CHOICE_SIZE, RECTANGLE_COLOR_CHOICE_SIZE, choice.postItColor);
            postItColorChoice.getStyleClass().add("color");

            postItColorChoice.setOnMouseClicked(mouseEvent -> rectangleColor.set(choice));
            postItColorChoice.setOnMouseEntered(mouseEvent -> postItColorChoice.setHeight(RECTANGLE_COLOR_CHOICE_SIZE * 1.4));
            postItColorChoice.setOnMouseExited(mouseEvent -> postItColorChoice.setHeight(RECTANGLE_COLOR_CHOICE_SIZE));

            VBox colorRectangleWrapper = new VBox();
            colorRectangleWrapper.setId("colorRectangleVBoxWrapper");
            colorRectangleWrapper.getChildren().add(postItColorChoice);

            colorsContainer.getChildren().add(colorRectangleWrapper);
        }

        postItController.coloriTopHBox.getChildren().add(colorsContainer);
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