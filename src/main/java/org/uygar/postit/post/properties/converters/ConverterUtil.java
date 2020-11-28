package org.uygar.postit.post.properties.converters;

import javafx.beans.property.BooleanProperty;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.PostIt;
import org.uygar.postit.post.properties.Colore;
import org.uygar.postit.post.properties.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ConverterUtil {

    public static List<Post> convertSQLResponseToPost(Map<String, List<String>> posts, int numOfPosts) {
        Function<Integer, Optional<Post>> parse = postsNum -> parseFromAbstractPostToObject(posts, postsNum);
        return getRangedIntStream(numOfPosts).mapToObj(parse::apply).flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

    public static Optional<Post> parseFromAbstractPostToObject(Map<String, List<String>> posts, int dataElementIndex) {
        if (isValidMappedObjectList(posts)) {
            int id = Integer.parseInt(posts.get("id").get(dataElementIndex));
            String name = posts.get("name").get(dataElementIndex);
            LocalDateTime creation = LocalDateTime.parse(posts.get("creationDate").get(dataElementIndex));
            LocalDateTime lastModified = LocalDateTime.parse(posts.get("lastModifiedDate").get(dataElementIndex));
            Sort sort = Sort.valueOf(posts.get("sort").get(dataElementIndex));
            return Optional.of(new Post(id, name, creation, lastModified, sort));
        }
        return Optional.empty();
    }

    public static List<PostIt> convertSQLResponseToPostIt(Map<String, List<String>> postIts, int numOfPostIts) {
        Function<Integer, Optional<PostIt>> parse = postItNum -> parseFromAbstractPostItToObject(postIts, postItNum);
        return getRangedIntStream(numOfPostIts).mapToObj(parse::apply)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

    private static Optional<PostIt> parseFromAbstractPostItToObject(Map<String, List<String>> postIts, int dataElementIndex) {
        if (isValidMappedObjectList(postIts)) {
            System.out.println(postIts);
            int id = Integer.parseInt(postIts.get("id").get(dataElementIndex));
            int priority = Integer.parseInt(postIts.get("priority").get(dataElementIndex));
            boolean done = Boolean.parseBoolean(postIts.get("done").get(dataElementIndex));
            String title = postIts.get("title").get(dataElementIndex);
            String text = postIts.get("text").get(dataElementIndex);
            LocalDateTime creationDate = LocalDateTime.parse(postIts.get("creationDate").get(dataElementIndex));
            LocalDateTime endDate = LocalDateTime.parse(postIts.get("endDate").get(dataElementIndex));
            Colore colore = Colore.valueOf(postIts.get("colore").get(dataElementIndex));
            return Optional.of(new PostIt(id, done, title, text, creationDate, endDate, colore, priority));
        }
        return Optional.empty();
    }

    private static IntStream getRangedIntStream(int limit) {
        return IntStream.range(0, limit);
    }

    private static boolean isValidMappedObjectList(Map<String, List<String>> postIts) {
        return postIts.values().stream().anyMatch(list -> list.size() > 0);
    }

}