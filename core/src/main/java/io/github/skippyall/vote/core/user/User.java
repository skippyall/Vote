package io.github.skippyall.vote.core.user;

import io.github.skippyall.vote.core.vote.Vote;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class User {

    boolean isBot;
    boolean canEditOther;
    long id;
    String name;

    private final Set<Consumer<User>> updateListeners = new HashSet<>();

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean isBot() {
        return isBot;
    }

    public boolean canEditOther() {
        return canEditOther;
    }

    public long getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public void setBot(boolean user) {
        isBot = user;
    }

    public void setCanEditOther(boolean canEditOther) {
        this.canEditOther = canEditOther;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void copyInfo(User other) {
        if(!(
                isBot == other.isBot
                && canEditOther == other.canEditOther
                && name.equals(other.name)
        )) {
            isBot = other.isBot;
            canEditOther = other.canEditOther;
            name = other.name;
            fireUpdateEvent();
        }
    }

    public void registerUpdateListener(Consumer<User> listener) {
        updateListeners.add(listener);
    }

    private void fireUpdateEvent(){
        for (Consumer<User> listener : updateListeners) {
            listener.accept(this);
        }
    }

    /*public static User createUser(boolean isBot, boolean canEditOther, String name) {
        return JSONUserStorage.createUser(isBot, canEditOther, name);
    }

    public static User getUser(long id) {
        return JSONUserStorage.getUser(id);
    }*/
}
