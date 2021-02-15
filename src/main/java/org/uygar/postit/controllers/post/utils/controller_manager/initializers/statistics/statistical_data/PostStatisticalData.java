package org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.statistical_data;

import org.uygar.postit.data.structures.PostItContainer;
import org.uygar.postit.post.PostIt;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PostStatisticalData {

    private final PostItContainer postItContainer;

    public PostStatisticalData(PostItContainer postItContainer) {
        this.postItContainer = postItContainer;
    }

    public long getNumberOfPostItBasedOnStateCondition(Predicate<PostIt> stateCondition) {
        return postItContainer.getSorted().stream().filter(stateCondition).count();
    }

    public Set<Integer> getCreationDateYears() {
        return postItContainer.getSorted().stream()
                .map(PostIt::getDataCreazione)
                .map(LocalDateTime::getYear)
                .collect(Collectors.toSet());
    }

    public int countPostItBasedOnCreationYear(Integer creationYear) {
        return (int) postItContainer.getSorted().stream()
                .filter(postIt -> postIt.getDataCreazione().getYear() == creationYear).count();
    }

}