package io.github.skippyall.vote.core.network.handlers;

import io.github.skippyall.vote.core.network.NetworkThread;
import io.github.skippyall.vote.core.network.packets.AnswerPacket;
import io.github.skippyall.vote.core.network.packets.VoteCreatePacket;
import io.github.skippyall.vote.core.storage.Storages;
import io.github.skippyall.vote.core.user.User;
import io.github.skippyall.vote.core.vote.Vote;
import io.github.skippyall.vote.core.vote.storage.SQLVoteStorage;
import io.github.skippyall.vote.core.vote.storage.VoteStorage;

public class ServerNetworkHandler extends NetworkHandler{
    final NetworkThread network;
    public ServerNetworkHandler(NetworkThread network) {
        this.network = network;
    }
    @Override
    public void handle(VoteCreatePacket voteCreate) {
        User owner = voteCreate.getOwner();
        if (owner == null)
            network.sendPacket(new AnswerPacket(AnswerPacket.AnswerType.VOTE_CREATE, owner, "Owner doesn't exist"));

        VoteStorage voteStorage = Storages.VOTE_STORAGE;
        if (voteStorage instanceof SQLVoteStorage sqlVoteStorage) {
            Vote vote = sqlVoteStorage.createVote(voteCreate.getOwner());
            if (vote == null)
                network.sendPacket(new AnswerPacket(AnswerPacket.AnswerType.VOTE_CREATE, owner, "Can't create Vote"));

            network.sendPacket(new AnswerPacket(AnswerPacket.AnswerType.VOTE_CREATE, owner, vote.getId()));
        }else {
            network.sendPacket(new AnswerPacket(AnswerPacket.AnswerType.VOTE_CREATE, owner, "Server use wrong Vote storage"));
        }
    }
}
