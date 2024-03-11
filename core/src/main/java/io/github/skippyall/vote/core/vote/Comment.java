package io.github.skippyall.vote.core.vote;

import io.github.skippyall.vote.core.user.User;

public class Comment {
    private final User writer;
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
