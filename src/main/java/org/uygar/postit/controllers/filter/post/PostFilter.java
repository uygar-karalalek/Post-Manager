package org.uygar.postit.controllers.filter.post;

import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.jetbrains.annotations.NotNull;
import org.uygar.postit.controllers.application.filter.FilterController;
import org.uygar.postit.controllers.filter.Filter;
import org.uygar.postit.controllers.filter.FilterUnitContainer;
import org.uygar.postit.controllers.filter.GeneralFilter;
import org.uygar.postit.controllers.filter.unit.DateFilterUnit;
import org.uygar.postit.controllers.filter.unit.StringFilterUnit;
import org.uygar.postit.post.Post;

import java.io.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class PostFilter extends GeneralFilter<FilterController, Post> implements Serializable {

    public static final long serialVersionUID = 111L;

    Boolean inizioEnabled, isIgnoreCase, finisceEnabled, contieneEnabled, datesEnabled;

    String contieneText, inizioText, finisceText;

    LocalDate date1, date2;

    public PostFilter(FilterController controller, FilterUnitContainer<Post> unitContainer) {
        super(controller, unitContainer);
        setTextOfUnits();
    }

    // FOR OBJECT SERIALIZATION
    public PostFilter() {
    }

    private void setTextOfUnits() {
        inizioEnabled = isSelected(getFilterController().inizio);
        isIgnoreCase = getFilterController().ignoraMaiusc.isSelected();
        finisceEnabled = isSelected(getFilterController().finisce);
        contieneEnabled = isSelected(getFilterController().contiene);
        datesEnabled = isSelected(getFilterController().tra);

        contieneText = trimmedTextOfField(getFilterController().contieneField);
        inizioText = trimmedTextOfField(getFilterController().inizioField);
        finisceText = trimmedTextOfField(getFilterController().finisceField);
        date1 = getFilterController().data1.getValue();
        date2 = getFilterController().data2.getValue();

        if (date1 != null && date2 != null && date1.isAfter(date2)) {
            LocalDate temp = date1;
            date1 = date2;
            date2 = temp;
        }
    }

    public String trimmedTextOfField(TextField textField) {
        if (textField.isDisabled()) {
            textField.setDisable(false);
            String text = textField.getText();
            textField.setDisable(true);
            return textField.getText() != null ? text.trim() : "";
        }
        return textField.getText() != null ? textField.getText().trim() : "";
    }

    @Override
    public void buildFilterSettingUnits() {
        setTextOfUnits();
        StringFilterUnit inizio = new StringFilterUnit(inizioText, String::startsWith, isIgnoreCase, inizioEnabled);
        StringFilterUnit contiene = new StringFilterUnit(contieneText, String::contains, isIgnoreCase, contieneEnabled);
        StringFilterUnit finisce = new StringFilterUnit(finisceText, String::endsWith, isIgnoreCase, finisceEnabled);
        DateFilterUnit tra = new DateFilterUnit(datesEnabled, date1, date2);

        addStringFilterUnit(inizio);
        addStringFilterUnit(finisce);
        addStringFilterUnit(contiene);
        addDateFilterUnit(tra);
    }

    @Override
    public void applyFilterToController() {
        applyToField(getFilterController().inizioField, inizioText);
        applyToField(getFilterController().contieneField, contieneText);
        applyToField(getFilterController().finisceField, finisceText);
        applyToDatePicker(getFilterController().data1, getFilterController().data2, date1, date2);
        getFilterController().ignoraMaiusc.setSelected(isIgnoreCase);
    }

    @Override
    public void addStringFilterUnit(StringFilterUnit stringFilterUnit) {
        super.addStringFilterUnit(stringFilterUnit);

        if (stringFilterUnit.isEnabled()) {
            Function<Post, String> postName = post -> stringFilterUnit.isIgnoreCase() ?
                    post.getName().toLowerCase() : post.getName();
            Predicate<Post> postCondition = post -> stringFilterUnit.getCondition()
                    .test(postName.apply(post), stringFilterUnit.getInputBasedOnIgnoreCase());

            getUnitContainer().addUnit(postCondition);
        }
    }

    @Override
    public void addDateFilterUnit(DateFilterUnit dateFilterUnit) {
        super.addDateFilterUnit(dateFilterUnit);

        if (dateFilterUnit.isEnabled()) {
            BiPredicate<Post, LocalDate> isEqual = (post, date) -> post.getCreationDate().toLocalDate().isEqual(date);
            BiPredicate<Post, LocalDate> isAfter = (post, date) -> post.getCreationDate().toLocalDate().isAfter(date);
            BiPredicate<Post, LocalDate> isBefore = (post, date) -> post.getCreationDate().toLocalDate().isBefore(date);

            getUnitContainer().addUnit(getDateFilterCondition(isEqual, isAfter, isBefore, dateFilterUnit));
        }
    }

    protected boolean isSelected(CheckBox checkBox) {
        return checkBox.isSelected();
    }

    @Override
    public void serialize() {
        File file = new File("filter.ser");
        if (file.exists())
            file.delete();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Filter<Post> deserialize() {
        File file = new File("filter.ser");
        if (!file.exists())
            return null;
        PostFilter filterObject = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            filterObject = (PostFilter) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return filterObject;
    }

}