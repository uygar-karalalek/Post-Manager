package org.uygar.postit.controllers.post.utils;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
        this.postIts.getList().forEach(postIt ->
                postIt.fattoProperty().addListener((observableValue, aBoolean, t1)
                        -> updatePercentages()));
    }

    private void onPostItListUpdate(ListChangeListener.Change<? extends PostIt> change) {
        while (change.next())
            if (change.wasAdded() || change.wasAdded())
                updatePercentages();
    }

    private void updatePercentages() {
        numOfDonePercentage.set(numOfPostItsDonePercentage());
        numOfUndonePercentage.set(numOfPostItsUnDonePercentage());
    }

    private double numOfPostItsDonePercentage() {
        int numOfPostIts = Math.max(numOfPostIts(), 1);
        return getNumOfDonePostIts() / numOfPostIts;
    }

    private double numOfPostItsUnDonePercentage() {
        return 1.0 - numOfPostItsDonePercentage();
    }

    private Double getNumOfDonePostIts() {
        return postIts.getList().stream()
                .filter(PostIt::isFatto).count() * 1.0;
    }

    private int numOfPostIts() {
        return postIts.getList().size();
    }

    public DoubleProperty numOfDonePercentageProperty() {
        return numOfDonePercentage;
    }
    public DoubleProperty numOfUndonePercentageProperty() {
        return numOfUndonePercentage;
    }

}