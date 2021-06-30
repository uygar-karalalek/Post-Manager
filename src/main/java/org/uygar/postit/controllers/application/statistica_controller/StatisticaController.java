package org.uygar.postit.controllers.application.statistica_controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;
import org.uygar.postit.controllers.BaseController;
import org.uygar.postit.controllers.application.statistica_controller.utility.TableViewColumnsBuilder;
import org.uygar.postit.controllers.application.statistica_controller.utility.grafico.Statistica;
import org.uygar.postit.controllers.application.statistica_controller.utility.grafico.StatisticaGrafico;
import org.uygar.postit.controllers.application.statistica_controller.utility.model.FrequencyModel;
import org.uygar.postit.controllers.application.statistica_controller.utility.model.HourFrequencyModel;
import org.uygar.postit.controllers.application.statistica_controller.utility.model.MonthFrequencyModel;
import org.uygar.postit.data.properties.LogProperties;

import java.util.Arrays;
import java.util.List;

import static javafx.collections.FXCollections.*;
import static org.uygar.postit.controllers.application.statistica_controller.utility.StatisticaUtils.*;

public class StatisticaController extends BaseController {

    @FXML
    public TabPane statistica;
    @FXML
    HBox tableContainer;
    @FXML
    HBox padiglioneDispersione, padiglioneLinee;
    @FXML
    VBox scatterBox, lineBox, valoriPane;
    @FXML
    SplitPane dispersionePane, lineePane;
    @FXML
    TableView<HourFrequencyModel> tabellaOre;
    @FXML
    TableView<MonthFrequencyModel> tabellaMesi;

    TableViewColumnsBuilder<HourFrequencyModel> hourTableViewColumnsBuilder;
    TableViewColumnsBuilder<MonthFrequencyModel> monthTableViewColumnsBuilder;

    @FXML
    Text datiTitleDispersione, datiTitleLinee;

    Text[] scatterChartStatisticalData, lineChartStatisticalData;

    @FXML
    Separator datiSeparatorDispersione, datiSeparatorLinee;

    ScatterChart<Number, Number> scatterChart;

    LineChart<String, Number> lineChart;

    LogProperties properties;

    public void init() {
        updateStatisticalDataForScatterChart();
        updateStatisticalDataForLineChart();
        initDataEditorTableViews();
        initScatterChart();
        initLineChart();

        scatterBox.getChildren().add(scatterChart);
        lineBox.getChildren().add(lineChart);
    }

    public void initDataEditorTableViews() {
        tableContainer.prefHeightProperty().bind(valoriPane.heightProperty().divide(1.3));
        tableContainer.setMaxWidth(1300);

        initTableView(tabellaOre,
                HourFrequencyModel.Data.getHourFrequencyData(properties),
                HourFrequencyModel.getHourColumn(),
                HourFrequencyModel.getFrequencyColumn());

        hourTableViewColumnsBuilder = new TableViewColumnsBuilder<>(tabellaOre, this);
        hourTableViewColumnsBuilder.buildColumns();

        initTableView(tabellaMesi,
                MonthFrequencyModel.Data.getMonthFrequencyData(properties),
                MonthFrequencyModel.getMonthColumn(),
                MonthFrequencyModel.getFrequencyColumn());

        monthTableViewColumnsBuilder = new TableViewColumnsBuilder<>(tabellaMesi, this);
        monthTableViewColumnsBuilder.buildColumns();
    }

    @SafeVarargs
    public final <T extends FrequencyModel> void initTableView(TableView<T> tableView, ObservableList<T> data, TableColumn<T, String>... columns) {
        tableView.setItems(data);
        tableView.setEditable(true);
        tableView.getColumns().addAll(columns);
        tableView.prefWidthProperty().bind(tableContainer.widthProperty().divide(2));
    }

    public void initScatterChart() {
        initOreEFrequenza();
        scatterChart.setTitle("Frequenza degli accessi durante le ore del giorno");
        scatterChart.prefHeightProperty().bind(scatterBox.heightProperty());
    }

