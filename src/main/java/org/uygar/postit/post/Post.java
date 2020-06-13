package org.uygar.postit.post;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import org.uygar.postit.post.properties.Sort;

import java.time.LocalDateTime;

public class Post {

    private SimpleIntegerProperty id;
    private SimpleStringProperty nome;
    private SimpleObjectProperty<LocalDateTime> creationDate, lastModifiedDate;
    private SimpleObjectProperty<Sort> sortType;

    public Post(int id, String nome, LocalDateTime creationDate, LocalDateTime lastModifiedDate, Sort sortType) {
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.creationDate = new SimpleObjectProperty<>(creationDate);
        this.lastModifiedDate = new SimpleObjectProperty<>(lastModifiedDate);
        this.sortType = new SimpleObjectProperty<>(sortType);
    }

    public int getId() {
        return id.get();
    }
    public SimpleIntegerProperty idProperty() {
        return id;
    }
    public void setId(int id) {
        this.id.set(id);
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate.get();
    }
    public SimpleObjectProperty<LocalDateTime> lastModifiedDateProperty() {
        return lastModifiedDate;
    }
    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate.set(lastModifiedDate);
    }

    public Sort getSortType() {
        return sortType.get();
    }
    public SimpleObjectProperty<Sort> sortTypeProperty() {
        return sortType;
    }
    public void setSortType(Sort sortType) {
        this.sortType.set(sortType);
    }

    public String getNome() {
        return nome.get();
    }
    public SimpleStringProperty nomeProperty() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public LocalDateTime getCreationDate() {
        return creationDate.get();
    }
    public SimpleObjectProperty<LocalDateTime> creationDateProperty() {
        return creationDate;
    }
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate.set(creationDate);
    }

}