package org.uygar.postit.post;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import org.uygar.postit.post.properties.Sort;

import java.time.LocalDateTime;

public class Post {

    /**
     * Order: id, creationDate, name, sort, lastModifiedDate
    */

    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleObjectProperty<LocalDateTime> creationDate, lastModifiedDate;
    private SimpleObjectProperty<Sort> sortType;

    public Post(Integer id, String name, LocalDateTime creationDate, LocalDateTime lastModifiedDate, Sort sortType) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.creationDate = new SimpleObjectProperty<>(creationDate);
        this.lastModifiedDate = new SimpleObjectProperty<>(lastModifiedDate);
        this.sortType = new SimpleObjectProperty<>(sortType);
    }

    public Post(Integer id, String name, LocalDateTime creationDate, LocalDateTime lastModifiedDate) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.creationDate = new SimpleObjectProperty<>(creationDate);
        this.lastModifiedDate = new SimpleObjectProperty<>(lastModifiedDate);
        this.sortType = new SimpleObjectProperty<>(Sort.DONE);
    }

    public Post(Integer id, String name, LocalDateTime creationAndLastModified) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.creationDate = new SimpleObjectProperty<>(creationAndLastModified);
        this.lastModifiedDate = new SimpleObjectProperty<>(creationAndLastModified);
        this.sortType = new SimpleObjectProperty<>(Sort.DONE);
    }

    public Post(Integer id, String name, LocalDateTime creationAndLastModified, Sort sortType) {
        this.id = new SimpleIntegerProperty();
        this.id.setValue(id);
        this.name = new SimpleStringProperty(name);
        this.creationDate = new SimpleObjectProperty<>(creationAndLastModified);
        this.lastModifiedDate = new SimpleObjectProperty<>(creationAndLastModified);
        this.sortType = new SimpleObjectProperty<>(sortType);
    }

    public Post(Integer id, String name) {
        this.id = new SimpleIntegerProperty();
        this.id.setValue(id);
        this.name = new SimpleStringProperty(name);
        this.creationDate = new SimpleObjectProperty<>(LocalDateTime.now());
        this.lastModifiedDate = new SimpleObjectProperty<>(LocalDateTime.now());
        this.sortType = new SimpleObjectProperty<>(Sort.DONE);
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

    public String getName() {
        return name.get();
    }
    public SimpleStringProperty nameProperty() {
        return name;
    }
    public void setName(String name) {
        this.name.set(name);
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