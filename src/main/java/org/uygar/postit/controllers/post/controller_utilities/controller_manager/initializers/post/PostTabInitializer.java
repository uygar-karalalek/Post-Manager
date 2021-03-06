package org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.post;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.uygar.postit.controllers.exception.WindowCoordinatesContainer;
import org.uygar.postit.controllers.exception.WrongFieldsException;
import org.uygar.postit.controllers.post.PostController;
import org.uygar.postit.controllers.post.controller_utilities.PostStatistics;
import org.uygar.postit.controllers.post.controller_utilities.PostStatisticsViewManager;
import org.uygar.postit.controllers.post.controller_utilities.controller_manager.PostControllerWrapper;
import org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.TabInitializer;
import org.uygar.postit.data.query_utils.QueryUtils;
import org.uygar.postit.post.properties.Sort;
import org.uygar.postit.post.viewers.post_it.PostItGridViewer;

public class PostTabInitializer extends PostControllerWrapper implements TabInitializer {

    protected static final float SCROLL_SPEED = 0.001f;
    public PostTabInitializer(PostController postController) {
        super(postController);
    }

    @Override
    public void initializeTab() {
        initGridPane();
        initPostTitle();
        bindRootTabPaneSizeWithWindow();

        PostStatisticsViewManager.buildChart(postController.pieChart,
                new PostStatistics(postController.postItGrid.getPostItOrganizer()));

        postController.srcBar.textProperty().addListener(this::onSearchTextChangeDoFilter);
    }

    private void bindRootTabPaneSizeWithWindow() {
        postController.rootTabPane.prefWidthProperty().bind(postController.post.widthProperty());
        postController.rootTabPane.prefHeightProperty().bind(postController.post.heightProperty());
    }

    public void onSearchTextChangeDoFilter(ObservableValue<? extends String> obs, String oldVal, String newVal) {
        postController.postItGrid.filter(postIt -> postIt.getTitolo().toLowerCase().contains(newVal.toLowerCase()));
    }

    private void initGridPane() {
        postController.postItGrid = new PostItGridViewer(postController.loadedPost, postController.dataMiner);

        postController.gridFatherScroll.setContent(postController.postItGrid);
        postController.postItGrid.prefWidthProperty().bind(postController.gridFatherScroll.widthProperty());
        postController.postItGrid.prefHeightProperty().bind(postController.gridFatherScroll.heightProperty());
        postController.gridFatherScroll.getContent().setOnScroll(this::setOnPostItGridScroll);
        postController.gridFatherScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        postController.gridFatherScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    private void setOnPostItGridScroll(ScrollEvent event) {
        double deltaY = event.getDeltaY() * SCROLL_SPEED;
        postController.gridFatherScroll.setVvalue(postController.gridFatherScroll.getVvalue() - deltaY);
    }

    private void initPostTitle() {
        postController.postTitle.textProperty().bind(postController.loadedPost.nameProperty());
        changeFontSizeOfPostTitleLabelBasedOnLength();
    }

    private void changeFontSizeOfPostTitleLabelBasedOnLength() {
        double defSize = 30;
        double lblTextLength = defSize / 1.25 * postController.postTitle.getText().length();
        double ratioContainerLabel = postController.vBoxOperationsContainer.getPrefWidth() / lblTextLength;
        ratioContainerLabel = Math.min(1, ratioContainerLabel);
        Font def = Font.font("Arial", FontWeight.EXTRA_BOLD, defSize * ratioContainerLabel);
        postController.postTitle.setFont(def);
    }

    public void changePostBasedOnSettings() throws WrongFieldsException {
        if (nameMoreThan18Chars()) {
            throw new WrongFieldsException("Il nome deve avere al massimo 18 caratteri!",
                    new WindowCoordinatesContainer(postController.getStage()));
        }
        else if (nameAlreadyExists()) {
            throw new WrongFieldsException("Il post esiste già",
                    new WindowCoordinatesContainer(postController.getStage()));
        }
        else {
            updateViewPostName();
            updateViewPostSortType();
        }
    }

    private boolean nameAlreadyExists() {
        return !updatePostNameFromDB();
    }

    private boolean nameMoreThan18Chars() {
        return postController.nomePostField.getText().length() > 18;
    }

    private boolean updatePostNameFromDB() {
        return QueryUtils.updatePostName(postController.dataMiner, postController.nomePostField.getText(),
                postController.loadedPost.getId());
    }

    private void updateViewPostName() {
        postController.loadedPost.setName(postController.nomePostField.getText());
        changeFontSizeOfPostTitleLabelBasedOnLength();
    }

    private void updateViewPostSortType() {
        Sort sortType = Sort.getSortBasedOnName(postController.tipoOrdinamentoField.getText());
        postController.loadedPost.setSortType(sortType);
        QueryUtils.updatePostSortType(postController.dataMiner, sortType, postController.loadedPost.getId());
    }

}