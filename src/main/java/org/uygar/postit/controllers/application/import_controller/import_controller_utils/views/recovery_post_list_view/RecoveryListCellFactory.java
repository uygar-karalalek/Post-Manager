package org.uygar.postit.controllers.application.import_controller.import_controller_utils.views.recovery_post_list_view;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.uygar.postit.data.recoveries.post.recovery_folder.reader.RecoveryPostReader;
import org.uygar.postit.post.Post;

public class RecoveryListCellFactory implements Callback<ListView<RecoveryPostReader>, ListCell<RecoveryPostReader>> {

    @Override
    public ListCell<RecoveryPostReader> call(ListView<RecoveryPostReader> postListView) {
        return new RecoveryPostListItem();
    }

}
