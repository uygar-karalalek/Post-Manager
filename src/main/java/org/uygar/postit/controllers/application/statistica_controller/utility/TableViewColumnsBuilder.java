package org.uygar.postit.controllers.application.statistica_controller.utility;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.commons.lang.StringUtils;
import org.uygar.postit.controllers.application.statistica_controller.StatisticaController;
import org.uygar.postit.controllers.application.statistica_controller.utility.model.FrequencyModel;

import java.util.Map;

public class TableViewColumnsBuilder<T extends FrequencyModel> {

    private final TableView<T> tableView;
    private final StatisticaController controller;

    public TableViewColumnsBuilder(TableView<T> tableView, StatisticaController controller) {
        this.tableView = tableView;
        this.controller = controller;
    }

    public void buildColumns() {
        buildTableViewColumns();
    }

    private void buildTableViewColumns() {
        tableView.getColumns().forEach(col -> {
            try {
                initTableColumnAndHandleOnEdit(col);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void initTableColumnAndHandleOnEdit(TableColumn<T, ?> tableColumn) throws Exception {
        if (tableView == null)
            throw new Exception("YOU MUST TO ADD NON-NULL COLUMN TO THE TABLEVIEW!");

        double numOfCols = tableView.getColumns().size();
        tableColumn.prefWidthProperty().bind(tableView.widthProperty().divide(numOfCols));

        tableColumn.setOnEditCommit(this::onEditCommit);
    }

    private void onEditCommit(TableColumn.CellEditEvent<T, ?> edit) {
        Map.Entry<String, String> keyAndValue = edit.getRowValue().getKeyAndValue();
        String newValue = edit.getNewValue().toString();

        if (StringUtils.isNumeric(newValue)) {
            controller.getLogProperties().putProperty(keyAndValue.getKey(), newValue);
            controller.getLogProperties().storeProperties();
            tableView.getItems().get(edit.getTablePosition().getRow()).setFrequenza(newValue);
            controller.getLogProperties().calculateLogStatistics();
            controller.updateGraphs();
        }

        else throw new IllegalArgumentException("Must be a number!");
    }

}