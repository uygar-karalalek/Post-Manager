package org.uygar.postit.controllers.post.utils.controller_manager.initializers.settings;

import javafx.collections.FXCollections;
import org.uygar.postit.controllers.post.PostController;
import org.uygar.postit.controllers.post.utils.controller_manager.PostControllerViewManager;
import org.uygar.postit.controllers.post.utils.controller_manager.PostControllerWrapper;
import org.uygar.postit.controllers.post.utils.controller_manager.initializers.TabInitializer;
import org.uygar.postit.post.properties.Sort;

public class PostSettingsInitializer extends PostControllerWrapper implements TabInitializer {

    public PostSettingsInitializer(PostController postController) {
        super(postController);
    }

    @Override
    public void initializeTab() {
        postController.nomePostField.setText(postController.loadingPost.getName());
        postController.tipoOrdinamentoField.setValue(postController.loadingPost.getSortType());
        postController.tipoOrdinamentoField.setItems(FXCollections.observableArrayList(Sort.values()));
    }

}