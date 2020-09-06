package org.uygar.postit.controllers.app.statistica;

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
import org.uygar.postit.controllers.app.statistica.utility.TableColumnUtils;
import org.uygar.postit.controllers.app.statistica.utility.grafico.Statistica;
import org.uygar.postit.controllers.app.statistica.utility.grafico.StatisticaGrafico;
import org.uygar.postit.controllers.app.statistica.utility.model.FrequencyModel;
import org.uygar.postit.controllers.app.statistica.utility.model.HourFrequencyModel;
import org.uygar.postit.controllers.app.statistica.utility.model.MonthFrequencyModel;
import org.uygar.postit.data.properties.LogProperties;

import java.util.List;

import static javafx.collections.FXCollections.*;
import static org.uygar.postit.controllers.app.statistica.utility.StatisticaUtils.*;

public class StatisticaController {

    @FXML
    public TabPane root;

    @FXML
    HBox tableContainer;

    @FXML
    VBox scatterBox, lineBox, valoriPane;

    @FXML
    HBox padiglioneDispersione, padiglioneLinee;

    @FXML
    SplitPane dispersionePane, lineePane;

    @FXML
    TableView<HourFrequencyModel> tabellaOre;

    @FXML
    TableView<MonthFrequencyModel> tabellaMesi;

    @FXML
    Text datiTitleDispersione, datiTitleLinee;

    @FXML
    Separator datiSeparatorDispersione, datiSeparatorLinee;

    ScatterChart<Number, Number> scatter;

    LineChart<String, Number> line;

    LogProperties properties;

    public void init() {
        initDatiDispersione();
        initTableViews();
        initDatiLinee();
        initScatter();
        initLine();

        scatterBox.getChildren().add(scatter);
        lineBox.getChildren().add(line);
    }

    public void initTableViews() {
        tableContainer.prefHeightProperty().bind(valoriPane.heightProperty().divide(1.3));

        initTableView(tabellaOre,
                HourFrequencyModel.Data.getHourFrequencyData(properties),
                HourFrequencyModel.getHourColumn(),
                HourFrequencyModel.getFrequencyColumn());

        initTableView(tabellaMesi,
                MonthFrequencyModel.Data.getMonthFrequencyData(properties),
                MonthFrequencyModel.getMonthColumn(),
                MonthFrequencyModel.getFrequencyColumn());
    }

    @SafeVarargs
    public final <T extends FrequencyModel> void initTableView(TableView<T> tableView, ObservableList<T> data, TableColumn<T, String>... columns) {
        tableView.setItems(data);
        tableView.setEditable(true);
        tableView.getColumns().addAll(columns);
        tableView.prefWidthProperty().bind(tableContainer.widthProperty().divide(2));

        // WARNING! do not copy-paste this BEFORE the line 'tableView.getColumns().addAll(columns);' !!!!
        // Otherwise it will throw ILLEGALACCESSEXCEPTION
        TableColumnUtils.handleTableViewCols(tableView, this);
    }

    public void initScatter() {
        initOreEFrequenza();
        scatter.setTitle("Frequenza ore del giorno");
        scatter.prefHeightProperty().bind(scatterBox.heightProperty());
    }

    public void initOreEFrequenza() {
        NumberAxis frequenza_ore = getFrequenzaAxis(0, 100, 10, "Frequenza ore");
        NumberAxis oreAxis = getOreAxis();
        scatter = new ScatterChart<>(oreAxis, frequenza_ore);

        List<XYChart.Data<Number, Number>> dataList = getHourFrequence(properties);
        XYChart.Series<Number, Number> xyData = new XYChart.Series<>();
        xyData.getData().addAll(dataList);
        xyData.setName("Frequenza in una certa ora");

        ObservableList<XYChart.Series<Number, Number>> data = singletonObservableList(xyData);

        scatter.setData(data);
    }

    public void initLine() {
        CategoryAxis mese = getMeseAxis();
        NumberAxis frequenzaLine = getFrequenzaAxis(0, 2000, 100, "Frequenza mesi (in decine)");

        line = new LineChart<>(mese, frequenzaLine);
        line.setTitle("Frequenza nei mesi");
        line.prefHeightProperty().bind(lineBox.heightProperty());

        XYChart.Series<String, Number> xyData = getStringNumberSeries(getMonthFrequence(properties), "Frequenza in un certo mese");
        ObservableList<XYChart.Series<String, Number>> data = singletonObservableList(xyData);

        line.setData(data);
    }

    @NotNull
    private XYChart.Series<String, Number> getStringNumberSeries(List<XYChart.Data<String, Number>> monthFrequence, String s) {
        XYChart.Series<String, Number> xyData = new XYChart.Series<>();
        xyData.getData().addAll(monthFrequence);
        xyData.setName(s);
        return xyData;
    }

    public void initDatiDispersione() {
        StatisticaGrafico stats =
                new StatisticaGrafico(Statistica.TipoGrafico.ORE, properties);

        initData(padiglioneDispersione, stats.getDataArray());
    }

    public void initDatiLinee() {
        StatisticaGrafico stats =
                new StatisticaGrafico(Statistica.TipoGrafico.MESI, properties);

        initData(padiglioneLinee, stats.getDataArray());
    }

    public void initData(HBox padiglione, String[] data) {
        padiglione.getChildren().removeIf(this::removeNodeIfIsStatisticalOrSeparator);

        for (String dataElement : data) {
            Text textElement = new Text(dataElement);
            textElement.setId("textDati");
            padiglione.getChildren().add(textElement);
            if (!dataElement.equals(data[data.length - 1])) {
                padiglione.getChildren().add(new Separator(Orientation.VERTICAL));
                textElement.setWrappingWidth(9 * dataElement.length());
            }
        }
    }

    public void updateGraphs() {
        line.getData().get(0).getData().setAll(getMonthFrequence(properties));
        scatter.getData().get(0).getData().setAll(getHourFrequence(properties));
        initDatiLinee();
        initDatiDispersione();
    }

    public void setLogProperties(LogProperties properties) {
        this.properties = properties;
    }

    public LogProperties getLogProperties() {
        return this.properties;
    }

    public boolean removeNodeIfIsStatisticalOrSeparator(Node node) {
        boolean equalsToDatiTitleText = node == datiTitleDispersione || node == datiTitleLinee;
        boolean equalsToDatiSeparator = node == datiSeparatorDispersione || node == datiSeparatorLinee;
        if (equalsToDatiTitleText || equalsToDatiSeparator)
            return false;
        return true;
    }

}