package org.uygar.postit.data.structures;

import javafx.collections.ObservableList;
import org.jetbrains.annotations.NotNull;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.properties.Sort;
import org.uygar.postit.post.properties.converters.ConverterUtil;

import java.time.LocalDateTime;
import java.util.*;

public class PostContainerOrganizer extends BaseDataContainer<Post> implements PostContainer {

    public static final String POST_TABLE_NAME = "post";

    public PostContainerOrganizer(DataMiner dataMiner) {
        super(dataMiner);
        loadDataFromTable(POST_TABLE_NAME, Optional.empty());
    }

    @Override
    protected List<Post> parseFromAbstractSQLObject(Map<String, List<String>> posts, int numOfElements) {
        return ConverterUtil.convertSQLResponseToPost(posts, numOfElements);
    }

    public ObservableList<Post> getPostList() {
        return list;
    }

    public Post getLastPost() {
        return list.stream().max
                (Comparator.comparing(Post::getId)).orElse(null);
    }

}