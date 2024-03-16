package io.github.skippyall.vote.core.network;

import io.github.skippyall.vote.core.user.User;
import io.github.skippyall.vote.core.vote.Vote;

import java.util.concurrent.CompletableFuture;

public class NetworkInterface {
    public static CompletableFuture<Vote> createVote(User user) {
        CompletableFuture<Vote> completableFuture = new CompletableFuture<>();
        
        return completableFuture;
    }

    public static CompletableFuture<String> getUserName(User user) {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        return completableFuture;
    }
}
