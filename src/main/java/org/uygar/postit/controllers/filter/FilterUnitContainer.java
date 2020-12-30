package org.uygar.postit.controllers.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FilterUnitContainer<T> {

    private final List<Predicate<T>> units = new ArrayList<>();

    public void addUnit(Predicate<T> unit) {
        this.units.add(unit);
    }

    public Predicate<T> getFinalResult() {
        Predicate<T> result = element -> true;
        for (Predicate<T> element : units)
            result = result.and(element);
        return result;
    }

}