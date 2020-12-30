package org.uygar.postit.controllers.filter.unit;

import java.time.LocalDate;

public class DateFilterUnit extends FilterUnit {

    private final LocalDate date1;
    private final LocalDate date2;

    public DateFilterUnit(boolean enabled, LocalDate date1, LocalDate date2) {
        super(enabled);
        this.date1 = date1;
        this.date2 = date2;
    }

    public LocalDate getDate1() {
        return date1;
    }

    public LocalDate getDate2() {
        return date2;
    }

}