package io.github.skippyall.vote.core.vote.storage;

import io.github.skippyall.vote.core.storage.Storage;
import io.github.skippyall.vote.core.user.User;
import io.github.skippyall.vote.core.vote.Vote;

import java.util.Collection;
import java.util.Map;

public interface VoteStorage extends Storage {
    Collection<Vote> getVotes();

    Map<Long, String> getVoteTitles();

    Vote getVote(long id);

    boolean containsVote(long id);

    Vote createVote(User owner);

    void addVote(Vote vote);

    void removeVote(long id);
}
