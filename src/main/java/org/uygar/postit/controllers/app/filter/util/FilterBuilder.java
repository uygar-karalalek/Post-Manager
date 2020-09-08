package org.uygar.postit.controllers.app.filter.util;

import org.uygar.postit.post.Post;

import java.time.LocalDate;
import java.util.function.Predicate;

public class FilterBuilder extends Filter {

    private Predicate<Post> inizioPredicate, traPredicate, contienePredicate, finiscePredicate;
    private boolean inizioSelected, traSelected, contieneSelected, finisceSelected;

    public FilterBuilder(String inizio, String contiene, String finisce,
                         LocalDate data1, LocalDate data2, boolean ignoraMaiusc, boolean inizioSelected,
                         boolean traSelected, boolean contieneSelected, boolean finisceSelected) {

        super(inizio, contiene, finisce, ignoraMaiusc, data1, data2);

        this.inizioSelected = inizioSelected;
        this.traSelected = traSelected;
        this.contieneSelected = contieneSelected;
        this.finisceSelected = finisceSelected;

        buildPredicates();
    }

    public Predicate<Post> getMergedPostPredicates() {
        return inizioPredicate.and(traPredicate).and(contienePredicate).and(finiscePredicate);
    }

    private void buildPredicates() {
        inizioPredicate = traPredicate = contienePredicate
                = finiscePredicate = getTruePostPredicate();

        initInizioPredicate();
        initTraPredicate();
        initContienePredicate();
        initFiniscePredicate();
    }

    private void initInizioPredicate() {
        if (inizioSelected && ignoraMaiusc)
            inizioPredicate = post -> post.getName().toLowerCase().indexOf(inizio.toLowerCase()) == 0;

        else if (inizioSelected)
            inizioPredicate = post -> post.getName().indexOf(inizio) == 0;
    }

    private void initTraPredicate() {
        if (traSelected)
            traPredicate = this::isPostCreationBetweenDates;
    }

    private void initContienePredicate() {
        if (contieneSelected && ignoraMaiusc)
            contienePredicate = post -> post.getName().toLowerCase().contains(contiene.toLowerCase());

        else if (contieneSelected)
            contienePredicate = post -> post.getName().contains(contiene);
    }

    private void initFiniscePredicate() {
        if (finisceSelected && ignoraMaiusc)
            finiscePredicate = post -> post.getName().toLowerCase().endsWith(finisce.toLowerCase());

        else if (finisceSelected)
            finiscePredicate = post -> post.getName().endsWith(finisce);
    }

    private Predicate<Post> getTruePostPredicate() {
        return post -> true;
    }

    public boolean isPostCreationBetweenDates(Post post) {
        boolean postCreationDateIsEqualOrAfterFirstDate = post.getCreationDate().toLocalDate().isAfter(data1) || post.getCreationDate().toLocalDate().isEqual(data1);
        boolean postCreationDateIsBeforeOrEqualSecondDate = post.getCreationDate().toLocalDate().isBefore(data2) && post.getCreationDate().toLocalDate().isEqual(data2);

        return postCreationDateIsEqualOrAfterFirstDate && postCreationDateIsBeforeOrEqualSecondDate;
    }

}