    public void initOreEFrequenza() {
        NumberAxis frequenza_ore = getFrequenzaAxis(0, 200, 10, "Frequenza accessi nelle ore");
        NumberAxis oreAxis = getOreAxis();
        scatterChart = new ScatterChart<>(oreAxis, frequenza_ore);

        List<XYChart.Data<Number, Number>> dataList = getHourFrequencyChartData(properties);
        XYChart.Series<Number, Number> xyData = new XYChart.Series<>();
        xyData.getData().addAll(dataList);
        xyData.setName("Frequenza accessi in una certa ora");

        ObservableList<XYChart.Series<Number, Number>> data = singletonObservableList(xyData);

        scatterChart.setData(data);
    }

    public void initLineChart() {
        CategoryAxis mese = getMeseAxis();
        NumberAxis frequenzaLine = getFrequenzaAxis(0, 2000, 100, "Numero accessi nei mesi (in decine)");

        lineChart = new LineChart<>(mese, frequenzaLine);
        lineChart.setTitle("Frequenza degli accessi durante i mesi");
        lineChart.prefHeightProperty().bind(lineBox.heightProperty());

        XYChart.Series<String, Number> xyData = getMonthFrequences(getMonthFrequenceChartData(properties));
        ObservableList<XYChart.Series<String, Number>> data = singletonObservableList(xyData);

        lineChart.setData(data);
    }

    @NotNull
    private XYChart.Series<String, Number> getMonthFrequences(List<XYChart.Data<String, Number>> monthFrequence) {
        XYChart.Series<String, Number> xyData = new XYChart.Series<>();
        xyData.getData().addAll(monthFrequence);
        xyData.setName("Frequenza in un certo mese");
        return xyData;
    }

    public void updateStatisticalDataForScatterChart() {
        StatisticaGrafico scatterChartStatistics =
                new StatisticaGrafico(Statistica.TipoGrafico.ORE, properties);

        scatterChartStatisticalData = scatterChartStatistics.getDataAsTextNodes();

        setStatisticalDataOfChart(padiglioneDispersione, scatterChartStatisticalData);
    }

    public void updateStatisticalDataForLineChart() {
        StatisticaGrafico lineChartStatistics =
                new StatisticaGrafico(Statistica.TipoGrafico.MESI, properties);

        lineChartStatisticalData = lineChartStatistics.getDataAsTextNodes();

        setStatisticalDataOfChart(padiglioneLinee, lineChartStatisticalData);
    }

    public void setStatisticalDataOfChart(HBox padiglioneStats, Text[] nodes) {
        padiglioneStats.getChildren().removeIf(this::nodeIsStatisticalDataOrSeparator);

        Arrays.stream(nodes).forEachOrdered(textNode -> {
            padiglioneStats.getChildren().add(textNode);
            boolean isLastNode = textNode != nodes[nodes.length-1];
            if (isLastNode) {
                padiglioneStats.getChildren().add(new Separator(Orientation.VERTICAL));
                int wrappingMultiply = 9;
                textNode.setWrappingWidth(wrappingMultiply * textNode.getText().length());
            }
        });

    }

    public void updateGraphs() {
        lineChart.getData().get(0).getData().setAll(getMonthFrequenceChartData(properties));
        scatterChart.getData().get(0).getData().setAll(getHourFrequencyChartData(properties));
        updateStatisticalDataForLineChart();
        updateStatisticalDataForScatterChart();
    }

    public boolean nodeIsStatisticalDataOrSeparator(Node node) {
        boolean nodeIsTheTitle = node == datiTitleDispersione || node == datiTitleLinee;
        boolean nodeIsTheTitleSeparator = node == datiSeparatorDispersione || node == datiSeparatorLinee;

        if (nodeIsTheTitle || nodeIsTheTitleSeparator)
            return false;
        return true;
    }

    public void setLogProperties(LogProperties properties) {
        this.properties = properties;
    }
    public LogProperties getLogProperties() {
        return this.properties;
    }

}