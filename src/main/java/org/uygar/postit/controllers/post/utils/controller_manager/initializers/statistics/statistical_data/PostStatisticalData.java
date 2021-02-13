package org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.statistical_data;

import org.uygar.postit.data.structures.PostItContainer;
import org.uygar.postit.post.PostIt;

import java.util.function.Predicate;

public class PostStatisticalData {

    private final PostItContainer postItContainer;

    public PostStatisticalData(PostItContainer postItContainer) {
        this.postItContainer = postItContainer;
    }

    public long getNumberOfPostItBasedOnStateCondition(Predicate<PostIt> stateCondition) {
        return postItContainer.getSorted().stream().filter(stateCondition).count();
    }

}