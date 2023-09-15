package de.skipall.vote;

import de.skipall.vote.command.VoteCommand;
import de.skipall.vote.storage.VoteStorage;
import org.bukkit.plugin.java.JavaPlugin;

public final class VotePlugin extends JavaPlugin {
    public static VotePlugin votePlugin;
    @Override
    public void onEnable() {
        VoteStorage.loadVotes();
    }

    @Override
    public void onLoad() {
        votePlugin =this;
        VoteCommand.initCommands();
    }

    @Override
    public void onDisable() {
        VoteStorage.saveVotes();
    }
}
