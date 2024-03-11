package io.github.skippyall.vote.core.user.storage;

import io.github.skippyall.vote.core.storage.Storage;
import io.github.skippyall.vote.core.user.AuthID;
import io.github.skippyall.vote.core.user.User;

public interface UserStorage extends Storage {

    long addUser(User user);

    void addAuthID(User user, AuthID id);

    User getUser(long id);

    User getUser(AuthID id);
}
