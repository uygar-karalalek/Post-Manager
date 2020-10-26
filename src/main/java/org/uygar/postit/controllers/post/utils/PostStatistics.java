package org.uygar.postit.controllers.post.utils;

import org.uygar.postit.data.structures.PostItContainerOrganizer;
import org.uygar.postit.post.PostIt;

public class PostStatistics {

    private PostItContainerOrganizer postIts;

    public PostStatistics(PostItContainerOrganizer containerOrganizer) {
        this.postIts = containerOrganizer;
    }

    public double getNumOfDonePercentage() {
        return getNumOfDonePostIts() * 1.0 / postIts.getList().size();
    }

    public double getNumOfUndonePercentage() {
        return getNumOfUndonePostIts() * 1.0 / postIts.getList().size();
    }

    private Long getNumOfDonePostIts() {
        return postIts.getList().stream()
                .filter(PostIt::isFatto).count();
    }

    private Long getNumOfUndonePostIts() {
        long numOfDone = getNumOfDonePostIts();
        return postIts.getList().size() - numOfDone;
    }

}