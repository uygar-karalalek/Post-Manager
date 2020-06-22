package org.uygar.postit.data.structures;

public interface PostContainer extends Container {

    String DB_PATH = "jdbc:sqlite:post.db";
    void filter();

}