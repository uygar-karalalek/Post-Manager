package org.uygar.postit.controllers.post.utils.controller_manager.initializers.settings;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import org.uygar.postit.controllers.post.PostController;
import org.uygar.postit.controllers.post.utils.controller_manager.PostControllerWrapper;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.TabInitializer;
import org.uygar.postit.post.properties.Sort;

public class PostSettingsInitializer extends PostControllerWrapper implements TabInitializer {

    public PostSettingsInitializer(PostController postController) {
        super(postController);
    }

    @Override
    public void initializeTab() {
        setInitialFields();
        initSortMenuChoice();
    }

    private void initSortMenuChoice() {
        for (Sort element : Sort.values()) {
            MenuItem item = new MenuItem(element.getName());
            postController.tipoOrdinamentoField.getItems().add(item);
            item.setOnAction(this::onSortDecided);
        }
    }

    public void onSortDecided(ActionEvent event) {
        MenuItem sort = (MenuItem) event.getTarget();
        postController.tipoOrdinamentoField.setText(sort.getText());
    }

    public void setInitialFields() {
        postController.nomePostField.setText(postController.loadingPost.getName());
        postController.tipoOrdinamentoField.setText(postController.loadingPost.getSortType().getName());
    }

}