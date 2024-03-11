package io.github.skippyall.vote.core.vote.storage;

import io.github.skippyall.vote.core.storage.SyncedStorage;
import io.github.skippyall.vote.core.vote.Vote;

import java.util.Collection;
import java.util.WeakHashMap;
import java.util.function.Consumer;

public class SyncedVoteStorage implements VoteStorage, SyncedStorage {
    private final WeakHashMap<Integer, Vote> voteCache = new WeakHashMap<>();
    private final Consumer<Vote> listener = this::pushVote;

    @Override
    public Vote getVote(int id) {
        if(voteCache.containsKey(id)){
            return voteCache.get(id);
        } else {
            Vote vote = loadVote(id);
            voteCache.put(vote.getId(), vote);
            vote.registerUpdateListener(listener);
            return vote;
        }
    }

    @Override
    public Collection<Vote> getVotes() {
        Collection<Vote> votes = loadVotes();
        for(Vote vote : votes) {
            voteCache.put(vote.getId(), vote);
            vote.registerUpdateListener(listener);
        }
        return votes;
    }

    @Override
    public boolean containsVote(int id) {
        if(voteCache.containsKey(id)){
            return true;
        } else {
            return storageContainsVote(id);
        }
    }

    @Override
    public void addVote(Vote v) {

    }

    @Override
    public void removeVote(int id) {

    }

    public Vote loadVote(int id) {

    }

    public Collection<Vote> loadVotes() {

    }

    public void pushVote(Vote vote) {

    }

    public boolean storageContainsVote(int id) {

    }

    public Collection<String> getNames() {

    }

    @Override
    public void sync(){
        voteCache.values().forEach(vote -> {
            Vote updatedVote = loadVote(vote.getId());
            vote.copyInfo(updatedVote);
        });
    }
}
