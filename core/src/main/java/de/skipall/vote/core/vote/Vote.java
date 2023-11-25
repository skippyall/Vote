package de.skipall.vote.core.vote;

import de.skipall.vote.core.user.User;

import java.util.Map;
import java.util.Set;

public class Vote {
    private String name;
    private String displayName;
    private Set<Comment> discussion;
    private User owner;
    private Map<User,VoteOption> votes;

    public Vote(String name, User owner, String displayName) {
        this.name = name;
        this.owner = owner;
        this.displayName = displayName;
    }

    public User getOwner() {
        return owner;
    }

    public boolean isOwner(User user) {
        return owner.getId()==user.getId();
    }

    public String getName() {
        return name;
    }

    public void addVote(User user, VoteOption option){
        votes.put(user, option);
    }

    public String getDisplayName() {
        return displayName;
    }

    public void discuss(Comment c) {
        discussion.add(c);
    }
}