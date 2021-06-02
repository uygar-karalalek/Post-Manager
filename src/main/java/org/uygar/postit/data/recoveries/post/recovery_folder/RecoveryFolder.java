package org.uygar.postit.data.recoveries.post.recovery_folder;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class RecoveryFolder extends File {

    public RecoveryFolder(@NotNull String pathname) throws IllegalArgumentException {
        super(pathname);
        if (this.isFile()) throw new IllegalArgumentException("Dev'essere una cartella!");
    }

}