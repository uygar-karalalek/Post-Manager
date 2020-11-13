package org.uygar.postit.post;

import javafx.beans.property.*;
import org.jetbrains.annotations.NotNull;
import org.uygar.postit.post.properties.Colore;

import java.time.LocalDateTime;

public class PostIt {

    private IntegerProperty id;
    private SimpleObjectProperty<LocalDateTime> dataCreazione, dataScadenza;
    private SimpleStringProperty titolo;
    private SimpleObjectProperty<Colore> colore;
    private SimpleStringProperty testo;
    private SimpleBooleanProperty fatto;

    public PostIt(int id, boolean fatto, String titolo, String testo, LocalDateTime dataCreazione, LocalDateTime dataFine, Colore colore) {
        this.id = new SimpleIntegerProperty(id);
        this.fatto = new SimpleBooleanProperty(fatto);
        this.titolo = new SimpleStringProperty(titolo);
        this.testo = new SimpleStringProperty(testo);
        this.dataCreazione = new SimpleObjectProperty<>(dataCreazione);
        this.dataScadenza = new SimpleObjectProperty<>(dataFine);
        this.colore = new SimpleObjectProperty<>(colore);
    }

    public int getId() {
        return id.get();
    }
    public IntegerProperty idProperty() {
        return id;
    }
    public void setId(int id) {
        this.id.set(id);
    }

    public boolean isFatto() {
        return fatto.get();
    }
    public SimpleBooleanProperty fattoProperty() {
        return fatto;
    }
    public void setFatto(boolean fatto) {
        this.fatto.set(fatto);
    }

    public String getTitolo() {
        return titolo.get();
    }
    public SimpleStringProperty titoloProperty() {
        return titolo;
    }
    public void setTitolo(String titolo) {
        this.titolo.set(titolo);
    }

    public String getTesto() {
        return testo.get();
    }
    public SimpleStringProperty testoProperty() {
        return testo;
    }
    public void setTesto(String testo) {
        this.testo.set(testo);
    }

    public LocalDateTime getDataCreazione() {
        return dataCreazione.get();
    }
    public SimpleObjectProperty<LocalDateTime> dataCreazioneProperty() {
        return dataCreazione;
    }
    public void setDataCreazione(LocalDateTime dataCreazione) {
        this.dataCreazione.set(dataCreazione);
    }

    public LocalDateTime getDataScadenza() {
        return dataScadenza.get();
    }
    public SimpleObjectProperty<LocalDateTime> dataScadenzaProperty() {
        return dataScadenza;
    }
    public void setDataScadenza(LocalDateTime dataScadenza) {
        this.dataScadenza.set(dataScadenza);
    }

    public Colore getColore() {
        return colore.get();
    }
    public SimpleObjectProperty<Colore> coloreProperty() {
        return colore;
    }
    public void setColore(Colore colore) {
        this.colore.set(colore);
    }

    public boolean isExpired() {
       return LocalDateTime.now().isAfter(dataScadenza.get());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PostIt)
            return this.getDataCreazione().isEqual(((PostIt) obj).getDataCreazione());
        return false;
    }

    public int compareToDone(@NotNull PostIt postIt) {
        if (isFatto() && !postIt.isFatto())
            return 1;
        if (!isFatto() && postIt.isFatto())
            return -1;
        return 0;
    }

    public int compareToUndone(@NotNull PostIt postIt) {
        return compareToDone(postIt) * - 1;
    }

}