package org.uygar.postit.post.properties.converters;

import javafx.beans.property.BooleanProperty;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.PostIt;
import org.uygar.postit.post.properties.Colore;
import org.uygar.postit.post.properties.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConvertertUtil {

    public static List<Post> convertSQLResponseToPost(Map<String, List<String>> posts, int numOfPosts) {
        Function<Integer, Post> parse = postsNum -> parseFromAbstractPostToObject(posts, postsNum);
        return getRangedIntStream(numOfPosts).filter(postIndex -> parse.apply(postIndex) != null).
                mapToObj(parse::apply).collect(Collectors.toList());
    }

    public static Post parseFromAbstractPostToObject(Map<String, List<String>> posts, int dataElementIndex) {
        if (isValidMappedObjectList(posts)) {
            int id = Integer.parseInt(posts.get("id").get(dataElementIndex));
            String name = posts.get("name").get(dataElementIndex);
            LocalDateTime creation = LocalDateTime.parse(posts.get("creationDate").get(dataElementIndex));
            LocalDateTime lastModified = LocalDateTime.parse(posts.get("lastModifiedDate").get(dataElementIndex));
            Sort sort = Sort.valueOf(posts.get("sort").get(dataElementIndex));
            return new Post(id, name, creation, lastModified, sort);
        }

        return null;
    }

    public static List<PostIt> convertSQLResponseToPostIt(Map<String, List<String>> postIts, int numOfPostIts) {
        Function<Integer, PostIt> parse = postItNum -> parseFromAbstractPostItToObject(postIts, postItNum);
        return getRangedIntStream(numOfPostIts).filter(postIndex -> parse.apply(postIndex) != null).
                mapToObj(parse::apply).collect(Collectors.toList());
    }

    private static PostIt parseFromAbstractPostItToObject(Map<String, List<String>> postIts, int dataElementIndex) {
        if (isValidMappedObjectList(postIts)) {
            int id = Integer.parseInt(postIts.get("id").get(dataElementIndex));
            boolean done = Boolean.parseBoolean(postIts.get("done").get(dataElementIndex));
            String title = postIts.get("title").get(dataElementIndex);
            String text = postIts.get("text").get(dataElementIndex);
            LocalDateTime creationDate = LocalDateTime.parse(postIts.get("creationDate").get(dataElementIndex));
            LocalDateTime endDate = LocalDateTime.parse(postIts.get("endDate").get(dataElementIndex));
            Colore colore = Colore.valueOf(postIts.get("colore").get(dataElementIndex));
            return  new PostIt(id, done, title, text, creationDate, endDate, colore);
        }

        return null;
    }

    private static IntStream getRangedIntStream(int limit) {
        return IntStream.range(0, limit);
    }

    private static boolean isValidMappedObjectList(Map<String, List<String>> postIts) {
        return postIts.values().stream().anyMatch(list -> list.size() > 0);
    }

}