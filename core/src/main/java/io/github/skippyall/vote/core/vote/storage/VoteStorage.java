package io.github.skippyall.vote.core.vote.storage;

import io.github.skippyall.vote.core.storage.Storage;
import io.github.skippyall.vote.core.vote.Vote;

import java.util.Collection;

public interface VoteStorage extends Storage {
    Collection<Vote> getVotes();

    Collection<String> getNames();

    Vote getVote(int id);

    boolean containsVote(int id);

    void addVote(Vote v);

    void removeVote(int id);
}
