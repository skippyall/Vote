package io.github.skippyall.vote.core.vote.storage;

import io.github.skippyall.vote.core.user.User;
import io.github.skippyall.vote.core.vote.Vote;

import java.util.Collection;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Consumer;

public class SyncedVoteStorage implements VoteStorage {
    private final WeakHashMap<Long, Vote> voteCache = new WeakHashMap<>();
    private final Consumer<Vote> listener = this::pushVote;

    @Override
    public Vote getVote(long id) {
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
    public Map<Long, String> getVoteTitles() {
        return null;
    }

    @Override
    public boolean containsVote(long id) {
        if(voteCache.containsKey(id)){
            return true;
        } else {
            return storageContainsVote(id);
        }
    }

    @Override
    public Vote createVote(User owner) {
        return null;
    }

    @Override
    public void addVote(Vote vote) {
        voteCache.put(vote.getId(), vote);
        vote.registerUpdateListener(listener);
    }

    @Override
    public void removeVote(long id) {

    }

    public Vote loadVote(long id) {
        return null;//TODO
    }

    public Collection<Vote> loadVotes() {
        return null;//TODO
    }

    public void pushVote(Vote vote) {

    }

    public boolean storageContainsVote(long id) {
        return false;//TODO
    }
}
