package org.uygar.postit.controllers.application.app;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.uygar.postit.controllers.application.AggiungiController;
import org.uygar.postit.controllers.application.FXLoader;
import org.uygar.postit.controllers.application.filter.FilterController;
import org.uygar.postit.controllers.application.statistica.StatisticaController;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.data.properties.LogProperties;
import org.uygar.postit.data.structures.PostContainerOrganizer;
import org.uygar.postit.post.Post;
import org.uygar.postit.viewers.PostGridViewer;

import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    @FXML
    public BorderPane rootPane;
    @FXML
    Text title;
    @FXML
    ScrollPane scrollPane;
    @FXML
    PostGridViewer postGrid;
    @FXML
    Button addButton, filterButton;
    @FXML
    TextField searchField;

    public BooleanProperty filterClosed = new SimpleBooleanProperty(true);
    DataMiner dataMiner = new DataMiner();
    PostContainerOrganizer postOrganizer = new PostContainerOrganizer(dataMiner);
    LogProperties properties;
    WindowInitializer windowInitializer = new WindowInitializer(this);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
    }

    private void init() {
        searchField.setFocusTraversable(true);
        searchField.requestFocus();
        initPostGrid();
        filterButton.disableProperty().bind(filterClosed.not());
        this.searchField.textProperty().addListener(this::onSearchChanged);
    }

    private void initPostGrid() {
        postGrid = new PostGridViewer(postOrganizer);
        postGrid.selected.addListener(this::onSelectedChange);
        this.scrollPane.setContent(postGrid);
    }

    @FXML
    public void onAddClicked() {
        AggiungiController ac = (AggiungiController) FXLoader.getLoadedController("add", "app");
        ac.setPostGridViewer(this.postGrid);
        windowInitializer.fadeWindowEffect(ac.root, 1);
        showWindow(366, 285, Modality.APPLICATION_MODAL, ac.root);
    }

    @FXML
    public void onFilterClicked() {
        filterClosed.set(!filterClosed.get());
        FilterController fc = (FilterController) FXLoader.getLoadedController("filter", "app");
        fc.init(this.postGrid);
        windowInitializer.fadeWindowEffect(fc.root, 0.4);
        showWindow(486, 400, Modality.WINDOW_MODAL, fc.root);
    }

    public void showWindow(double prefWidth, double prefHeight, Modality modality, Parent root) {
        Stage stage = windowInitializer.getStageWithModality(prefWidth, prefHeight, modality);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setOnHidden(event -> this.filterClosed.set(true));
        stage.showAndWait();
    }

    @FXML
    public void onExitClicked() {
        this.rootPane.getScene().getWindow().hide();
    }

    @FXML
    public void onAbout() {

    }

    @FXML
    public void onStatisticaClicked() {
        Stage stage = windowInitializer.getStageWithModality(Modality.WINDOW_MODAL, true);
        StatisticaController sc = (StatisticaController)
                FXLoader.getLoadedController("statistica", "app");
        sc.setLogProperties(properties);
        sc.init();
        Scene scene = new Scene(sc.root);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void onSearchChanged(ObservableValue<? extends String> obs, String oldVal, String newVal) {
        this.postGrid.filterPostsNameContaining(newVal);
    }

    public Stage getStage() {
        return (Stage) this.rootPane.getScene().getWindow();
    }

    public void onSelectedChange(ObservableValue<? extends Post> v, Post oldV, Post newV) {
        this.loadPost(newV);
    }

    private void loadPost(Post post) {

    }

    public void setLogProperties(LogProperties properties) {
        this.properties = properties;
    }

}