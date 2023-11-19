package de.skipall.vote.core.user;

import java.util.HashMap;

public class User {
    private static long idCounter=0;
    boolean isBot;
    boolean canEditOther;
    long id;
    String name;
    private static HashMap<long,User> users=new HashMap<long, User>();

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

    public void setUuid(long uuid) {
        this.id = uuid;
    }

    public static User createUser(boolean isBot, boolean canEditOther, String name) {
        User user = new User(isBot, canEditOther, newId(), name);
        users.put(user.getId(),user);
        return user;
    }

    public static User getUser(long id) {
        return users.get(id);
    }

    private static long newId(){
        idCounter++;
        return idCounter;
    }
}
