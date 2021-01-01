package org.uygar.postit.controllers.filter.postit;

import org.uygar.postit.controllers.filter.Filter;
import org.uygar.postit.controllers.filter.FilterUnitContainer;
import org.uygar.postit.controllers.filter.GeneralFilter;
import org.uygar.postit.controllers.filter.post.PostFilter;
import org.uygar.postit.controllers.filter.unit.DateFilterUnit;
import org.uygar.postit.controllers.filter.unit.StringFilterUnit;
import org.uygar.postit.controllers.post.PostController;
import org.uygar.postit.post.PostIt;

import java.io.*;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class PostItFilter extends GeneralFilter<PostController, PostIt> implements Serializable {

    public static final long serialVersionUID = 222L;

    String contieneText, inizioText;
    LocalDate date1, date2;

    public PostItFilter(PostController controller, FilterUnitContainer<PostIt> unitContainer) {
        super(controller, unitContainer);
        setTextOfUnits();
    }

    // FOR OBJECT SERIALIZATION
    public PostItFilter() {}

    private void setTextOfUnits() {
        inizioText = getFilterController().postItTitleBegins.getText().trim();
        contieneText = getFilterController().postItTitleContains.getText().trim();

        date1 = getFilterController().postTraField1.getValue();
        date2 = getFilterController().postTraField2.getValue();

        if (date1 != null && date2 != null && date1.isAfter(date2)) {
            LocalDate temp = date1;
            date1 = date2;
            date2 = temp;
        }
    }

    @Override
    public void buildFilterSettingUnits() {
        setTextOfUnits();
        StringFilterUnit inizioUnit = new StringFilterUnit(inizioText, String::startsWith, true);
        StringFilterUnit contieneUnit = new StringFilterUnit(contieneText, String::contains, true);

        DateFilterUnit dateUnit = new DateFilterUnit(date1, date2);

        addStringFilterUnit(inizioUnit);
        addStringFilterUnit(contieneUnit);

        addDateFilterUnit(dateUnit);
    }

    @Override
    public void applyFilterToController() {
        if (notEmpty(inizioText))
            applyToField(getFilterController().postItTitleBegins, inizioText, Optional.empty());
        if (notEmpty(contieneText))
            applyToField(getFilterController().postItTitleContains, contieneText, Optional.empty());
        if (date1 != null && date2 != null)
            applyToDatePicker(getFilterController().postTraField1, getFilterController().postTraField2, date1, date2, Optional.empty());
    }

    private boolean notEmpty(String string) {
        return !string.isEmpty();
    }

    @Override
    public void addStringFilterUnit(StringFilterUnit stringFilterUnit) {
        super.addStringFilterUnit(stringFilterUnit);

        if (stringFilterUnit.isEnabled()) {
            Predicate<PostIt> postCondition = postit -> stringFilterUnit.getCondition()
                        .test(postit.getTitolo().toLowerCase(), stringFilterUnit.getInputBasedOnIgnoreCase());

            getUnitContainer().addUnit(postCondition);
        }
    }

    @Override
    public void addDateFilterUnit(DateFilterUnit dateFilterUnit) {
        super.addDateFilterUnit(dateFilterUnit);

        if (dateFilterUnit.isEnabled()) {
            BiPredicate<PostIt, LocalDate> isAfter = (post, date) -> post.getDataScadenza().toLocalDate().isAfter(date);
            BiPredicate<PostIt, LocalDate> isEqual = (post, date) -> post.getDataCreazione().toLocalDate().isEqual(date);
            BiPredicate<PostIt, LocalDate> isBefore = (post, date) -> post.getDataScadenza().toLocalDate().isBefore(date);

            getUnitContainer().addUnit(getDateFilterCondition(isEqual, isAfter, isBefore, dateFilterUnit));
        }
    }

    @Override
    public void serialize() {
        File file = new File("postit_filter.ser");
        if (file.exists())
            file.delete();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Filter<PostIt> deserialize() {
        File file = new File("postit_filter.ser");
        if (!file.exists())
            return null;
        PostItFilter filterObject = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            filterObject = (PostItFilter) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return filterObject;
    }

}