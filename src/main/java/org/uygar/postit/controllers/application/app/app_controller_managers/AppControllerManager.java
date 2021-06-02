package org.uygar.postit.controllers.application.app.app_controller_managers;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.uygar.postit.controllers.application.app.AppController;
import org.uygar.postit.controllers.application.utils.app_loader.PostLoader;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.viewers.post.PostGridViewer;

import java.util.Optional;
import java.util.function.Consumer;

public class AppControllerManager extends AppManager {

    public AppControllerManager(AppController controller) {
        super(controller);
    }

    public AppInitializer appInitializer = new AppInitializer();

    public class AppInitializer {

        public static final int MODIFICA_BTNS_HORIZONTAL_MARGIN = 60;

        public void initialize() {
            initAndRequestFocusToSearchField();
            initModificaButtonsSize();
            initPostGrid();
            appController.controllerManager.sizeManager.changeOnStageSize();
            appController.searchField.textProperty().addListener(this::onSearchChanged);
            appController.controllerManager = new AppControllerManager(appController);
            appController.styleManager = new AppStyleManager(appController);
        }

        private void initModificaButtonsSize() {
            Consumer<Button> applyToButton = button -> {
                button.prefWidthProperty().bind(appController.pannelloModifica.widthProperty().subtract(MODIFICA_BTNS_HORIZONTAL_MARGIN));
            };

            applyToButton.accept(appController.addButton);
            applyToButton.accept(appController.importButton);
            applyToButton.accept(appController.filterButton);
            applyToButton.accept(appController.statisticaBtn);
            applyToButton.accept(appController.esciBtn);
        }

        private void initAndRequestFocusToSearchField() {
            appController.searchField.setFocusTraversable(true);
            appController.searchField.requestFocus();
        }

        private void initPostGrid() {
            appController.postGrid = new PostGridViewer(appController.postOrganizer);
            appController.postGrid.selected.addListener(this::onSelectedPostChange);
            appController.scrollPane.setContent(appController.postGrid);
        }

        public void onSearchChanged(ObservableValue<? extends String> obs, String oldVal, String newVal) {
            appController.postGrid.filterPostsNameContaining(newVal);
        }

        public void onSelectedPostChange(ObservableValue<? extends Post> v, Post oldV, Post newV) {
            Optional<Post> newPost = Optional.ofNullable(newV);
            newPost.ifPresent(this::loadPost);
        }

        private void loadPost(Post post) {
            PostLoader loader = new PostLoader(appController, post);
            loader.load();
        }
    }

    public AppPositionResponsivenessManager positionManager = new AppPositionResponsivenessManager();

    public class AppPositionResponsivenessManager {
        private double onClickedX, onClickedY;

        public void savePositionOnMousePressed(MouseEvent event) {
            onClickedX = event.getX();
            onClickedY = event.getY();
        }

        public void changePositionOnMouseDragged(MouseEvent event) {
            Stage mainStage = (Stage) appController.application.getScene().getWindow();
            double xDiff = event.getScreenX() - onClickedX;
            double yDiff = event.getScreenY() - onClickedY;

            mainStage.setX(xDiff);
            mainStage.setY(yDiff);
        }

    }

    public AppSizeResponsivenessManager sizeManager = new AppSizeResponsivenessManager();

    public class AppSizeResponsivenessManager {

        public void changeOnStageSize() {
            appController.getStage().widthProperty().addListener((obs, oldWidth, newWidth) -> {
                appController.scrollPane.setPrefWidth(((double) newWidth * (487.0 / 850)));
                appController.pannelloModifica.setPrefWidth(((double) newWidth * (300.0 / 850)));
            });
            appController.getStage().heightProperty().addListener((obs, oldHeight, newHeight) -> {
                appController.scrollPane.setPrefHeight((double) newHeight * (326.0 / 544));
                appController.pannelloModifica.setPrefHeight(((double) newHeight * (218.0 / 544)));
            });
        }

    }

}