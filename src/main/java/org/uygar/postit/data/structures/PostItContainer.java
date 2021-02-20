package org.uygar.postit.data.structures;

import org.uygar.postit.post.PostIt;
import org.uygar.postit.post.properties.Sort;

import java.util.List;

public interface PostItContainer extends Container {

    String DB_PATH = "jdbc:sqlite:postit.db";

    void sortPostIts();

    List<PostIt> getList();
    List<PostIt> getSorted();

}