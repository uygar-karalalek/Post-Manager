package org.uygar.postit.controllers.application.app;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.uygar.postit.controllers.BaseController;
import org.uygar.postit.controllers.application.utils.app_loader.*;
import org.uygar.postit.controllers.application.utils.ButtonDisableBinding;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.data.properties.LogProperties;
import org.uygar.postit.data.structures.PostContainerOrganizer;
import org.uygar.postit.post.viewers.post.PostGridViewer;

import javax.swing.text.Style;

public class AppController extends BaseController {

    @FXML
    public BorderPane application;
    @FXML
    Text title;
    @FXML
    ScrollPane scrollPane;
    @FXML
    public Button addButton, filterButton, statisticaBtn;
    @FXML
    TextField searchField;
    @FXML
    MenuBar menuBar;
    @FXML
    VBox pannelloModifica;

    public LogProperties properties;
    public StatisticaLoader statisticaLoader;
    public FilterLoader filterLoader;
    public AggiungiLoader aggiungiLoader;

    public PostGridViewer postGrid;
    public DataMiner dataMiner = new DataMiner();
    public PostContainerOrganizer postOrganizer = new PostContainerOrganizer(dataMiner);

    public AppControllerManager controllerManager;
    public AppStyleManager styleManager;

    public void init() {
        controllerManager = new AppControllerManager(this);
        styleManager = new AppStyleManager(this);
        controllerManager.appInitializer.initialize();
    }

    @FXML
    public void onAggiungiPostClicked() {
        aggiungiLoader = new AggiungiLoader(this);
        aggiungiLoader.load();
    }

    @FXML
    public void onOpenFilterClicked() {
        filterLoader = new FilterLoader(this);
        filterLoader.load();
    }

    @FXML
    public void onOpenStatisticaClicked() {
        statisticaLoader = new StatisticaLoader(this);
        statisticaLoader.load();
    }

    @FXML
    public void onMousePressed(MouseEvent event) {
        controllerManager.positionManager.savePositionOnMousePressed(event);
    }

    @FXML
    public void onMouseDragged(MouseEvent event) {
        controllerManager.positionManager.changePositionOnMouseDragged(event);
    }

    @FXML
    public void onAbout() {
        // TODO : NOT YET IMPLEMENTED
    }

    @FXML
    public void onExitClicked() {
        Platform.exit();
    }

    public void setHidingStageEventAndShowAndWait(Stage stage, ButtonDisableBinding disableBinding) {
        if (disableBinding != null)
            stage.setOnHiding(disableBinding::enableButton);
        stage.showAndWait();
    }

    @FXML
    public void onBlackStyleClicked() {
        styleManager.setTheme("org/uygar/stylesheets/main/app_black.css");
    }

    @FXML
    public void onNormalStyleClicked() {
        styleManager.setTheme("org/uygar/stylesheets/main/app_normal.css");
    }

    @FXML
    public void onBlueStyleClicked() {
    }

    public void setLogProperties(LogProperties properties) {
        this.properties = properties;
    }

}