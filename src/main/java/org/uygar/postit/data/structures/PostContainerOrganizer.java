package org.uygar.postit.data.structures;

import org.jetbrains.annotations.NotNull;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.data.database.queries.DQL;
import org.uygar.postit.data.database.queries.DQLQueryBuilder;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.properties.Sort;
import org.uygar.postit.viewers.PostViewer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PostContainerOrganizer implements PostContainer, Iterable<Post> {

    public static int numOfPosts = 0;
    private DataMiner dataMiner;
    private ArrayList<Post> postList = new ArrayList<>();
    private Sort sortType;

    public PostContainerOrganizer(DataMiner dataMiner) {
        this.dataMiner = dataMiner;
        initStructure();
    }

    private void initStructure() {
        DQL dql = new DQLQueryBuilder().selectAll().from("post");
        dataMiner.executeQuery(dql);

        Map<String, List<String>> posts = dataMiner.getListOfResult();
        numOfPosts = posts.get("id").size();

        for (int i = 0; i < numOfPosts; i++)
            add(getPost(posts, i));
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

    private Post getPost(Map<String, List<String>> posts, int index) {
        int id = Integer.parseInt(posts.get("id").get(index));
        String name = posts.get("name").get(index);
        LocalDateTime creation = LocalDateTime.parse(posts.get("creationDate").get(index));
        LocalDateTime lastModified = LocalDateTime.parse(posts.get("lastModifiedDate").get(index));
        Sort sort = Sort.valueOf(posts.get("sort").get(index));

        return new Post(id, name, creation, lastModified, sort);
    }

    @Override
    public void filter() {

    }

    public ArrayList<Post> getPostList() {
        return postList;
    }

    @Override
    public String toString() {
        return "PostContainer{" +
                "postList=" + postList +
                '}';
    }

    @NotNull
    @Override
    public Iterator<Post> iterator() {
        Iterator<Post> iterator = postList.iterator();
        return iterator;
    }

}