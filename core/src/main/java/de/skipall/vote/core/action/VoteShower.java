package de.skipall.vote.core.action;

import de.skipall.vote.core.user.Session;
import de.skipall.vote.core.vote.Vote;

public interface VoteShower<T extends Session> {
    public void showVote(Vote vote, T session);
    public void hideVote();
}
