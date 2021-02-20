package org.uygar.postit.controllers.post.utils;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import org.uygar.postit.data.structures.PostItContainerOrganizer;
import org.uygar.postit.post.PostIt;

public class PostStatistics {

    private final PostItContainerOrganizer postIts;
    private final DoubleProperty numOfDonePercentage = new SimpleDoubleProperty();
    private final DoubleProperty numOfUndonePercentage = new SimpleDoubleProperty();

    public PostStatistics(PostItContainerOrganizer containerOrganizer) {
        this.postIts = containerOrganizer;
        updatePercentages();
        onPostItChanges();
    }

    private void onPostItChanges() {
        this.postIts.getList().addListener(this::onPostItListUpdate);
        this.postIts.getList().forEach(this::addDonePropertyListener);
    }

    private void onPostItListUpdate(ListChangeListener.Change<? extends PostIt> change) {
        while (change.next()) {
            updatePercentages();
            if (change.wasAdded()) {
                PostIt addedPostIt = change.getAddedSubList().get(0);
                addDonePropertyListener(addedPostIt);
            }
        }
    }

    private void addDonePropertyListener(PostIt postIt) {
        postIt.fattoProperty().addListener(this::onPostItDoneChange);
    }

    public void onPostItDoneChange(ObservableValue<? extends Boolean> obs, Boolean oldVal, Boolean newVal) {
        updatePercentages();
    }

    private void updatePercentages() {
        numOfDonePercentage.set(numOfPostItsDonePercentage());
        numOfUndonePercentage.set(numOfPostItsUnDonePercentage());
    }

    private double numOfPostItsDonePercentage() {
        return 1.0 - numOfPostItsUnDonePercentage();
    }

    private double numOfPostItsUnDonePercentage() {
        int numOfPostIts = Math.max(getNumOfPostIts(), 1);
        return getNumOfUnDonePostIts() / numOfPostIts;
    }

    private Double getNumOfUnDonePostIts() {
        return postIts.getList().stream()
                .filter(PostIt::isUndone).count() * 1.0;
    }

    private int getNumOfPostIts() {
        return postIts.getList().size();
    }

    public DoubleProperty numOfDonePercentageProperty() {
        return numOfDonePercentage;
    }
    public DoubleProperty numOfUndonePercentageProperty() {
        return numOfUndonePercentage;
    }

}