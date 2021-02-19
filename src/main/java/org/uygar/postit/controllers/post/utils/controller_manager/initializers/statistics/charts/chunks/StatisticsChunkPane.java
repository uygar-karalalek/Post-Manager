package org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.chunks;

import javafx.beans.value.ObservableValue;
import javafx.scene.chart.Chart;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.views.PostChartDataProcessor;
import org.uygar.postit.data.structures.PostItContainer;

import java.util.List;
import java.util.stream.Collectors;

public abstract class StatisticsChunkPane extends BorderPane implements StatisticsChunk {

    private static final int CHART_AND_FRAME_GAP = 40;

    protected Text topTitle;

    protected Pane parentPane;
    protected HBox centerChartBox;
    protected List<PostChartDataProcessor> charts;

    protected Slider bottomSizeSlider;

    protected PostItContainer postItContainer;

    public StatisticsChunkPane(String title, List<PostChartDataProcessor> charts, Pane parentPane, PostItContainer postItContainer) {
        this.topTitle = new Text(title);
        this.centerChartBox = new HBox();
        this.charts = charts;
        this.bottomSizeSlider = new Slider();
        this.parentPane = parentPane;
        this.postItContainer = postItContainer;

        this.parentPane.widthProperty().addListener(this::resizeChunkWidthOnParentWidthChange);

        initTitleSide();
        initChartSide();
        initSliderSide();
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
        this.bindChartsWidthWithChunkWidth();
        this.setCenter(this.centerChartBox);
    }

    private void resizeChunkWidthOnParentWidthChange(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
        double sizeFactor = this.getCharts().size() >= 2 ? 1 : 0.5;
        this.setPrefWidth(sizeFactor * ( (double) newVal - CHART_AND_FRAME_GAP));
    }

    private void bindChartsWidthWithChunkWidth() {
        this.getCharts().forEach(chart -> chart.prefWidthProperty().bind(this.widthProperty().divide(this.charts.size())));
    }

    @Override
    public void initSliderSide() {
        this.setBottom(this.bottomSizeSlider);
    }

    public List<Chart> getCharts() {
        return charts.stream().map(PostChartDataProcessor::getChart).collect(Collectors.toList());
    }

    public void updateCharts() {
        charts.forEach(PostChartDataProcessor::updateChartData);
    }

}