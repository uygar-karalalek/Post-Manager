package org.uygar.postit.post;

import javafx.beans.property.*;
import org.jetbrains.annotations.NotNull;
import org.uygar.postit.post.properties.Colore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Vector;

public class PostIt {

    // DB Order: id, priority, colore, postid, creationdate, text

    private IntegerProperty id, postFatherId, priority;
    private SimpleObjectProperty<LocalDateTime> dataCreazione, dataScadenza;
    private SimpleStringProperty titolo;
    private SimpleObjectProperty<Colore> colore;
    private SimpleStringProperty testo;
    private SimpleBooleanProperty fatto;

    public PostIt(int id, int fatherId, boolean fatto, String titolo, String testo, LocalDateTime dataCreazione, LocalDateTime dataFine, Colore colore, int priority) {
        this.id = new SimpleIntegerProperty(id);
        this.postFatherId = new SimpleIntegerProperty(fatherId);
        this.priority = new SimpleIntegerProperty(priority);
        this.fatto = new SimpleBooleanProperty(fatto);
        this.titolo = new SimpleStringProperty(titolo);
        this.testo = new SimpleStringProperty(testo);
        this.dataCreazione = new SimpleObjectProperty<>(dataCreazione);
        this.dataScadenza = new SimpleObjectProperty<>(dataFine);
        this.colore = new SimpleObjectProperty<>(colore);
    }

    public PostIt(int id, boolean fatto, String titolo, String testo, LocalDateTime dataCreazione, LocalDateTime dataFine, Colore colore, int priority) {
        this(id, -1, fatto, titolo, testo, dataCreazione, dataFine, colore, priority);
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

    public int getPostFatherId() {
        return postFatherId.get();
    }
    public IntegerProperty postFatherIdProperty() {
        return postFatherId;
    }
    public void setPostFatherId(int postFatherId) {
        this.postFatherId.set(postFatherId);
    }

    public int getPriority() {
        return priority.get();
    }
    public IntegerProperty priorityProperty() {
        return priority;
    }
    public void setPriority(int priority) {
        this.priority.set(priority);
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
    public boolean isUndone() {
        return !isFatto();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PostIt)
            return this.getDataCreazione().isEqual(((PostIt) obj).getDataCreazione());
        return false;
    }

    public static PostIt empty() {
        return new PostIt(-1, false, "", "",
                LocalDateTime.now(), LocalDateTime.now(), Colore.GIALLO, -1);
    }

}