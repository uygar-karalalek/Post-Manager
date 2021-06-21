package org.uygar.postit.data.recoveries.post.recovery_folder;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class RecoveryFolder extends File {

    public RecoveryFolder(@NotNull String absPath) throws IllegalArgumentException {
        super(absPath);
        if (this.isFile()) throw new IllegalArgumentException("Dev'essere una cartella!");
    }

    protected String fromParentPath(String subPath) {
        return getPath() + "/" + subPath;
    }

    protected String fromParentPath(String parentPath, String subPath) {
        return parentPath + "/" + subPath;
    }

}