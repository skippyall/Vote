package io.github.skippyall.vote.core.storage;

import io.github.skippyall.vote.core.config.Config;
import io.github.skippyall.vote.core.user.storage.UserStorage;
import io.github.skippyall.vote.core.vote.storage.VoteStorage;

public class Storages {
    public static Storage STORAGE_INIT;
    public static UserStorage USER_STORAGE;
    public static VoteStorage VOTE_STORAGE;

    public static void loadServer() {
        STORAGE_INIT = Config.getStorageType().storage;
        load();
    }

    public static void loadClient() {
        STORAGE_INIT = StorageType.SyncedStorage.storage;
        load();
    }

    public static void load() {
        STORAGE_INIT.load();
        USER_STORAGE.load();
        VOTE_STORAGE.load();
    }

    public static void save() {
        STORAGE_INIT.save();
        USER_STORAGE.save();
        VOTE_STORAGE.save();
    }
}
