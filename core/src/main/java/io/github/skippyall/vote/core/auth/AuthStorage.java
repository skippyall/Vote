package io.github.skippyall.vote.core.auth;

public interface AuthStorage extends Storage {
  long getUserId(AuthID id);
  Set<AuthID> getAuthIds(long user, String authType);
  void addAuthId(long user, AuthID id);
  void removeAuthId(long user, AuthID id);

  default User getUser(AuthID id){
    return Storages.USER_STORAGE.getUser(getUserId(id));
  }

  default Set<AuthID> getAuthIds(User user, String authType){
    return getAuthIds(user.getId(), authType);
  }

  default void addAuthId(User user, AuthID id){
    return addAuthId(user.getId(), id);
  }

  default void addAuthId(User user, AuthID id){
    return removeAuthId(user.getId(), id);
  }
}
