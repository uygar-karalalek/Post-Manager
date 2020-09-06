package org.uygar.postit.controllers.app.statistica.utility.grafico;

import org.uygar.postit.data.properties.LogProperties;

import java.util.Collection;

public interface Statistica {

    int getModa();

    int getMin();

    int getMax();

    double getMedia();

    enum TipoGrafico {
        ORE, MESI;

        public Collection<Integer> getFrequence(LogProperties properties) {

            if (this == ORE)
                return properties.getHoursAndFrequencies().values();
            else if (this == MESI)
                return properties.getMonthsAndFrequencies().values();

            return null;
        }
    }

}