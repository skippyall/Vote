package de.skipall.vote.core.storage;

import de.skipall.vote.core.user.User;

import java.util.List;
import java.util.Map;

public class Vote {
    private String name;
    private String displayName;
    private List<Comment> discussion;
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
        return owner.getUuid().equals(user.getUuid());
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
}