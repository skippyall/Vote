package de.skipall.vote.core;

import de.skipall.vote.core.user.UserStorage;
import de.skipall.vote.core.vote.VoteStorage;

public class VoteCore {
    public static void init(){
        VoteStorage.loadVotes();
        UserStorage.load();
    }

    public static void shutdown(){
        VoteStorage.saveVotes();
        UserStorage.save();
    }
}
