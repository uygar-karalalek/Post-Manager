package org.uygar.postit.post;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;

public class Post {

    private SimpleStringProperty nome;
    private SimpleObjectProperty<LocalDateTime> creationDate;

    public Post(String nome, LocalDateTime creationDate) {
        this.nome = new SimpleStringProperty(nome);
        this.creationDate = new SimpleObjectProperty<>(creationDate);
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