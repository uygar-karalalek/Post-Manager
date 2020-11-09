package org.uygar.postit.controllers.application.filter.util.predicates;

import org.uygar.postit.controllers.application.filter.util.FilterBuilder;
import org.uygar.postit.post.Post;

import java.util.function.Predicate;

public class FilterPredicates {

    FilterBuilder filterBuilder;

    private Predicate<Post> INIZIA, TRA, CONTIENE, FINISCE;

    public FilterPredicates(FilterBuilder builder) {
        this.filterBuilder = builder;
        givePredicatesAnInitialValue();
        initPreds();
    }

    private void initPreds() {
        PredicateReferencer iniziaCon = new PredicateReferencer(filterBuilder, filterBuilder.inizio, filterBuilder.inizioSelected, String::startsWith);
        PredicateReferencer contiene = new PredicateReferencer(filterBuilder, filterBuilder.contiene, filterBuilder.contieneSelected, String::contains);
        PredicateReferencer finisceCon = new PredicateReferencer(filterBuilder, filterBuilder.finisce, filterBuilder.finisceSelected, String::endsWith);

        INIZIA = iniziaCon::getResult;
        CONTIENE = contiene::getResult;
        FINISCE = finisceCon::getResult;
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

    public Predicate<Post> checkAllConditions() {
        return INIZIA.and(TRA).and(CONTIENE).and(FINISCE);
    }

    public void givePredicatesAnInitialValue() {
        INIZIA = TRA = CONTIENE = FINISCE = getTruePostPredicate();
    }

    private Predicate<Post> getTruePostPredicate() {
        return post -> true;
    }

}