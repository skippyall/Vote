package io.github.skippyall.vote.core.network;

import io.github.skippyall.vote.core.util.EqualsMap;
import io.github.skippyall.vote.core.user.User;

import java.util.concurrent.CompletableFuture;

public class ClientStorage {
    public EqualsMap<User, CompletableFuture<?>> VOTE_CREATE = new EqualsMap<>();
}
