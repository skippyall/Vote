package de.skipall.vote.core.action;

import de.skipall.vote.core.misc.EnvironmentVars;
import de.skipall.vote.core.vote.Vote;
import de.skipall.vote.core.vote.VoteStorage;
import de.skipall.vote.core.user.User;

import java.util.List;
import java.util.function.Consumer;

public class Actions {
    public static Result add(User player, String name, String displayName){
        if (VoteStorage.containsVote(name)) {
            //player.sendMessage("Vote already exists");
            return Result.ALREADY_EXIT;
        }
        VoteStorage.addVote(new Vote(name, player, displayName));
        return Result.NO_PROBLEM;
    }

    public static Result remove(User user, String name){
        Vote vote = VoteStorage.getVote(name);
        if (vote == null) {
            return Result.NOT_EXIST;

        }
        if (vote.isOwner(user) || user.canEditOther()) {
            VoteStorage.removeVote(name);
            return Result.NO_PROBLEM;
        } else {
            return Result.NO_RIGHTS;
        }
    }

    public static Result show(User player, String name){
        EnvironmentVars.logger.info(player.getName() + " showed " + name);
        return Result.NO_PROBLEM;
    }

    public static void hide(User player){

    }

    public static List<String> list(User player){
        return VoteStorage.getVotes().stream().map(e -> {
            return e.getDisplayName();
        }).toList();
        /**/
    }

    public static void getVoteSuggestions(Consumer<String> suggestionConsumer) {
        for (Vote v: VoteStorage.getVotes()) {
            suggestionConsumer.accept(v.getName());
        }
    }

    public static void getEditableVoteSuggestions(User user, Consumer<String> suggestionConsumer) {
        boolean editOther = user.canEditOther();
        if (!user.isBot()) {
            for (Vote v: VoteStorage.getVotes()) {
                if (v.isOwner(user) || editOther) {
                    suggestionConsumer.accept(v.getName());
                }
            }
        } else if (editOther) {
            for (Vote v: VoteStorage.getVotes()) {
                suggestionConsumer.accept(v.getName());
            }
        }
    }
}