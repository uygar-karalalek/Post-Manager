package org.uygar.postit.controllers.application.filter.util;

import org.uygar.postit.controllers.application.filter.util.predicates.FilterPredicates;
import org.uygar.postit.post.Post;

import java.time.LocalDate;
import java.util.function.Predicate;

public class FilterBuilder extends Filter {

    FilterPredicates predicates;

    public boolean inizioSelected, traSelected, contieneSelected, finisceSelected;

    public FilterBuilder(String inizio, String contiene, String finisce,
                         LocalDate data1, LocalDate data2, boolean ignoraMaiusc, boolean inizioSelected,
                         boolean traSelected, boolean contieneSelected, boolean finisceSelected) {
        super(inizio, contiene, finisce, ignoraMaiusc, data1, data2);
        this.inizioSelected = inizioSelected;
        this.traSelected = traSelected;
        this.contieneSelected = contieneSelected;
        this.finisceSelected = finisceSelected;

        this.predicates = new FilterPredicates(this);
    }

    public Predicate<Post> unifiedPredicates() {
        return predicates.unified();
    }

}