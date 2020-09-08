package org.uygar.postit.controllers.app.filter.util;

import java.io.Serializable;
import java.time.LocalDate;

public class Filter implements Serializable {

    public static final long serialVersionUID = 10000L;

    protected String inizio, contiene, finisce;
    protected Boolean ignoraMaiusc;
    protected LocalDate data1, data2;

    public Filter(String inizio, String contiene,
                            String finisce, Boolean ignoraMaiusc,
                            LocalDate data1, LocalDate data2) {
        this.inizio = inizio;
        this.contiene = contiene;
        this.finisce = finisce;
        this.ignoraMaiusc = ignoraMaiusc;
        this.data1 = data1;
        this.data2 = data2;
        swapIfFirstGreaterThanSecond();
    }

    public Filter() {}

    public void swapIfFirstGreaterThanSecond() {
        if (this.data1 == null || this.data2 == null)
            return;

        LocalDate data1Temp = this.data1;

        if (this.data1.isAfter(this.data2))
            this.data1 = this.data2;

        this.data2 = data1Temp;
    }

}