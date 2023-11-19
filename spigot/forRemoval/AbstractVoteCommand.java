package forRemoval;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.skipall.vote.core.misc.EnvironmentVars;
import de.skipall.vote.core.storage.Vote;
import de.skipall.vote.core.storage.VoteStorage;
import de.skipall.vote.core.user.User;
import de.skipall.vote.misc.MinecraftSession;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;

import java.util.function.Consumer;

public class AbstractVoteCommand {
    public static <PlayerType> void register(CommandDispatcher<PlayerType> dispatcher) {
        dispatcher.register(
                LiteralArgumentBuilder.<PlayerType>literal("vote")
                        .then(LiteralArgumentBuilder.<PlayerType>literal("add")
                                .executes((context)->{
                                    PlayerType player = context.getSource();
                                    return 0;
                                })
                        )
                        .then(LiteralArgumentBuilder.<PlayerType>literal("remove"))
                        .then(LiteralArgumentBuilder.<PlayerType>literal("show"))
                        .then(LiteralArgumentBuilder.<PlayerType>literal("hide"))
                        .then(LiteralArgumentBuilder.<PlayerType>literal("list"))
            )
    }
    public static void add(MinecraftSession session, String name, String displayName){
        if (VoteStorage.containsVote(name)) {
            player.sendMessage("Vote already exists");
            return;
        }
        VoteStorage.addVote(new Vote(name, session.getUser(), displayName));
    }

    public static void remove(MinecraftSession session, String name){
        Vote vote = VoteStorage.getVote(name);
        User user = session.getUser();
        if (vote.isOwner(user) || user.canEditOther()) {
            VoteStorage.removeVote(name);
        }
    }

    public static void show(MinecraftSession session, String name){
        EnvironmentVars.logger.info(session.getUser().getName() + " showed " + name);
    }

    public static void hide(MinecraftSession player){

    }

    public static void list(MinecraftSession player){
        for (Vote v: VoteStorage.getVotes()) {
            TextComponent text = Component.text(v.getDisplayName());
            text.clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/vote show " + v.getName()));
            text.hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, Component.text(v.getName())));
            player.sendMessage(text);
        }
    }

    public static void getEditableVoteSuggestions(MinecraftSession session, Consumer<String> suggestionConsumer) {
        boolean editOther = session.getUser().canEditOther();
        if (!session.getUser().isBot()) {
            for (Vote v: VoteStorage.getVotes()) {
                if (v.isOwner(session.getUser()) || editOther) {
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