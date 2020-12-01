package org.uygar.postit.data.structures;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.PostIt;
import org.uygar.postit.post.properties.Sort;
import org.uygar.postit.post.properties.converters.ConverterUtil;

import java.util.*;

public class PostItContainerOrganizer extends BaseDataContainer<PostIt> implements PostItContainer {

    private final Post fatherPost;
    public static final String POST_IT_TABLE_NAME = "postit";

    public PostItContainerOrganizer(Post fatherPost, DataMiner dataMiner) {
        super(dataMiner);
        this.fatherPost = fatherPost;
        loadDataFromTable(POST_IT_TABLE_NAME, Optional.of("postId=" + fatherPost.getId()));
    }

    @Override
    protected List<PostIt> parseFromAbstractSQLObject(Map<String, List<String>> stringMappedObj, int numOfElements) {
        return ConverterUtil.convertSQLResponseToPostIt(stringMappedObj, numOfElements);
    }

    @Override
    public void sortPostIts() {
        Sort sort = fatherPost.getSortType();
        sort.sort(list);
    }

    @Override
    public List<PostIt> getSorted() {
        sortPostIts();
        return list;
    }

    public Sort getSortType() {
        return this.fatherPost.getSortType();
    }

    public Post getFatherPost() {
        return fatherPost;
    }

}