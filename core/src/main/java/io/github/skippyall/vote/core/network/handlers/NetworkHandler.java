package io.github.skippyall.vote.core.network.handlers;

import io.github.skippyall.vote.core.network.packets.AnswerPacket;
import io.github.skippyall.vote.core.network.packets.VoteCreatePacket;

public class NetworkHandler {
    public void handle(VoteCreatePacket voteCreate) {};
    public void handle(AnswerPacket answer) {};
}
