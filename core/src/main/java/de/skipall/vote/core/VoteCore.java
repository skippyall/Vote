package de.skipall.vote.core;

import de.skipall.vote.core.storage.VoteStorage;

public class VoteCore {
    public static void init(){
        VoteStorage.loadVotes();
    }

    public static void shutdown(){
        VoteStorage.saveVotes();
    }
}
