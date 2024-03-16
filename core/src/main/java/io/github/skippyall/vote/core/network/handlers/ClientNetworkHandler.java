package io.github.skippyall.vote.core.network.handlers;

import io.github.skippyall.vote.core.network.NetworkThread;
import io.github.skippyall.vote.core.network.packets.AnswerPacket;
import io.github.skippyall.vote.core.storage.Storages;
import io.github.skippyall.vote.core.user.User;
import io.github.skippyall.vote.core.util.EqualsMap;
import io.github.skippyall.vote.core.vote.Vote;

import java.util.concurrent.CompletableFuture;

public class ClientNetworkHandler extends NetworkHandler{
    NetworkThread networkThread;
    public ClientNetworkHandler(NetworkThread networkThread) {
        this.networkThread = networkThread;
    }
    @Override
    public void handle(AnswerPacket answer) {
        switch (answer.getType()) {
            case VOTE_CREATE -> {
                EqualsMap<User, CompletableFuture<?>> voteCreate = networkThread.getClientStorage().VOTE_CREATE;
                CompletableFuture<?> future = voteCreate.get(answer.getUser());
                if (answer.isSuccess()) {
                    try {
                        CompletableFuture<Vote> cv = (CompletableFuture<Vote>) future;
                        Vote vote = new Vote(answer.getUser(), answer.getId());
                        Storages.VOTE_STORAGE.addVote(vote);
                        cv.complete(vote);
                    } catch (ClassCastException ignored) {}
                }else {
                    future.completeExceptionally(new RuntimeException(answer.getFailReason()));
                }
            }
        }
    }
}
