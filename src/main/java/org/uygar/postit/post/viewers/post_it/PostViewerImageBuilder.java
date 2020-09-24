package org.uygar.postit.post.viewers.post_it;

import javafx.scene.image.Image;
import org.uygar.postit.post.PostIt;
import org.uygar.postit.post.properties.Colore;

public class PostViewerImageBuilder {

    public static Image build(Colore colore) {
        StringBuilder builder = new StringBuilder("org/uygar/images/");
        builder.append("post_it_");
        builder.append(colore.toString());
        builder.append(".png");

        return new Image(builder.toString());
    }

}