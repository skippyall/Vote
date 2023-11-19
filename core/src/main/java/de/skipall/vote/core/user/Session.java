package de.skipall.vote.core.user;

public class Session {
    private User user;
    public Session(User user){
        this.user=user;
    }

    public User getUser(){
        return user;
    }

    public void sendMessage(String s) {

    }
}
