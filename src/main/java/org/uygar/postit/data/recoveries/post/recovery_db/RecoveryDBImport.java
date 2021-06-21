package org.uygar.postit.data.recoveries.post.recovery_db;

import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.data.query_utils.QueryUtils;
import org.uygar.postit.data.recoveries.post.recovery_folder.reader.RecoveryReader;
import org.uygar.postit.post.PostIt;

public class RecoveryDBImport extends Recovery<RecoveryReader> {

    public RecoveryDBImport(RecoveryReader recoveryReader, DataMiner dataMiner) {
        super(recoveryReader, dataMiner);
    }

    public boolean saveToDatabasePostAndPostIts() {
        boolean success = tryCreateNewPost();
        int newPostId = QueryUtils.getLastCreatedPostId(dataMiner);

        for (PostIt postIt : recoveryFolderManager.getNewPostIts()) {
            postIt.setPostFatherId(newPostId);
            success = success && QueryUtils.tryCreateNewPostItOnDB(dataMiner, postIt);
        }

        return success;
    }

    public boolean tryCreateNewPost() {
        return QueryUtils.tryCreateNewPostOnDB(dataMiner, this.recoveryFolderManager.getNewPost());
    }

}