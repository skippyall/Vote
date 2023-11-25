package de.skipall.vote.spigot.command;

import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import de.skipall.vote.core.action.Actions;
import de.skipall.vote.core.action.Result;
import de.skipall.vote.spigot.misc.SpigotSession;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.SuggestionInfo;
import dev.jorel.commandapi.arguments.GreedyStringArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.concurrent.CompletableFuture;

public class SpigotVoteCommand extends Actions {
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
                                .executes((sender, args)-> {add(sender,args);}),
                        new CommandAPICommand("remove")
                                .withPermission("vote.remove")
                                .withAliases("close")
                                .withArguments(new StringArgument("name").replaceSuggestions(SpigotVoteCommand::getEditableVoteSuggestions))
                                .executes((sender, args) -> {remove(sender, args);}),
                        new CommandAPICommand("show")
                                .withPermission("vote.show")
                                .withArguments(new StringArgument("name").replaceSuggestions(SpigotVoteCommand::getVoteSuggestions))
                                .executes((sender,args)->{show(sender, args);}),
                        new CommandAPICommand("hide")
                                .withPermission("vote.hide")
                                .executes((sender,args)->{hide(SpigotSession.findUser(sender));}),
                        new CommandAPICommand("list")
                                .withPermission("vote.list")
                                .executes((sender,args)->{list(SpigotSession.findUser(sender));})
                ).register();
    }

    private static void add(CommandSender sender, CommandArguments args) {
        Result result = add(SpigotSession.findUser(sender),(String)args.get("name"), (String)args.get("displayName"));
        if (result == Result.NO_PROBLEM) {
            sender.sendMessage("[Vote] Vote successfully created");
        } else if (result == Result.ALREADY_EXIT) {
            sender.sendMessage("[Vote] " + ChatColor.RED + "Vote successfully created");
        }
    }

    private static void remove(CommandSender sender, CommandArguments args) {
        Result result = remove(SpigotSession.findUser(sender),(String)args.get("name"));
        if (result == Result.NO_PROBLEM) {
            sender.sendMessage("[Vote] Vote successfully removed");
        } else if (result == Result.NOT_EXIST) {
            sender.sendMessage("[Vote] " + ChatColor.RED + "Vote not exist");
        } else if (result == Result.NO_RIGHTS) {
            sender.sendMessage("[Vote] " + ChatColor.RED + "No Rights to delete");
        }
    }

    private static void show(CommandSender sender, CommandArguments args) {
        /*Result result = show(SpigotUtil.toUser(sender),(String)args.get("name"));
        if (result == Result.NO_PROBLEM) {
            sender.sendMessage("[Vote] Vote successfully removed");
        } else if (result == Result.NOT_EXIST) {
            sender.sendMessage("[Vote] " + ChatColor.RED + "Vote not exist");
        } else if (result == Result.NO_RIGHTS) {
            sender.sendMessage("[Vote] " + ChatColor.RED + "No Rights to delete");
        }*/
    }

    public static CompletableFuture<Suggestions> getVoteSuggestions(SuggestionInfo<CommandSender> info, SuggestionsBuilder builder) {
        info.sender().sendMessage("suggestion1");
        getVoteSuggestions(e -> builder.suggest(e));
        return builder.buildFuture();
    }

    public static CompletableFuture<Suggestions> getEditableVoteSuggestions(SuggestionInfo<CommandSender> var1, SuggestionsBuilder var2) {
        getEditableVoteSuggestions(SpigotSession.findUser(var1.sender()), e -> var2.suggest(e));
        return var2.buildFuture();
    }
}
