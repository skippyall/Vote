package de.skipall.vote.core.vote;

import de.skipall.vote.core.user.User;

public class Comment {
    private User writer;
    private String text;

    public Comment(User writer, String text){
        this.text = text;
        this.writer = writer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text){
        this.text = text;
    }

    public User getWriter() {
        return writer;
    }
}
