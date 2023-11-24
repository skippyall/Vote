package de.skipall.vote.spigot.command;

import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import de.skipall.vote.core.command.AbstractVoteCommand;
import de.skipall.vote.core.misc.Result;
import de.skipall.vote.core.storage.Vote;
import de.skipall.vote.core.storage.VoteStorage;
import de.skipall.vote.spigot.misc.SpigotUtil;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.SuggestionInfo;
import dev.jorel.commandapi.arguments.GreedyStringArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public class SpigotVoteCommand extends AbstractVoteCommand{
    public SpigotVoteCommand() {
        initCommands();
    }

    private void initCommands(){
        new CommandAPICommand("vote")
                .withSubcommands(
                        new CommandAPICommand("add")
                                .withPermission("vote.add")
                                .withAliases("open")
                                .withArguments(new StringArgument("name"))
                                .withOptionalArguments(new GreedyStringArgument("displayName"))
                                .executes((sender,args)->{add(sender, args);}),
                        new CommandAPICommand("remove")
                                .withPermission("vote.remove")
                                .withAliases("close")
                                .withArguments(new StringArgument("name").replaceSuggestions(SpigotVoteCommand::getEditableVoteSuggestions))
                                .executes((player,args)->{remove(SpigotUtil.toUser(player),(String)args.get("name"));}),
                        new CommandAPICommand("show")
                                .withPermission("vote.show")
                                .withArguments(new StringArgument("name").replaceSuggestions(SpigotVoteCommand::getVoteSuggestions))
                                .executes((player,args)->{show(SpigotUtil.toUser(player),(String)args.get("name"));}),
                        new CommandAPICommand("hide")
                                .withPermission("vote.hide")
                                .executes((player,args)->{hide(SpigotUtil.toUser(player));}),
                        new CommandAPICommand("list")
                                .withPermission("vote.list")
                                .executes((player,args)->{list(SpigotUtil.toUser(player));})
                ).register();
    }

    private static void add(CommandSender sender, CommandArguments args) {
        Result result = add(SpigotUtil.toUser(sender),(String)args.get("name"), (String)args.get("displayName"));
        if (result == Result.NO_PROBLEM) {
            sender.sendMessage("[Vote] Vote successfully created");
        } else if (result == Result.ALREADY_EXIT) {
            sender.sendMessage("[Vote] " + ChatColor.RED + "Vote successfully created");
        }
    }

    private static void remove(CommandSender sender, CommandArguments args) {
        Result result = add(SpigotUtil.toUser(sender),(String)args.get("name"), (String)args.get("displayName"));
        if (result == Result.NO_PROBLEM) {
            sender.sendMessage("[Vote] Vote successfully created");
        } else if (result == Result.ALREADY_EXIT) {
            sender.sendMessage("[Vote] " + ChatColor.RED + "Vote successfully created");
        }
    }

    public static CompletableFuture<Suggestions> getVoteSuggestions(SuggestionInfo<CommandSender> info, SuggestionsBuilder builder) {
        info.sender().sendMessage("suggestion1");
        getVoteSuggestions(e -> builder.suggest(e));
        return builder.buildFuture();
    }

    public static CompletableFuture<Suggestions> getEditableVoteSuggestions(SuggestionInfo<CommandSender> var1, SuggestionsBuilder var2) {
        getEditableVoteSuggestions(SpigotUtil.toUser(var1.sender()), e -> var2.suggest(e));
        return var2.buildFuture();
    }
}
