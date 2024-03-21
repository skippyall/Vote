package io.github.skippyall.vote.core.vote.storage;


/*
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.skippyall.vote.core.misc.EnvironmentVars;
import io.github.skippyall.vote.core.vote.Vote;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;*/

public class JsonVoteStorage {
    /*private static final File file= new File(EnvironmentVars.dataFolder,"votes.json");
    private static final Gson gson = new Gson();
    private static Map<String, Vote> votes;
    public static void loadVotes() {
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try (FileReader reader = new FileReader(file)){
            votes=gson.fromJson(reader, new TypeToken<Map<String,Vote>>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveVotes(){
        try(FileWriter writer = new FileWriter(file)) {
            String json = gson.toJson(votes);
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Collection<Vote> getVotes() {
        return votes.values();
    }

    public static Map<String,Vote> getVoteMap() {
        return votes;
    }

    public static Vote getVote(String name) {
        return votes.get(name);
    }

    public static boolean containsVote(String name) {
        return votes.containsKey(name);
    }

    public static void addVote(Vote v) {
        votes.put(v.getName(),v);
    }

    public static void removeVote(String name) {
        votes.remove(name);
    }

    public static void removeVote(Vote vote) {
        votes.forEach((name, v) -> {
            if (v == vote) {
                votes.remove(name);
            }
        });
    }*/
}
