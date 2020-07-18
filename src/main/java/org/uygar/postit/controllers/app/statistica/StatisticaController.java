package org.uygar.postit.controllers.app.statistica;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;
import org.uygar.postit.data.properties.LogProperties;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StatisticaController implements Initializable {

    @FXML
    public TabPane root;

    @FXML
    SplitPane dispersionePane, lineePane;

    @FXML
    ScrollPane valoriPane;

    @FXML
    VBox scatterBox, lineBox;

    ScatterChart<Number, Number> scatter;

    LineChart<String, Number> line;

    LogProperties properties = new LogProperties();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initScatter();
        initLine();
        scatterBox.getChildren().add(scatter);
        lineBox.getChildren().add(line);
    }

    public void initScatter() {
        initOreEFrequenza();
        scatter.setTitle("Frequenza ore del giorno");
        scatter.prefHeightProperty().bind(scatterBox.heightProperty());
    }

    public void initOreEFrequenza() {
        NumberAxis frequenza_ore = StatisticaUtils.getFrequenzaAxis(0, 100, 10, "Frequenza ore");
        NumberAxis oreAxis = StatisticaUtils.getOreAxis();
        scatter = new ScatterChart<>(oreAxis, frequenza_ore);

        List<XYChart.Data<Number, Number>> dataList = StatisticaUtils.getHourFrequence(properties);
        XYChart.Series<Number, Number> xyData = new XYChart.Series<>();
        xyData.getData().addAll(dataList);
        xyData.setName("Frequenza in una certa ora");

        ObservableList<XYChart.Series<Number, Number>> data = FXCollections.singletonObservableList(xyData);

        scatter.setData(data);
    }

    public void initLine() {
        CategoryAxis mese = StatisticaUtils.getMeseAxis();
        NumberAxis frequenzaLine = StatisticaUtils.getFrequenzaAxis(0, 2000, 100, "Frequenza mesi (in decine)");

        line = new LineChart<>(mese, frequenzaLine);
        line.prefHeightProperty().bind(lineBox.heightProperty());

        XYChart.Series<String, Number> xyData = getStringNumberSeries(StatisticaUtils.getMonthFrequence(properties), "Frequenza in un certo mese");

        ObservableList<XYChart.Series<String, Number>> data = FXCollections.singletonObservableList(xyData);

        line.setData(data);
    }

    @NotNull
    private XYChart.Series<String, Number> getStringNumberSeries(List<XYChart.Data<String, Number>> monthFrequence, String s) {
        List<XYChart.Data<String, Number>> dataList = monthFrequence;
        XYChart.Series<String, Number> xyData = new XYChart.Series<>();
        xyData.getData().addAll(dataList);
        xyData.setName(s);
        return xyData;
    }

}