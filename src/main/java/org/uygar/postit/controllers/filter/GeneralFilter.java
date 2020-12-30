package org.uygar.postit.controllers.filter;

import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.jetbrains.annotations.NotNull;
import org.uygar.postit.controllers.BaseController;
import org.uygar.postit.controllers.filter.unit.DateFilterUnit;
import org.uygar.postit.controllers.filter.unit.StringFilterUnit;
import org.uygar.postit.post.Post;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.function.Predicate;

public abstract class GeneralFilter<FILTER_TYPE extends BaseController, FILTER_UNIT> implements Filter<FILTER_UNIT> {

    private FILTER_TYPE filterController;
    private final FilterUnitContainer<FILTER_UNIT> unitContainer;

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
        if (stringFilterUnit.isDisabled()) {
            getUnitContainer().addUnit(alwaysTrue());
        }
    }

    @Override
    public void addDateFilterUnit(DateFilterUnit dateFilterUnit) {
        if (dateFilterUnit.isDisabled()) {
            getUnitContainer().addUnit(alwaysTrue());
        }
    }

    @NotNull
    private Predicate<FILTER_UNIT> alwaysTrue() {
        return alwaysTrue -> true;
    }

    protected boolean isSelected(CheckBox checkBox) {
        return checkBox.isSelected();
    }

    protected void applyToField(TextField field, String text, CheckBox relatedBox) {
        relatedBox.setSelected(true);
        field.setText(text);
    }

    protected void applyToDatePicker(DatePicker picker1, DatePicker picker2,
                                     LocalDate value1, LocalDate value2, CheckBox relatedBox) {
        picker1.setValue(value1);
        picker2.setValue(value2);
        relatedBox.setSelected(true);
    }

}