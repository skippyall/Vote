package io.github.skippyall.vote.core.action;

import io.github.skippyall.vote.core.VoteServer;
import io.github.skippyall.vote.core.vote.Vote;
import io.github.skippyall.vote.core.vote.storage.JsonVoteStorage;
import io.github.skippyall.vote.core.user.User;

import java.util.List;
import java.util.function.Consumer;

public class Actions {
    public static Result add(User player, String name, String displayName){
        if (JsonVoteStorage.containsVote(name)) {
            //player.sendMessage("Vote already exists");
            return Result.ALREADY_EXIT;
        }
        JsonVoteStorage.addVote(new Vote(name, player, displayName));
        return Result.NO_PROBLEM;
    }

    public static Result remove(User user, String name){
        Vote vote = JsonVoteStorage.getVote(name);
        if (vote == null) {
            return Result.NOT_EXIST;

        }
        if (vote.isOwner(user) || user.canEditOther()) {
            JsonVoteStorage.removeVote(name);
            return Result.NO_PROBLEM;
        } else {
            return Result.NO_RIGHTS;
        }
    }

    public static Result show(User player, String name){
        VoteServer.LOGGER.info(player.getName() + " showed " + name);
        return Result.NO_PROBLEM;
    }

    public static void hide(User player){

    }

    public static List<String> list(User player){
        return JsonVoteStorage.getVotes().stream().map(e -> {
            return e.getDisplayName();
        }).toList();
    }

    public static void getVoteSuggestions(Consumer<String> suggestionConsumer) {
        for (Vote v: JsonVoteStorage.getVotes()) {
            suggestionConsumer.accept(v.getName());
        }
    }

    public static void getEditableVoteSuggestions(User user, Consumer<String> suggestionConsumer) {
        boolean editOther = user.canEditOther();
        if (!user.isBot()) {
            for (Vote v: JsonVoteStorage.getVotes()) {
                if (v.isOwner(user) || editOther) {
                    suggestionConsumer.accept(v.getName());
                }
            }
        } else if (editOther) {
            for (Vote v: JsonVoteStorage.getVotes()) {
                suggestionConsumer.accept(v.getName());
            }
        }
    }
}