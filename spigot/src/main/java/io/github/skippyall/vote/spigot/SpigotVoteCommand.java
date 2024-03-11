package io.github.skippyall.vote.spigot;

import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.SuggestionInfo;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.GreedyStringArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.skippyall.vote.core.action.Actions;
import io.github.skippyall.vote.core.action.Result;
import io.github.skippyall.vote.spigot.misc.SpigotSession;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.command.CommandSender;

import java.util.concurrent.CompletableFuture;

public class SpigotVoteCommand {
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
                                .executes(SpigotVoteCommand::add),
                        new CommandAPICommand("remove")
                                .withPermission("vote.remove")
                                .withAliases("close")
                                .withArguments(new StringArgument("name").replaceSuggestions(SpigotVoteCommand::getEditableVoteSuggestions))
                                .executes(SpigotVoteCommand::remove),
                        new CommandAPICommand("show")
                                .withPermission("vote.show")
                                .withArguments(new StringArgument("name").replaceSuggestions(SpigotVoteCommand::getVoteSuggestions))
                                .executes(SpigotVoteCommand::show),
                        new CommandAPICommand("hide")
                                .withPermission("vote.hide")
                                .executes(SpigotVoteCommand::hide),
                        new CommandAPICommand("list")
                                .withPermission("vote.list")
                                .executes(SpigotVoteCommand::list)
                ).register();
    }

    private static void add(CommandSender sender, CommandArguments args) {
        Result result = Actions.add(SpigotSession.findUser(sender),(String)args.get("name"), (String)args.get("displayName"));
        if (result == Result.NO_PROBLEM) {
            sender.sendMessage("[VoteSpigot] VoteSpigot successfully created");
        } else if (result == Result.ALREADY_EXIT) {
            sender.sendMessage("[VoteSpigot] " + ChatColor.RED + "VoteSpigot successfully created");
        }
    }

    private static void remove(CommandSender sender, CommandArguments args) {
        Result result = Actions.remove(SpigotSession.findUser(sender),(String)args.get("name"));
        if (result == Result.NO_PROBLEM) {
            sender.sendMessage("[VoteSpigot] VoteSpigot successfully removed");
        } else if (result == Result.NOT_EXIST) {
            sender.sendMessage("[VoteSpigot] " + ChatColor.RED + "VoteSpigot not exist");
        } else if (result == Result.NO_RIGHTS) {
            sender.sendMessage("[VoteSpigot] " + ChatColor.RED + "No Rights to delete");
        }
    }

    private static void show(CommandSender sender, CommandArguments args) {
        Result result = Actions.show(SpigotSession.findUser(sender),(String)args.get("name"));
        /*if (result == Result.NO_PROBLEM) {
            sender.sendMessage("[VoteSpigot] VoteSpigot successfully removed");
        } else if (result == Result.NOT_EXIST) {
            sender.sendMessage("[VoteSpigot] " + ChatColor.RED + "VoteSpigot not exist");
        } else if (result == Result.NO_RIGHTS) {
            sender.sendMessage("[VoteSpigot] " + ChatColor.RED + "No Rights to delete");
        }*/
    }

    private static void hide(CommandSender sender, CommandArguments args){

    }

    private static void list(CommandSender sender, CommandArguments args){
        for (String name: Actions.list(SpigotSession.findUser(sender))) {
            TextComponent text = new TextComponent(name);
            text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/vote show " + name));
            text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(name)));
            sender.spigot().sendMessage(text);
        }
    }

    public static CompletableFuture<Suggestions> getVoteSuggestions(SuggestionInfo<CommandSender> info, SuggestionsBuilder builder) {
        info.sender().sendMessage("suggestion1");
        Actions.getVoteSuggestions(builder::suggest);
        return builder.buildFuture();
    }

    public static CompletableFuture<Suggestions> getEditableVoteSuggestions(SuggestionInfo<CommandSender> var1, SuggestionsBuilder var2) {
        Actions.getEditableVoteSuggestions(SpigotSession.findUser(var1.sender()), var2::suggest);
        return var2.buildFuture();
    }
}
