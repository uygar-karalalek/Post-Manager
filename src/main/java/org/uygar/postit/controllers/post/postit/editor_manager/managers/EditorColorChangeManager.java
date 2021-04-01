package org.uygar.postit.controllers.post.postit.editor_manager.managers;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import org.uygar.postit.controllers.post.postit.editor_manager.PostItEditorManager;
import org.uygar.postit.post.properties.Colore;

public class EditorColorChangeManager extends EditorManager {

    public static final Colore DEFAULT_EDITOR_COLOR = Colore.GIALLO;
    private static final int RECTANGLE_COLOR_CHOICE_SIZE = 20;

    public EditorColorChangeManager(PostItEditorManager manager) {
        super(manager);
    }

    @Override
    public void initialize() {
        initializeRectangleColor();
        addPosItColorChoicesAsRectangles();
    }

    public void initializeRectangleColor() {
        getManager().rectangleColor.addListener(this::onPostItColorChange);
        getManager().rectangleColor.set(DEFAULT_EDITOR_COLOR);
    }

    private void onPostItColorChange(ObservableValue<? extends Colore> obs, Colore oldColore, Colore newColore) {
        getManager().getPostItController().postItRectangle.setFill(newColore.postItColor);

        double red = newColore.postItTextColor.getRed() * 255;
        double green = newColore.postItTextColor.getGreen() * 255;
        double blue = newColore.postItTextColor.getBlue() * 255;

        String fieldColor = "-fx-text-fill: rgb(" + red + ", " + green + ", " + blue + ");";
        getManager().getPostItController().titoloField.setStyle(fieldColor);
        // I DON'T KNOW WHY JAVAFX DOES ALSO AN ALIGNMENT OVERRIDE
        getManager().getPostItController().titoloField.setAlignment(Pos.CENTER);
        getManager().getPostItController().compitoField.setStyle(fieldColor);

        getManager().getPostItController().propertyBox.getChildren().forEach(node -> {
                    node.setStyle("-fx-background-color: linear-gradient(" + newColore.getPostItColorWebFormat() + " 95%, #ffc9c9);");
                    if (node instanceof Pane) setLabelColorRecursively((Pane) node, newColore.getPostItTextColorWebFormat());
                }
        );
    }

    private void setLabelColorRecursively(Pane pane, String labelWebColor) {
        if (pane.getChildren().size() == 0) return;
        pane.getChildren().forEach(innerNode -> {
            if (innerNode instanceof Pane) setLabelColorRecursively((Pane) innerNode, labelWebColor);
            else if (innerNode instanceof Label) ((Label)innerNode).setTextFill(Color.web(labelWebColor));
            else if (innerNode instanceof TextField) innerNode.setStyle("-fx-text-fill: " + labelWebColor);
            else if (innerNode instanceof DatePicker) ((DatePicker) innerNode).getEditor().setStyle("-fx-text-fill: " + labelWebColor);
        });
    }

    public void addPosItColorChoicesAsRectangles() {
        HBox colorsContainer = new HBox();
        colorsContainer.setMaxHeight(RECTANGLE_COLOR_CHOICE_SIZE);
        colorsContainer.setId("colorsContainer");

        for (Colore choice : Colore.values()) {
            Rectangle postItColorChoice = new Rectangle(RECTANGLE_COLOR_CHOICE_SIZE, RECTANGLE_COLOR_CHOICE_SIZE, choice.postItColor);
            postItColorChoice.getStyleClass().add("color");

            postItColorChoice.setOnMouseClicked(mouseEvent -> getManager().rectangleColor.set(choice));
            postItColorChoice.setOnMouseEntered(mouseEvent -> postItColorChoice.setHeight(RECTANGLE_COLOR_CHOICE_SIZE * 1.4));
            postItColorChoice.setOnMouseExited(mouseEvent -> postItColorChoice.setHeight(RECTANGLE_COLOR_CHOICE_SIZE));

            VBox colorRectangleWrapper = new VBox();
            colorRectangleWrapper.setId("colorRectangleVBoxWrapper");
            colorRectangleWrapper.getChildren().add(postItColorChoice);

            colorsContainer.getChildren().add(colorRectangleWrapper);
        }

        getManager().getPostItController().coloriTopHBox.getChildren().add(colorsContainer);
    }

}