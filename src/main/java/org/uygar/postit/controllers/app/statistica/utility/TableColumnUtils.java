package org.uygar.postit.controllers.app.statistica.utility;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Window;
import org.apache.commons.lang.StringUtils;
import org.thymeleaf.util.NumberUtils;
import org.uygar.postit.controllers.app.exception.WrongFieldsException;
import org.uygar.postit.controllers.app.statistica.StatisticaController;
import org.uygar.postit.controllers.app.statistica.utility.model.FrequencyModel;
import org.uygar.postit.data.properties.LogProperties;

import java.util.Map;

public class TableColumnUtils {

    private static <T extends FrequencyModel> void initTableColumnAndHandleOnEdit(
            TableColumn<T, ?> tableColumn, StatisticaController controller) throws Exception {

        TableView<T> tableView = tableColumn.getTableView();

        if (tableView == null)
            throw new Exception("YOU MUST TO ADD THE COLUMN TO THE TABLEVIEW!");

        double numOfCols = tableView.getColumns().size();
        tableColumn.prefWidthProperty().bind(tableView.widthProperty().divide(numOfCols));

        tableColumn.setOnEditCommit(edit -> onEditCommit(edit, controller));
    }

    public static <T extends FrequencyModel> void handleTableViewCols(TableView<T> tableView, StatisticaController controller) {
        tableView.getColumns().forEach(col -> {
            try {
                initTableColumnAndHandleOnEdit(col, controller);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static <T extends FrequencyModel> void onEditCommit(TableColumn.CellEditEvent<T, ?> edit, StatisticaController controller) {
        Map.Entry<String, String> keyAndValue = edit.getRowValue().getKeyAndValue();
        String newValue = edit.getNewValue().toString();

        if (StringUtils.isNumeric(newValue)) {
            controller.getLogProperties().putProperty(keyAndValue.getKey(), newValue);
            controller.getLogProperties().storeProperties();
            edit.getTableView().getItems().get(edit.getTablePosition().getRow()).setFrequenza(newValue);
            controller.getLogProperties().updateFrequencies();
            controller.updateGraphs();
            return;
        }

        throw new IllegalArgumentException("Must be a number!");
    }

}