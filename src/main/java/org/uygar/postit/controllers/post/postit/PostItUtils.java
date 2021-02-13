package org.uygar.postit.controllers.post.postit;

import org.uygar.postit.post.PostIt;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static java.lang.Integer.parseInt;

public class PostItUtils {

    public static void copyFirstToSecond(PostIt first, PostIt second) {
        second.setId(first.getId());
        second.setColore(first.getColore());
        second.setTitolo(first.getTitolo());
        second.setPriority(first.getPriority());
        second.setDataScadenza(first.getDataScadenza());
        second.setColore(first.getColore());
        second.setTesto(first.getTesto());
    }

    public static Optional<PostIt> getPostItFromControllerWhenModifying(PostItController postItController) {
        Optional<PostIt> returned;
        try {
            // in this line we get postIt with values that you specify when create a completely new postIt
            // that is, without id and creation date, that in this case I fill if postIt data is valid.
            returned = getPostItFromControllerWhenCreating(postItController);
            if (returned.isPresent()) {
                returned.get().setId(postItController.loadedPostIt.getId());
                returned.get().setDataCreazione(postItController.loadedPostIt.getDataCreazione());
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

            postIt.setColore(postItController.postItEditorManager.rectangleColor.get());
            postIt.setPriority(postItController.prioritySpinner.getValue());
            return Optional.of(postIt);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}