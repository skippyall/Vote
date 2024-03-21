package io.github.skippyall.vote.core.storage;

import io.github.skippyall.vote.core.misc.EnvironmentVars;

import java.io.File;

public enum StorageType {
    SQLite(new SQLStorage("jdbc:sqlite:" + new File(EnvironmentVars.dataFolder, "database.db").getAbsolutePath())),
    MySQL(new SQLStorage("?")),
    SyncedStorage(new SyncedStorages());

    public final Storage storage;
    StorageType(Storage storage){
        this.storage = storage;
    }
}
