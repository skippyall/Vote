package io.github.skippyall.vote.core.vote.storage;

import io.github.skippyall.vote.core.storage.SQLStorage;
import io.github.skippyall.vote.core.storage.Storages;
import io.github.skippyall.vote.core.user.User;
import io.github.skippyall.vote.core.vote.Vote;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SQLVoteStorage implements VoteStorage {
    @Override
    public void load() {
        SQLStorage.execute("""
             CREATE TABLE IF NOT EXISTS votes (
                    id INTEGER AUTO_INCREMENT PRIMARY KEY,
                    title VARCHAR(50),
                    desc TEXT,
                    possibilities SET,
                    owner INTEGER,
                    createDate DATE
             )
             """);
    }

    @Override
    public void save() {

    }

    private static Vote toVote(ResultSet set) {
        try {
            long id = set.getLong("id");
            String title = set.getString("title");
            String desc = set.getString("desc");
//            Set desc = set.getString("desc");  TODO
            User owner = Storages.USER_STORAGE.getUser(set.getLong("owner"));
            Date createDate = set.getDate("createDate");
            Vote vote = new Vote(owner, id);
            vote.setDisplayName(title);
            return vote;
        }catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Collection<Vote> getVotes() {
        try (ResultSet set = SQLStorage.executeQuery("SELECT id, title, desc, possibilities, owner, createDate FROM votes")){
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
    public Map<Long, String> getVoteTitles() {
        return null;
    }

    @Override
    public Vote getVote(long id) {
        return null;
    }

    @Override
    public boolean containsVote(long id) {
        return false;
    }

    @Override
    public Vote createVote(User owner) {
        try(PreparedStatement statement = SQLStorage.prepareStatement(
                "INSERT INTO votes(owner) VALUES(?)")){
            statement.setLong(1, owner.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try(ResultSet set = SQLStorage.executeQuery("SELECT MAX(id) FROM users")){
            return new Vote(owner, set.getLong("id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeVote(long id) {

    }
}
