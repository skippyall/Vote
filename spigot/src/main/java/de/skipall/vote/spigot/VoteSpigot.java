package de.skipall.vote.spigot;

import de.skipall.vote.core.VoteCore;
import de.skipall.vote.core.misc.EnvironmentVars;
import de.skipall.vote.spigot.command.SpigotVoteCommand;
import jdk.javadoc.internal.doclint.Env;
import org.bukkit.plugin.java.JavaPlugin;

public final class VoteSpigot extends JavaPlugin {
    public static VoteSpigot instance;
    public static SpigotVoteCommand voteCommand;
    @Override
    public void onEnable() {
        instance = this;
        EnvironmentVars.logger = this.getLogger();
        EnvironmentVars.dataFolder = this.getDataFolder();
        EnvironmentVars.shower = new SpigotVoteShower();
        voteCommand = new SpigotVoteCommand();
        VoteCore.init();
    }

    @Override
    public void onDisable() {
        VoteCore.shutdown();
    }
}
