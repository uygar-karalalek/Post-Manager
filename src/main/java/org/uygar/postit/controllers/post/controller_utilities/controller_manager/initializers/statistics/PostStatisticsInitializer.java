package org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics;

import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.uygar.postit.controllers.WindowDimensions;
import org.uygar.postit.controllers.post.PostController;
import org.uygar.postit.controllers.post.controller_utilities.controller_manager.PostControllerWrapper;
import org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.TabInitializer;
import org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.manager.ChartGroupManager;

import java.util.function.Predicate;

public class PostStatisticsInitializer extends PostControllerWrapper implements TabInitializer {

    public static final String PLACEHOLDER_TEXT = "CREATE POSTS TO VIEW STATISTICS!";
    private static final double FLOW_SCROLL_STATISTICS_GAP = 10;

    private final ChartGroupManager manager;

    public PostStatisticsInitializer(PostController postController) {
        super(postController);
        this.manager = new ChartGroupManager(postController);
    }

    @Override
    public void initializeTab() {
        addStatisticalsChunksToPane();
    }

    private void addStatisticalsChunksToPane() {
        this.postController.statsPane.getChildren().addAll(manager.getChunks());
        this.postController.statsScrollPane.widthProperty().addListener(this::onStatisticsFlowPaneSizeChange);
        this.postController.rootTabPane.getSelectionModel().selectedItemProperty().addListener(this::onStatisticsChartClickedUpdateAllCharts);
    }

    private void onStatisticsFlowPaneSizeChange(ObservableValue<? extends Number> obs, Number oldNum, Number newNum) {
        this.postController.statsPane.setPrefWidth((double) newNum - FLOW_SCROLL_STATISTICS_GAP);
    }

    private void onStatisticsChartClickedUpdateAllCharts(ObservableValue<? extends Tab> obs, Tab oldTab, Tab newTab) {
        if (newTab.getText().equalsIgnoreCase("statistics")) {
            if (this.manager.isEmpty())
                this.fillStatisticalPanel();
            if (this.manager.thereAreZeroPostIts())
                this.emptyStatisticalPanel();
            this.manager.updateAllCharts();
        }
    }

    private void fillStatisticalPanel() {
        this.manager.addChunksToManagerGroupIfAlmostOnePostIt();
        this.postController.statsPane.getChildren().addAll(this.manager.getChunks());
        this.removePlaceHolder();
    }

    private void emptyStatisticalPanel() {
        this.postController.statsPane.getChildren().setAll();
        this.addPlaceHolder();
    }

    private void addPlaceHolder() {
        Text placeHolder = new Text(PLACEHOLDER_TEXT);

        StackPane placeHolderContainer = new StackPane(placeHolder);

        placeHolderContainer.prefWidthProperty().bind(this.postController.statsPane.widthProperty());
        placeHolderContainer.setPrefHeight(WindowDimensions.POST_WINDOW_DIMENSION.getHeight() / 2);

        this.postController.statsPane.getChildren().add(placeHolderContainer);
    }

    private void removePlaceHolder() {
        Predicate<Node> removeConditions = statsNode ->
                statsNode instanceof Text &&
                        ((Text) statsNode).getText().equals(PLACEHOLDER_TEXT);

        this.postController.statsPane.getChildren().removeIf(removeConditions);
    }

}