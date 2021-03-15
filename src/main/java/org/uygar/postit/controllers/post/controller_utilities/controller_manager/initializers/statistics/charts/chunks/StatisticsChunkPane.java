package org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.chunks;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.views.PostChartDataProcessor;
import org.uygar.postit.data.structures.PostItContainer;

import java.util.List;
import java.util.stream.Collectors;

public abstract class StatisticsChunkPane extends BorderPane implements StatisticsChunk {

    private static final int CHART_AND_FRAME_GAP = 150;

    protected Text topTitle;
    protected Pane parentPane;
    protected HBox centerChartBox;
    protected Slider bottomSizeSlider;
    protected PostItContainer postItContainer;
    protected List<PostChartDataProcessor> charts;

    public StatisticsChunkPane(String title, List<PostChartDataProcessor> charts, Pane parentPane, PostItContainer postItContainer) {
        this.parentPane = parentPane;
        this.topTitle = new Text(title);
        this.centerChartBox = new HBox();
        this.bottomSizeSlider = new Slider();
        this.postItContainer = postItContainer;
        this.charts = charts;

        this.parentPane.widthProperty().addListener(this::resizeChunkWidthOnParentWidthChange);

        initTitleSide();
        initChartSide();
        initBottomSide();
    }

    @Override
    public void initTitleSide() {
        StackPane titleContainer = new StackPane(this.topTitle);
        titleContainer.setId("titleContainer");
        this.setTop(titleContainer);
    }

    @Override
    public void initChartSide() {
        this.centerChartBox.getChildren().addAll(this.charts.stream()
                .map(PostChartDataProcessor::getChart)
                .collect(Collectors.toList()));

        this.bindChartsWidthWithChunkWidth();
        this.setCenter(this.centerChartBox);
    }

    private void resizeChunkWidthOnParentWidthChange(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
        double sizeFactor = this.charts.size() >= 2 ? 1 : 0.5;
        this.setPrefWidth(sizeFactor * ( (double) newVal - CHART_AND_FRAME_GAP));
    }

    private void bindChartsWidthWithChunkWidth() {
        this.charts.forEach(postChartDataProcessor -> postChartDataProcessor.getChart()
                .prefWidthProperty().bind(this.widthProperty()
                        .divide(this.charts.size())));
    }

    @Override
    public void initBottomSide() {
        // this.setBottom(this.bottomSizeSlider);
    }

    public void updateCharts() {
        charts.forEach(PostChartDataProcessor::updateChartData);
    }

}