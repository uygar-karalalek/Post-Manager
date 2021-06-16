package org.uygar.postit.data.recoveries.post;

import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.data.query_utils.QueryUtils;
import org.uygar.postit.data.recoveries.post.recovery_folder.reader.RecoveryReader;
import org.uygar.postit.post.PostIt;

public class RecoveryDBSaver {

    private RecoveryReader recoveryReader;
    private DataMiner dataMiner;

    public RecoveryDBSaver(RecoveryReader recoveryReader, DataMiner dataMiner) {
        this.recoveryReader = recoveryReader;
        this.dataMiner = dataMiner;
    }

    public boolean saveToDatabase() {
        boolean success = QueryUtils.tryCreateNewPostOnDB(dataMiner, this.recoveryReader.getNewPost());
        for (PostIt postIt : recoveryReader.getNewPostIts())
            success = success && QueryUtils.tryCreateNewPostItOnDB(dataMiner, postIt);
        return success;
    }

}