package org.uygar.postit.post.viewers.post_it.post_it_viewer.builder.graphic_builder;

import javafx.scene.image.Image;
import org.uygar.postit.post.PostIt;
import org.uygar.postit.post.properties.Colore;

public class PostViewerImageBuilder {

    public static Image build(Colore colore) {
        String builder = "org/uygar/images/" + "post_it_" +
                colore.toString() +
                ".png";
        return new Image(builder);
    }

}