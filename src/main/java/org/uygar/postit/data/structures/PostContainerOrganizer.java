package org.uygar.postit.data.structures;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.properties.converters.ConverterUtil;
import org.uygar.postit.post.viewers.post.PostViewer;

import java.util.*;
import java.util.List;

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

}