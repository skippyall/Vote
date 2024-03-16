package io.github.skippyall.vote.core.vote;

import io.github.skippyall.vote.core.user.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class Vote {
    private final long id;
    private String displayName;
    private Set<Comment> discussion;
    private final User owner;
    private Set<String> voteOptions = new HashSet<>();
    private Map<User,String> votes = new HashMap<>();

    private final Set<Consumer<Vote>> updateListeners = new HashSet<>();

    public Vote(User owner, long id) {
        this.owner = owner;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public User getOwner() {
        return owner;
    }

    public boolean isOwner(User user) {
        return owner.getId()==user.getId();
    }

    public boolean addVote(User user, String option){
        if(voteOptions.contains(option)) {
            votes.put(user, option);
            return true;
        } else {
            return false;
        }
    }

    public String getDisplayName() {
        return displayName != null ? displayName : String.valueOf(id);
    }

    public void discuss(Comment c) {
        discussion.add(c);
    }

    public Set<Comment> getDiscussion() {
        return discussion;
    }

    public void copyInfo(Vote other) {
        if(!(
                displayName.equals(other.displayName)
                && discussion.equals(other.discussion)
                && votes.equals(other.votes)
        )) {
            displayName = other.displayName;
            discussion = other.discussion;
            votes = other.votes;
            fireUpdateEvent();
        }
    }

    public void registerUpdateListener(Consumer<Vote> listener) {
        updateListeners.add(listener);
    }

    private void fireUpdateEvent(){
        for (Consumer<Vote> listener : updateListeners) {
            listener.accept(this);
        }
    }
}