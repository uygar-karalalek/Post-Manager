package org.uygar.postit.viewers;

import javafx.scene.shape.Rectangle;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.properties.Sort;

import java.time.LocalDateTime;

public class PostViewer extends Post {

    private Rectangle view;

    public PostViewer(int id, String name, LocalDateTime creationDate, LocalDateTime lastModifiedDate, Sort sortType) {
        super(id, name, creationDate, lastModifiedDate, sortType);
    }

    public PostViewer(int id, String name, LocalDateTime creationDate, LocalDateTime lastModifiedDate) {
        super(id, name, creationDate, lastModifiedDate, Sort.UNDONE);
    }

}