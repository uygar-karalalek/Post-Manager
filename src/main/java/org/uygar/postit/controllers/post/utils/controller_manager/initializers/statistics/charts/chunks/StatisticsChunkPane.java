package org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.chunks;

import javafx.scene.chart.Chart;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.statistics.charts.GeneralPostChart;
import org.uygar.postit.data.structures.PostItContainer;

import java.util.List;
import java.util.stream.Collectors;

public abstract class StatisticsChunkPane extends BorderPane implements StatisticsChunk {

    protected Text topTitle;

    protected HBox centerChartBox;
    protected List<GeneralPostChart> charts;

    protected Slider bottomSizeSlider;

    protected PostItContainer postItContainer;

    public StatisticsChunkPane(String title, List<GeneralPostChart> charts, PostItContainer postItContainer) {
        this.topTitle = new Text(title);
        this.centerChartBox = new HBox();
        this.charts = charts;
        this.bottomSizeSlider = new Slider();
        this.postItContainer = postItContainer;

        initTitleSide();
        initChartSide();
        initSliderSide();
    }

    public List<Chart> getCharts() {
        return charts.stream().map(GeneralPostChart::getChart).collect(Collectors.toList());
    }

    public void updateCharts() {
        charts.forEach(GeneralPostChart::updateChartData);
    }

}