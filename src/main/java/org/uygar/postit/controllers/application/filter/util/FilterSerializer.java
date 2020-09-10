package org.uygar.postit.controllers.application.filter.util;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import org.uygar.postit.controllers.application.filter.FilterController;

import java.io.*;
import java.time.LocalDate;

public class FilterSerializer extends Filter implements Serializable {

    public static final long serialVersionUID = 10000L;

    public FilterSerializer(String inizio, String contiene,
                            String finisce, Boolean ignoraMaiusc,
                            LocalDate data1, LocalDate data2) {
        super(inizio, contiene, finisce, ignoraMaiusc, data1, data2);
    }

    public void applyFilter(FilterController controller) {
        if (this.inizio != null)
            applyToField(controller.inizioField, inizio, controller.inizio);
        if (this.contiene != null)
            applyToField(controller.contieneField, contiene, controller.contiene);
        if (this.finisce != null)
            applyToField(controller.finisceField, finisce, controller.finisce);
        if (dateNotNull())
            applyToDatePicker(controller);
        if (ignoraMaiusc != null)
            controller.ignoraMaiusc.setSelected(this.ignoraMaiusc);
    }

    public FilterSerializer() {}

    private boolean dateNotNull() {
        return data1 != null && data2 != null;
    }

    private void applyToDatePicker(FilterController controller) {
        controller.data1.setValue(data1);
        controller.data2.setValue(data2);
        controller.tra.setSelected(true);
    }

    private void applyToField(TextField field, String text, CheckBox relatedBox) {
        relatedBox.setSelected(true);
        field.setText(text);
    }

    public static void serialize(FilterSerializer filterObject) {
        File file = new File("filter.ser");
        if (file.exists())
            file.delete();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(filterObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FilterSerializer deserialize() {
        File file = new File("filter.ser");
        if (!file.exists())
            return null;
        FilterSerializer filterObject = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            filterObject = (FilterSerializer) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return filterObject;
    }

}