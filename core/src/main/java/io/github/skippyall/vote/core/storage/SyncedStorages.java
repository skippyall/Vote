package io.github.skippyall.vote.core.storage;

import io.github.skippyall.vote.core.user.storage.SyncedUserStorage;
import io.github.skippyall.vote.core.vote.storage.SyncedVoteStorage;

public class SyncedStorages implements Storage{
    @Override
    public void load() {
        Storages.VOTE_STORAGE = new SyncedVoteStorage();
        Storages.USER_STORAGE = new SyncedUserStorage();
    }
}
