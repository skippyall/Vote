package de.skipall.vote.storage;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.Map;

public class Vote {
    private String name;
    private String displayName;
    private List<Comment> discussion;
    private OfflinePlayer owner;
    private Map<OfflinePlayer,VoteOption> votes;

    public Vote(String name, OfflinePlayer owner, String displayName) {
        this.name = name;
        this.owner = owner;
        this.displayName = displayName;
    }

    public void save(ConfigurationSection config){
        config.set(name, this);
    }

    public static Vote load(ConfigurationSection config, String name){
        return config.getObject(name,Vote.class);
    }

    public OfflinePlayer getOwner() {
        return owner;
    }

    public boolean isOwner(OfflinePlayer p) {
        return owner.getUniqueId().equals(p.getUniqueId());
    }

    public String getName() {
        return name;
    }

    public void addVote(OfflinePlayer p, VoteOption option){
        votes.put(p, option);
    }

    public String getDisplayName() {
        return displayName;
    }
}