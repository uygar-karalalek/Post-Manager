package org.uygar.postit.controllers.post.postit.editor_manager.managers;

import org.uygar.postit.controllers.post.postit.PostItUtils;
import org.uygar.postit.controllers.post.postit.editor_manager.PostItEditorManager;
import org.uygar.postit.post.PostIt;

import static org.uygar.postit.data.query_utils.QueryUtils.tryCreateNewPostItOnDB;
import static org.uygar.postit.data.query_utils.QueryUtils.tryModifyPostItOnDB;

public class DataEditorManager extends EditorManager {

    public DataEditorManager(PostItEditorManager manager) {
        super(manager);
    }

    @Override
    public void initialize() {
        loadValuesIfModifying();
    }

    private void loadValuesIfModifying() {
        if (getManager().getPostItController().modifying) {
            PostIt loadedPostIt = getManager().getPostItController().loadedPostIt;

            getManager().rectangleColor.set(loadedPostIt.getColore());

            getManager().getPostItController().rimuoviBtn.setDisable(false);
            getManager().getPostItController().titoloField.setText(loadedPostIt.getTitolo());
            getManager().getPostItController().compitoField.setText(loadedPostIt.getTesto());
            getManager().getPostItController().dataField.setValue(loadedPostIt.getDataScadenza().toLocalDate());
            getManager().getPostItController().oraField.setText(String.valueOf(loadedPostIt.getDataScadenza().getHour()));
            getManager().getPostItController().minutoField.setText(String.valueOf(loadedPostIt.getDataScadenza().getMinute()));
            getManager().getPostItController().prioritySpinner.getEditor().setText(String.valueOf(loadedPostIt.getPriority()));
        }
    }

    public boolean trySavePostIt(PostIt postIt) {
        boolean operationSucceed;
        if (getManager().getPostItController().modifying) {
            if (operationSucceed = tryModifyPostItOnDB(getManager().getPostItController().miner, postIt)) {
                PostItUtils.copyBaseValuesFromByPostIts(getManager().getPostItController().loadedPostIt, postIt);
            }
        } else {
            if (operationSucceed = tryCreateNewPostItOnDB(getManager().getPostItController().miner, postIt)) {
                getManager().getPostItController().postItGrid.add(postIt);
            }
        }
        return operationSucceed;
    }

}