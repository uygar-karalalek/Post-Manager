package org.uygar.postit.controllers.post.utils.controller_manager.initializers.post;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.uygar.postit.controllers.post.PostController;
import org.uygar.postit.controllers.post.utils.PostStatistics;
import org.uygar.postit.controllers.post.utils.PostStatisticsViewManager;
import org.uygar.postit.controllers.post.utils.controller_manager.PostControllerViewManager;
import org.uygar.postit.controllers.post.utils.controller_manager.PostControllerWrapper;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.TabInitializer;
import org.uygar.postit.post.viewers.post_it.PostItGridViewer;

import java.util.Locale;

public class PostTabInitializer extends PostControllerWrapper implements TabInitializer {

    protected static final float SCROLL_SPEED = 0.001f;
    public PostTabInitializer(PostController postController) {
        super(postController);
    }

    @Override
    public void initializeTab() {
        postController.srcBar.textProperty().addListener(this::onSearchTextChangeDoFilter);
        postController.postItGrid = new PostItGridViewer(postController.loadingPost, postController.dataMiner);
        postController.rootTabPane.prefWidthProperty().bind(postController.post.widthProperty());
        postController.rootTabPane.prefHeightProperty().bind(postController.post.heightProperty());

        initGridPane();
        initPostTitle();
        PostStatisticsViewManager.buildChart(postController.pieChart,
                new PostStatistics(postController.postItGrid.getPostItOrganizer()));
    }

    public void onSearchTextChangeDoFilter(ObservableValue<? extends String> obs, String oldVal, String newVal) {
        postController.postItGrid.sortPostIts();
        postController.postItGrid.filter(postIt -> postIt.getTitolo().toLowerCase(Locale.ROOT).contains(newVal.toLowerCase()));
    }

    private void initGridPane() {
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
        postController.postTitle.setText(postController.loadingPost.getName());
        setFontSizeToTitleLabelBasedOnLength();
    }

    private void setFontSizeToTitleLabelBasedOnLength() {
        double defSize = 30;
        double lblTextLength = defSize / 1.25 * postController.postTitle.getText().length();
        double ratioContainerLabel = postController.vBoxOperationsContainer.getPrefWidth() / lblTextLength;
        ratioContainerLabel = Math.min(1, ratioContainerLabel);
        Font def = Font.font("Arial", FontWeight.EXTRA_BOLD, defSize * ratioContainerLabel);
        postController.postTitle.setFont(def);
    }

}