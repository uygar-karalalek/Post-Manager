package org.uygar.postit.controllers.post.postit;

import org.uygar.postit.post.PostIt;
import org.uygar.postit.post.properties.Colore;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static java.lang.Integer.parseInt;

public class PostItUtils {

    public static void copyBaseValuesFromByPostIts(PostIt first, PostIt second) {
        first.setId(second.getId());
        first.setColore(second.getColore());
        first.setTitolo(second.getTitolo());
        first.setPriority(second.getPriority());
        first.setDataScadenza(second.getDataScadenza());
        first.setColore(second.getColore());
        first.setTesto(second.getTesto());
    }

    public static Optional<PostIt> getPostItFromControllerWhenModifying(PostItController postItController) {
        Optional<PostIt> returned;
        try {
            returned = getPostItFromControllerWhenCreating(postItController);
            if (returned.isPresent()) {
                returned.get().setId(postItController.loadedPostit.getId());
                returned.get().setDataCreazione(postItController.loadedPostit.getDataCreazione());
            }
        } catch (Exception e) {
            returned = Optional.empty();
        }
        return returned;
    }

    public static Optional<PostIt> getPostItFromControllerWhenCreating(PostItController postItController) {
        try {
            PostIt postIt = PostIt.empty();

            postIt.setTitolo(postItController.titoloField.getText());
            postIt.setTesto(postItController.compitoField.getText());
            postIt.setPostFatherId(postItController.postItGrid.getPostItOrganizer().getFatherPost().getId());

            postIt.setDataCreazione(LocalDateTime.now());
            postIt.setDataScadenza(LocalDateTime.of(postItController.dataField.getValue(),
                    LocalTime.of(parseInt(postItController.oraField.getText()),
                            parseInt(postItController.minutoField.getText()))));

            postIt.setColore(postItController.rectangleColor.get());
            postIt.setPriority(parseInt(postItController.priorityField.getText()));
            return Optional.of(postIt);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}