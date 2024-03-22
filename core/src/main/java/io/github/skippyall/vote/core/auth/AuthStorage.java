package io.github.skippyall.vote.core.auth;

public interface AuthStorage extends Storage {
  User getUser(AuthID id);
  Set<AuthID> getAuthIds(User user, String authType);
  void addAuthId(User user, AuthID id);
  void removeAuthId(User user, AuthID id);
}
