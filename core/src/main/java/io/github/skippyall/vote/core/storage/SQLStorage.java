package io.github.skippyall.vote.core.storage;

import io.github.skippyall.vote.core.user.storage.SQLUserStorage;

import java.sql.*;

public class SQLStorage implements Storage{
    private final String url;
    public SQLStorage(String url){
        this.url = url;
    }
    public Connection connection;

    @Override
    public void load() {
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database", e);
        }
        Storages.USER_STORAGE = new SQLUserStorage();
    }

    @Override
    public void save() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to close database", e);
        }
    }

    public static Connection getConnection() {
        return ((SQLStorage)Storages.STORAGE_INIT).connection;
    }

    public static Statement createStatement() {
        try {
            return getConnection().createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement prepareStatement(String command) {
        try {
            return getConnection().prepareStatement(command);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean execute(String command) {
        try(Statement statement = createStatement()) {
            return statement.execute(command);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet executeQuery(String command) {
        try(Statement statement = createStatement()) {
            return statement.executeQuery(command);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
