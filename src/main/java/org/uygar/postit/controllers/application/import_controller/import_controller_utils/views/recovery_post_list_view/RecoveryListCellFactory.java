package org.uygar.postit.controllers.application.import_controller.import_controller_utils.views.recovery_post_list_view;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.uygar.postit.data.recoveries.post.recovery_folder.reader.RecoveryPostReader;
import org.uygar.postit.data.structures.PostItContainerOrganizer;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.viewers.post.PostGridViewer;
import org.uygar.postit.post.viewers.post_it.PostItGridViewer;

public class RecoveryListCellFactory implements Callback<ListView<RecoveryPostReader>, ListCell<RecoveryPostReader>> {

    private PostGridViewer postGridViewer;
    private PostItContainerOrganizer postIts;

    public RecoveryListCellFactory(PostGridViewer postGridViewer, PostItContainerOrganizer postIts) {
        this.postGridViewer = postGridViewer;
        this.postIts = postIts;
    }

    @Override
    public ListCell<RecoveryPostReader> call(ListView<RecoveryPostReader> postListView) {
        return new RecoveryPostListItem(postGridViewer, postIts);
    }

}
