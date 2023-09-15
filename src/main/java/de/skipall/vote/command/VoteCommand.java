package de.skipall.vote.command;

import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import de.skipall.vote.VotePlugin;
import de.skipall.vote.storage.Vote;
import de.skipall.vote.storage.VoteStorage;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.SuggestionInfo;
import dev.jorel.commandapi.arguments.GreedyStringArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public class VoteCommand {
    public static void initCommands(){
        new CommandAPICommand("vote")
                .withSubcommands(
                        new CommandAPICommand("add")
                                .withPermission("vote.add")
                                .withAliases("open")
                                .withArguments(new StringArgument("name"))
                                .withOptionalArguments(new GreedyStringArgument("displayName"))
                                .executesPlayer((player,args)->{add(player,(String)args.get("name"), (String)args.get("displayName"));}),
                        new CommandAPICommand("remove")
                                .withPermission("vote.remove")
                                .withAliases("close")
                                .withArguments(new StringArgument("name").replaceSuggestions(VoteCommand::getEditableVoteSuggestions))
                                .executesPlayer((player,args)->{remove(player,(String)args.get("name"));}),
                        new CommandAPICommand("show")
                                .withPermission("vote.show")
                                .withArguments(new StringArgument("name").replaceSuggestions(VoteCommand::getVoteSuggestions))
                                .executesPlayer((player,args)->{show(player,(String)args.get("name"));}),
                        new CommandAPICommand("hide")
                                .withPermission("vote.hide")
                                .executesPlayer((player,args)->{hide(player);}),
                        new CommandAPICommand("list")
                                .withPermission("vote.list")
                                .executesPlayer((player,args)->{list(player);})
                ).register();
    }

    public static void add(Player player, String name, String displayName){
        if (VoteStorage.containsVote(name)) {
            player.sendMessage("Vote already exists");
            return;
        }
        VoteStorage.addVote(new Vote(name, player, displayName));
    }

    public static void remove(Player player, String name){
        Vote vote = VoteStorage.getVote(name);
        if (vote.isOwner(player) || player.hasPermission("vote.remove.other")) {
            VoteStorage.removeVote(name);
        }
    }

    public static void show(Player player, String name){
        VotePlugin.votePlugin.getLogger().info(player.getDisplayName() + " showed " + name);
    }

    public static void hide(Player player){

    }

    public static void list(Player player){
        for (Vote v: VoteStorage.getVotes()) {
            TextComponent text = new TextComponent(v.getDisplayName());
            text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/vote show " + v.getName()));
            text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(v.getName())));
            player.spigot().sendMessage(text);
        }
    }

    public static CompletableFuture<Suggestions> getVoteSuggestions(SuggestionInfo<CommandSender> var1, SuggestionsBuilder var2) {
        for (Vote v: VoteStorage.getVotes()) {
            var2.suggest(v.getName());
        }
        return var2.buildFuture();
    }

    public static CompletableFuture<Suggestions> getEditableVoteSuggestions(SuggestionInfo<CommandSender> var1, SuggestionsBuilder var2) {
        boolean editOther = var1.sender().hasPermission("vote.remove.other");
        if (var1.sender() instanceof OfflinePlayer) {

            OfflinePlayer p = (OfflinePlayer)var1.sender();
            for (Vote v: VoteStorage.getVotes()) {
                if (v.isOwner(p) || editOther) {
                    var2.suggest(v.getName());
                }
            }
        } else if (var1.sender().hasPermission("vote.remove.other")) {
            for (Vote v: VoteStorage.getVotes()) {
                var2.suggest(v.getName());
            }
        }
        return var2.buildFuture();
    }
}
