package io.github.skippyall.vote.core.user.storage;

import io.github.skippyall.vote.core.storage.SQLStorage;
import io.github.skippyall.vote.core.user.AuthID;
import io.github.skippyall.vote.core.user.User;
import io.github.skippyall.vote.core.user.storage.AbstractUserStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUserStorage implements UserStorage {
    @Override
    public void load() {
        SQLStorage.execute("""
             CREATE TABLE IF NOT EXISTS users (
                    id INTEGER AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(20)
             """);
    }

    @Override
    public void save() {

    }

    @Override
    public long addUser(User user) {
        try(PreparedStatement statement = SQLStorage.prepareStatement(
                "INSERT INTO users(name) VALUES(?)")){
            statement.setString(1, user.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try(ResultSet set = SQLStorage.executeQuery("SELECT MAX(id) FROM users")){
            return set.getLong("id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addAuthID(User user, AuthID id) {

    }

    @Override
    public User getUser(long id) {
        try(PreparedStatement statement = SQLStorage.prepareStatement("SELECT name FROM users WHERE id = ?")){
            statement.setLong(0, id);
            try (ResultSet set = statement.executeQuery()) {
                String name = set.getString("name");
                return new User(id, name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUser(AuthID id) {
        return null;
    }
}
