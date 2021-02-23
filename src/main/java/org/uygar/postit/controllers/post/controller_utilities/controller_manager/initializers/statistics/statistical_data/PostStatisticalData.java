package org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.statistical_data;

import org.uygar.postit.data.structures.PostItContainer;
import org.uygar.postit.post.PostIt;
import org.uygar.postit.post.properties.Colore;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
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

    public Map<String, Integer> getCreationDateMonthsAndValues() {
        return getListStream().map(PostIt::getDataCreazione)
                .map(LocalDateTime::getMonth).distinct()
                .collect(Collectors.toMap(Month::toString,
                        month -> countPostItBasedOnFieldAndValue(
                                postIt -> postIt.getDataCreazione().getMonth(),
                                month)));
    }

    private  <T> int countPostItBasedOnFieldAndValue(Function<PostIt, T> postItFieldFunction, T item) {
        return (int) getListStream().filter(postIt -> postItFieldFunction.apply(postIt) == item).count();
    }

    public int getSumOfPostField(Function<PostIt, Integer> postItToPropertyMapping) {
        return getListStream().map(postItToPropertyMapping).reduce(0, Integer::sum);
    }

    public Map<String, Integer> getColorCounts() {
        return getListStream().map(PostIt::getColore).distinct().collect(Collectors.toMap(Colore::toString,
                colore -> countPostItBasedOnFieldAndValue(PostIt::getColore, colore)));
    }

    private Stream<PostIt> getListStream() {
        return postItContainer.getList().stream();
    }

}