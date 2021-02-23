package org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.statistical_data;

import org.uygar.postit.data.structures.PostItContainer;
import org.uygar.postit.post.PostIt;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PostStatisticalData {

    private final PostItContainer postItContainer;

    public PostStatisticalData(PostItContainer postItContainer) {
        this.postItContainer = postItContainer;
    }

    public long getNumberOfPostItBasedOnStateCondition(Predicate<PostIt> stateCondition) {
        return getListStream().filter(stateCondition).count();
    }

    public Set<Month> getCreationDateMonths() {
        return getListStream().map(PostIt::getDataCreazione)
                .map(LocalDateTime::getMonth)
                .collect(Collectors.toSet());
    }

    public int countPostItBasedOnCreationYear(Month month) {
        return (int) getListStream().filter(postIt -> postIt.getDataCreazione().getMonth() == month).count();
    }

    public int getSumOfPostField(Function<PostIt, Integer> postItToPropertyMapping) {
        return getListStream().map(postItToPropertyMapping).reduce(0, Integer::sum);
    }

    private Stream<PostIt> getListStream() {
        return postItContainer.getList().stream();
    }

}