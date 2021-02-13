package org.uygar.postit.controllers.filter.postit;

import org.uygar.postit.controllers.filter.Filter;
import org.uygar.postit.controllers.filter.FilterUnitContainer;
import org.uygar.postit.controllers.filter.GeneralFilter;
import org.uygar.postit.controllers.filter.unit.DateFilterUnit;
import org.uygar.postit.controllers.filter.unit.IntFilterUnit;
import org.uygar.postit.controllers.filter.unit.StringFilterUnit;
import org.uygar.postit.controllers.post.PostController;
import org.uygar.postit.post.PostIt;

import java.io.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class PostItFilter extends GeneralFilter<PostController, PostIt> implements Serializable {

    public static final long serialVersionUID = 222L;

    String contieneText, inizioText;
    Integer priority;
    LocalDate date1, date2;

    Boolean priorityEnabled;

    public PostItFilter(PostController controller, FilterUnitContainer<PostIt> unitContainer) {
        super(controller, unitContainer);
    }

    // FOR OBJECT SERIALIZATION
    public PostItFilter() {}

    private void setTextOfUnits() {
        priorityEnabled = !getFilterController().postItPriorityField.getText().isBlank();

        inizioText = getFilterController().postItTitleBegins.getText().trim();
        contieneText = getFilterController().postItTitleContains.getText().trim();
        priority = convertPriority();

        date1 = getFilterController().postTraField1.getValue();
        date2 = getFilterController().postTraField2.getValue();

        if (date1 != null && date2 != null && date1.isAfter(date2)) {
            LocalDate temp = date1;
            date1 = date2;
            date2 = temp;
        }
    }

    private int convertPriority() {
        String priorityText = getFilterController().postItPriorityField.getText();
        return priorityEnabled ? Integer.parseInt(priorityText) : Integer.MIN_VALUE;
    }

    @Override
    public void buildFilterSettingUnits() {
        super.resetUnitContainer();
        setTextOfUnits();
        StringFilterUnit inizioUnit = new StringFilterUnit(inizioText, String::startsWith);
        StringFilterUnit contieneUnit = new StringFilterUnit(contieneText, String::contains);
        IntFilterUnit priorityUnit = new IntFilterUnit(priority, Integer::equals, priorityEnabled);

        DateFilterUnit dateUnit = new DateFilterUnit(date1, date2);

        addStringFilterUnit(inizioUnit);
        addStringFilterUnit(contieneUnit);
        addIntFilterUnit(priorityUnit);

        addDateFilterUnit(dateUnit);
    }

    @Override
    public void applyFilterToController() {
        if (notEmpty(inizioText))
            applyToField(getFilterController().postItTitleBegins, inizioText);
        if (notEmpty(contieneText))
            applyToField(getFilterController().postItTitleContains, contieneText);
        if (priority != null)
            applyToField(getFilterController().postItPriorityField, priority.toString());
        if (date1 != null && date2 != null)
            applyToDatePicker(getFilterController().postTraField1, getFilterController().postTraField2, date1, date2);
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
    public void addIntFilterUnit(IntFilterUnit intFilterUnit) {
        super.addIntFilterUnit(intFilterUnit);

        if (intFilterUnit.isEnabled()) {
            Predicate<PostIt> postItPriorityEqualsCondition = postIt ->
                    intFilterUnit.getCondition().test(postIt.getPriority(), intFilterUnit.getInput());

            getUnitContainer().addUnit(postItPriorityEqualsCondition);
        }
    }

    @Override
    public void serialize() {
        File file = new File(getPostItFilterSerializerBasedOnPostName());
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
        File file = new File(getPostItFilterSerializerBasedOnPostName());
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

    private String getPostItFilterSerializerBasedOnPostName() {
        return "postit_filter_"+getFilterController().loadedPost.getName()+".ser";
    }

}