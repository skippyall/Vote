package io.github.skippyall.vote.core.user.storage;

import io.github.skippyall.vote.core.user.AuthID;
import io.github.skippyall.vote.core.user.User;

import java.util.WeakHashMap;
import java.util.function.Consumer;

public class SyncedUserStorage implements UserStorage {
    private final WeakHashMap<Long, User> userCache = new WeakHashMap<>();

    private final Consumer<User> listener = this::pushUser;

    @Override
    public long addUser(User user) {
        return 0;
    }

    @Override
    public void addAuthID(User user, AuthID id) {

    }

    @Override
    public User getUser(long id) {
        if(userCache.containsKey(id)){
            return userCache.get(id);
        } else {
            User user = new User(id);
            userCache.put(id, user);
            user.registerUpdateListener(listener);
            return user;
        }
    }

    @Override
    public User getUser(AuthID id) {
        return null;
    }

    public void pushUser(User user) {

    }
}
