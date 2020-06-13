package org.uygar.postit.post;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import org.uygar.postit.post.properties.Colore;

import java.time.LocalDateTime;

public class PostIt {

    private IntegerProperty id;
    private SimpleObjectProperty<LocalDateTime> dataCreazione;
    private SimpleObjectProperty<Colore> colore;
    private SimpleStringProperty testo;
    private SimpleObjectProperty<Post> padre;

    public PostIt(int id, LocalDateTime dataCreazione, Colore colore, String testo, Post padre) {
        this.id = new SimpleIntegerProperty(id);
        this.dataCreazione = new SimpleObjectProperty<>(dataCreazione);
        this.colore = new SimpleObjectProperty<>(colore);
        this.testo = new SimpleStringProperty(testo);
        this.padre = new SimpleObjectProperty<>(padre);
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

    public LocalDateTime getDataCreazione() {
        return dataCreazione.get();
    }
    public SimpleObjectProperty<LocalDateTime> dataCreazioneProperty() {
        return dataCreazione;
    }
    public void setDataCreazione(LocalDateTime dataCreazione) {
        this.dataCreazione.set(dataCreazione);
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

    public String getTesto() {
        return testo.get();
    }
    public SimpleStringProperty testoProperty() {
        return testo;
    }
    public void setTesto(String testo) {
        this.testo.set(testo);
    }

    public Post getPadre() {
        return padre.get();
    }
    public SimpleObjectProperty<Post> padreProperty() {
        return padre;
    }
    public void setPadre(Post padre) {
        this.padre.set(padre);
    }

}