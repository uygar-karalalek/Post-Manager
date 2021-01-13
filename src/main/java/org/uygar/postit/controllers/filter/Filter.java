package org.uygar.postit.controllers.filter;

import org.uygar.postit.controllers.filter.unit.DateFilterUnit;
import org.uygar.postit.controllers.filter.unit.IntFilterUnit;
import org.uygar.postit.controllers.filter.unit.StringFilterUnit;

import java.io.Serializable;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public interface Filter<T> {

    void buildFilterSettingUnits();
    void applyFilterToController();
    void addDateFilterUnit(DateFilterUnit dateFilterUnit);
    void addStringFilterUnit(StringFilterUnit filterUnit);
    void addIntFilterUnit(IntFilterUnit filterUnit);

    void serialize();
    Filter<T> deserialize();

    Predicate<T> getResult();

}