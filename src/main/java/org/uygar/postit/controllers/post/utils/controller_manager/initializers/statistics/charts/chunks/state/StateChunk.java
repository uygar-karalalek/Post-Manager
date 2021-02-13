package org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.chunks.state;

import javafx.scene.chart.Chart;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.StateChart;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.chunks.StatisticsChunkPane;
import org.uygar.postit.data.structures.PostItContainer;

import java.util.List;

public class StateChunk extends StatisticsChunkPane {

    public StateChunk(PostItContainer postItContainer) {
        super("State", List.of(new StateChart(postItContainer)), postItContainer);
    }

    @Override
    public void initTitleSide() {
        StackPane titleContainer = new StackPane(this.topTitle);
        titleContainer.setId("titleContainer");
        this.setTop(titleContainer);
    }

    @Override
    public void initChartSide() {
        this.centerChartBox.getChildren().addAll(this.getCharts());
        this.setCenter(this.centerChartBox);
    }

    @Override
    public void initSliderSide() {
        this.setBottom(this.bottomSizeSlider);
    }

}