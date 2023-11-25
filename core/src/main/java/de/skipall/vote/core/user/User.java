package de.skipall.vote.core.user;

import java.util.HashMap;

public class User {

    boolean isBot;
    boolean canEditOther;
    long id;
    String name;


    public User(boolean isBot, boolean canEditOther, long id, String name) {
        this.isBot = isBot;
        this.canEditOther = canEditOther;
        this.id = id;
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

    public static User createUser(boolean isBot, boolean canEditOther, String name) {
        return UserStorage.createUser(isBot, canEditOther, name);
    }

    public static User getUser(long id) {
        return UserStorage.getUser(id);
    }
}
