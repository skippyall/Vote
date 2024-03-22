package io.github.skippyall.vote.core.auth;

import java.util.Objects;

public class AuthID {
    String authType;
    String id;
    public AuthID(String authType, String id){
        this.authType = authType;
        this.id = id;
    }

    public String getAuthType() {
        return authType;
    }

    public String getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(authType, id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthID authID = (AuthID) o;
        return authType.equals(authID.authType) && id.equals(authID.id);
    }
}
