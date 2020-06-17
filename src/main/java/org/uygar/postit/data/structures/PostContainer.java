package org.uygar.postit.data.structures;

import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.properties.Sort;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PostContainer implements Container {

    public static int numOfPosts = 0;
    private DataMiner dataMiner = new DataMiner();
    private ArrayList<Post> postList = new ArrayList<>();
    private Sort sortType;

    @Override
    public void sort() {

    }

    @Override
    public Sort getSort() {
        return this.sortType;
    }

    @Override
    public void setSort(Sort sort) {

    }

    // TODO : Add elements to the structure from the db.
    @Override
    public void initStructure() {

    }

    public void add(Post post) {
        postList.add(post);
    }

    public void remove(int index) {
        postList.remove(index);
    }

    public void remove(Post post) {
        int index = 0;
        for (Post element : postList) {
            if (!element.equals(post)) index++;
            else break;
        }
        postList.remove(index);
    }

}