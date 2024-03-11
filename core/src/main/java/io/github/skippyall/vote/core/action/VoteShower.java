package io.github.skippyall.vote.core.action;

import io.github.skippyall.vote.core.user.Session;
import io.github.skippyall.vote.core.vote.Vote;

public interface VoteShower<T extends Session> {
    public void showVote(Vote vote, T session);
    public void hideVote();
}
