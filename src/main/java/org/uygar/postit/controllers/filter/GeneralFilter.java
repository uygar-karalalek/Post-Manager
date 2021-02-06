package org.uygar.postit.controllers.filter;

import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.jetbrains.annotations.NotNull;
import org.uygar.postit.controllers.BaseController;
import org.uygar.postit.controllers.filter.unit.DateFilterUnit;
import org.uygar.postit.controllers.filter.unit.FilterUnit;
import org.uygar.postit.controllers.filter.unit.IntFilterUnit;
import org.uygar.postit.controllers.filter.unit.StringFilterUnit;
import org.uygar.postit.post.Post;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public abstract class GeneralFilter<FILTER_TYPE extends BaseController, FILTER_UNIT> implements Filter<FILTER_UNIT> {

    private FILTER_TYPE filterController;
    private FilterUnitContainer<FILTER_UNIT> unitContainer;

    public GeneralFilter(FILTER_TYPE controller, FilterUnitContainer<FILTER_UNIT> unitContainer) {
        this.filterController = controller;
        this.unitContainer = unitContainer;
    }

    public GeneralFilter() {
        this.unitContainer = new FilterUnitContainer<>();
    }

    public void setFilterController(FILTER_TYPE filterController) {
        this.filterController = filterController;
    }

    public FILTER_TYPE getFilterController() {
        return filterController;
    }

    public FilterUnitContainer<FILTER_UNIT> getUnitContainer() {
        return unitContainer;
    }

    @Override
    public Predicate<FILTER_UNIT> getResult() {
        return unitContainer.getFinalResult();
    }

    @Override
    public void addStringFilterUnit(StringFilterUnit stringFilterUnit) {
        addTrueStatementToTheContainerIfUnitDisabled(stringFilterUnit);
    }

    @Override
    public void addDateFilterUnit(DateFilterUnit dateFilterUnit) {
        addTrueStatementToTheContainerIfUnitDisabled(dateFilterUnit);
    }

    @Override
    public void addIntFilterUnit(IntFilterUnit intFilterUnit) {
        addTrueStatementToTheContainerIfUnitDisabled(intFilterUnit);
    }

    public void addTrueStatementToTheContainerIfUnitDisabled(FilterUnit unit) {
        if (unit.isDisabled())
            getUnitContainer().addUnit(alwaysTrue());
    }

    public Predicate<FILTER_UNIT> getDateFilterCondition(BiPredicate<FILTER_UNIT, LocalDate> isEqual, BiPredicate<FILTER_UNIT,
            LocalDate> isAfter, BiPredicate<FILTER_UNIT, LocalDate> isBefore, DateFilterUnit dateFilterUnit) {
        return unit -> {
            boolean dateIsEqualOrAfterFirstDate =
                    isAfter.test(unit, dateFilterUnit.getDate1()) || isEqual.test(unit, dateFilterUnit.getDate1());

            boolean dateIsBeforeOrEqualSecondDate =
                    isBefore.test(unit, dateFilterUnit.getDate2()) || isEqual.test(unit, dateFilterUnit.getDate2());

            return dateIsEqualOrAfterFirstDate && dateIsBeforeOrEqualSecondDate;
        };
    }

    @NotNull
    private Predicate<FILTER_UNIT> alwaysTrue() {
        return alwaysTrue -> true;
    }

    protected void applyToField(TextField field, String text) {
        field.setText(text);
    }

    protected void applyToDatePicker(DatePicker picker1, DatePicker picker2,
                                     LocalDate value1, LocalDate value2) {
        picker1.setValue(value1);
        picker2.setValue(value2);
    }

    public void resetUnitContainer() {
        this.unitContainer = new FilterUnitContainer<>();
    }

}