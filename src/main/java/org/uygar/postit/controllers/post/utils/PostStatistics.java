package org.uygar.postit.controllers.post.utils;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.statistical_data.PostStatisticalData;
import org.uygar.postit.data.structures.PostItContainerOrganizer;
import org.uygar.postit.post.PostIt;

public class PostStatistics {

    private final PostItContainerOrganizer postIts;
    private final PostStatisticalData postStatisticalData;
    private final DoubleProperty numOfDonePercentage = new SimpleDoubleProperty();
    private final DoubleProperty numOfUndonePercentage = new SimpleDoubleProperty();

    public PostStatistics(PostItContainerOrganizer containerOrganizer) {
        this.postIts = containerOrganizer;
        this.postStatisticalData = new PostStatisticalData(this.postIts);
        updatePercentages();
        onPostItChanges();
    }

    private void onPostItChanges() {
        this.postIts.getObservableList().addListener(this::onPostItListUpdate);
        this.postIts.getObservableList().forEach(this::addDonePropertyListener);
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
        return postStatisticalData.
                getNumberOfPostItBasedOnStateCondition(PostIt::isUndone) * 1.0;
    }

    private int getNumOfPostIts() {
        return postIts.getObservableList().size();
    }

    public DoubleProperty numOfDonePercentageProperty() {
        return numOfDonePercentage;
    }
    public DoubleProperty numOfUndonePercentageProperty() {
        return numOfUndonePercentage;
    }

}