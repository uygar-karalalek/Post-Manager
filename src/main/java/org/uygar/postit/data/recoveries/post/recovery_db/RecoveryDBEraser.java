package org.uygar.postit.data.recoveries.post.recovery_db;

import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.data.query_utils.QueryUtils;
import org.uygar.postit.data.recoveries.post.recovery_folder.writer.RecoveryWriter;
import org.uygar.postit.post.PostIt;

public class RecoveryDBEraser extends Recovery<RecoveryWriter> {

    public RecoveryDBEraser(RecoveryWriter recoveryFolder, DataMiner dataMiner) {
        super(recoveryFolder, dataMiner);
    }

    public void saveFiles() {
        recoveryFolderManager.writePostFile();
        recoveryFolderManager.writePostItFiles();
    }

    public boolean deleteDatabaseData() {
        boolean success = QueryUtils.tryRemovePostFromDB(dataMiner, recoveryFolderManager.getOldPost());

        for (PostIt postIt : recoveryFolderManager.getOldPostIts())
            success = success && QueryUtils.tryRemovePostItFromDB(dataMiner, postIt);

        return success;
    }

}