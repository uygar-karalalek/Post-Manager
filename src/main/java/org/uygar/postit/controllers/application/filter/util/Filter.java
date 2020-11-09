package org.uygar.postit.controllers.application.filter.util;

import java.io.Serializable;
import java.time.LocalDate;

public class Filter implements Serializable {

    public static final long serialVersionUID = 10000L;

    public String inizio, contiene, finisce;
    public Boolean ignoraMaiusc;
    public LocalDate data1, data2;

    public Filter(String inizio, String contiene,
                            String finisce, Boolean ignoraMaiusc,
                            LocalDate data1, LocalDate data2) {
        this.inizio = inizio;
        this.contiene = contiene;
        this.finisce = finisce;
        this.ignoraMaiusc = ignoraMaiusc;
        this.data1 = data1;
        this.data2 = data2;

        // Swap if first is greater than second
        if (data1 != null && data1.isAfter(data2)) {
            this.data1 = data2;
            this.data2 = data1;
        }
    }

    // This constructor is used by java IO
    public Filter() {}

}