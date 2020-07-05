package org.uygar.postit.controllers.app;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class StatisticaController implements Initializable {

    @FXML
    AnchorPane dispersionePane, lineePane, bollePane;

    ScatterChart<LocalDate, Integer> scatter;
    LineChart<LocalDate, Integer> line;
    BubbleChart<LocalDate, Integer> bubble;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initScatter();
        initLine();
        initBubble();
    }

    public void initScatter() {

    }

    public void initLine() {

    }

    public void initBubble() {

    }

}