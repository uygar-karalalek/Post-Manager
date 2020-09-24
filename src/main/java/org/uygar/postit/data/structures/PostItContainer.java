package org.uygar.postit.data.structures;

import org.uygar.postit.post.properties.Sort;

public interface PostItContainer extends Container {

    String DB_PATH = "jdbc:sqlite:postit.db";

    void sortPostIts();

}