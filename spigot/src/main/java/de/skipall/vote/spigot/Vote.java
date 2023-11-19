package de.skipall.vote.spigot;

import de.skipall.vote.core.VoteCore;
import de.skipall.vote.core.misc.EnvironmentVars;
import de.skipall.vote.spigot.command.SpigotVoteCommand;
import de.skipall.vote.spigot.misc.EnvironmentInit;
import org.bukkit.plugin.java.JavaPlugin;
import sun.security.provider.ConfigFile;

public final class Vote extends JavaPlugin {
    public static Vote instance;
    public static SpigotVoteCommand voteCommand;
    @Override
    public void onEnable() {
        instance = this;
        EnvironmentVars.logger = this.getLogger();
        EnvironmentVars.dataFolder = this.getDataFolder();
        voteCommand = new SpigotVoteCommand();
        VoteCore.init();
    }

    @Override
    public void onDisable() {
        VoteCore.shutdown();
    }
}
