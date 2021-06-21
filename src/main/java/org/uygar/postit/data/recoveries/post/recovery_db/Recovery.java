package org.uygar.postit.data.recoveries.post.recovery_db;

import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.data.recoveries.post.recovery_folder.RecoveryFolder;

public class Recovery<T extends RecoveryFolder> {

    protected T recoveryFolderManager;
    protected DataMiner dataMiner;

    public Recovery(T recoveryFolder, DataMiner dataMiner) {
        this.recoveryFolderManager = recoveryFolder;
        this.dataMiner = dataMiner;
    }

}