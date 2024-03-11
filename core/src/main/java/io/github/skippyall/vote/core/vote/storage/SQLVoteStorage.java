package io.github.skippyall.vote.core.vote.storage;

import io.github.skippyall.vote.core.storage.SQLStorage;
import io.github.skippyall.vote.core.vote.Vote;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SQLVoteStorage implements VoteStorage {
    @Override
    public void load() {
        SQLStorage.execute("""
             CREATE TABLE IF NOT EXISTS votes (
                    id INTEGER AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(20),
                    title VARCHAR(50),
                    desc TEXT,
                    possibilities SET,
                    owner INTEGER
             """);
    }

    @Override
    public void save() {
    }

    private static Vote toVote(ResultSet set) {

    }

    @Override
    public Collection<Vote> getVotes() {
        try (ResultSet set = SQLStorage.executeQuery("SELECT name, title, desc, possibilities, owner FROM votes")){
            Set<Vote> votes = new HashSet<>();
            while (set.next()){
                votes.add(toVote(set));
            }
            return votes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<String> getNames() {
        try (ResultSet set = SQLStorage.executeQuery("SELECT cmdid FROM votes")) {
            Collection<String> ids = new HashSet<>();
            while (set.next()){
                ids.add(set.getString("cmdid"));
            }
            return ids;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Vote getVote(int id) {
        return null;
    }

    @Override
    public boolean containsVote(int id) {
        return false;
    }

    @Override
    public void addVote(Vote v) {

    }

    @Override
    public void removeVote(int id) {

    }
}
