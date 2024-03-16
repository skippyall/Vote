package io.github.skippyall.vote.core.network.packets;

import io.github.skippyall.vote.core.network.PacketBuffer;
import io.github.skippyall.vote.core.user.User;
import io.github.skippyall.vote.core.vote.Vote;

public class VoteCreatePacket extends Packet{
    User owner;
    VoteCreatePacket(User owner) {
        this.owner = owner;
    }

    @Override
    public int id() {
        return 0;
    }

    @Override
    public void decode(PacketBuffer.PacketBufferWriter writer) {
        writer.writeUser(owner);
    }

    public static VoteCreatePacket encode(PacketBuffer buffer) {
        return new VoteCreatePacket(buffer.nextUser());
    }

    public User getOwner() {
        return owner;
    }
}
