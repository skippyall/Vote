package de.skipall.vote.storage;

import de.skipall.vote.VotePlugin;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class VoteStorage {
    private static FileConfiguration config;

    private static final File file= new File(VotePlugin.votePlugin.getDataFolder(),"Vote/votes.yml");
    private static ConfigurationSection votesection;
    private static List<Vote> votes = new ArrayList<>();
    public static void loadVotes() {
        config = YamlConfiguration.loadConfiguration(file);
        votesection = config.getConfigurationSection("votes");
        votesection.getKeys(false).forEach((name) -> {
            votes.add(Vote.load(votesection,name));
        });
    }

    public static void saveVotes(){
        votesection = config.createSection("votes");
        votes.forEach(e -> {
            e.save(votesection);
        });
        try {
            config.save(file);
        } catch (IOException e) {
        }
    }

    public static List<Vote> getVotes() {
        return votes;
    }

    public static Vote getVote(String name) {
        if (!containsVote(name)) return null;
        return (Vote) votes.stream().filter(e -> {
            return e.getName().equals(name);
        }).toArray()[0];
    }

    public static boolean containsVote(String name) {
        return votes.stream().anyMatch(e -> {
            return e.getName().equals(name);
        });
    }

    public static void addVote(Vote v) {
        votes.add(v);
    }

    public static void removeVote(String name) {
        AtomicReference<Vote> delvote = new AtomicReference<>();
        votes.forEach(vote -> {
            if(vote.getName().equals(name)){
                delvote.set(vote);
            }
        });
        if (delvote.get() != null) removeVote(delvote.get());
    }

    public static void removeVote(Vote vote) {
        votes.remove(vote);
    }
}
