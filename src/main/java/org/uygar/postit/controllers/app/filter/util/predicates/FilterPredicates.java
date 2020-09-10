package org.uygar.postit.controllers.app.filter.util.predicates;

import org.uygar.postit.controllers.app.filter.util.FilterBuilder;
import org.uygar.postit.post.Post;

import java.util.function.Predicate;

class FilterPredicates {

    FilterBuilder filterBuilder;

    private Predicate<Post> INIZIO, TRA, CONTIENE, FINISCE;

    public FilterPredicates(FilterBuilder builder) {
        this.filterBuilder = builder;
        givePredicatesAnInitialValue();
        initPreds();
    }

    private void initPreds() {
        final PredicateReferencer inizioRef = new PredicateReferencer(filterBuilder, filterBuilder.inizio, filterBuilder.inizioSelected, String::startsWith);
        final PredicateReferencer contieneRef = new PredicateReferencer(filterBuilder, filterBuilder.contiene, filterBuilder.contieneSelected, String::contains);
        final PredicateReferencer finisceRef = new PredicateReferencer(filterBuilder, filterBuilder.finisce, filterBuilder.finisceSelected, String::endsWith);

        INIZIO = inizioRef::get;
        CONTIENE = contieneRef::get;
        FINISCE = finisceRef::get;
        TRA = this::isPostCreationBetweenDates;
    }

    public boolean isPostCreationBetweenDates(Post post) {
        if (filterBuilder.traSelected) {
            boolean postCreationDateIsEqualOrAfterFirstDate =
                    post.getCreationDate().toLocalDate().isAfter(filterBuilder.data1) || post.getCreationDate().toLocalDate().isEqual(filterBuilder.data1);

            boolean postCreationDateIsBeforeOrEqualSecondDate =
                    post.getCreationDate().toLocalDate().isBefore(filterBuilder.data2) || post.getCreationDate().toLocalDate().isEqual(filterBuilder.data2);

            return postCreationDateIsEqualOrAfterFirstDate && postCreationDateIsBeforeOrEqualSecondDate;
        }
        return true;
    }

    public Predicate<Post> unified() {
        return INIZIO.and(TRA).and(CONTIENE).and(FINISCE);
    }

    public void givePredicatesAnInitialValue() {
        INIZIO = TRA = CONTIENE = FINISCE = getTruePostPredicate();
    }

    private Predicate<Post> getTruePostPredicate() {
        return post -> true;
    }

}