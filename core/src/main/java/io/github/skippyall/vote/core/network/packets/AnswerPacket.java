package io.github.skippyall.vote.core.network.packets;

import io.github.skippyall.vote.core.network.PacketBuffer;
import io.github.skippyall.vote.core.user.User;

public class AnswerPacket extends Packet{
    AnswerType type;
    User user;
    boolean sucess;
    long id;
    String failReason;

    public AnswerPacket(AnswerType type, User user, long id) {
        this.type = type;
        this.user = user;
        sucess = true;
        this.id = id;
    }

    public AnswerPacket(AnswerType type, User user, String failReason) {
        this.type = type;
        this.user = user;
        sucess = false;
        this.failReason = failReason;
    }

    public int id() {
        return 0;
    }

    public static AnswerPacket encode(PacketBuffer buf) {
        return null;
    }

    public void decode(PacketBuffer.PacketBufferWriter writer) {
        writer.write((byte)type.ordinal());
        writer.writeUser(user);
        writer.writeBoolean(sucess);
        if (sucess) {
            writer.writeLong(id);
        }else {
            writer.writeString(failReason);
        }
    }

    public AnswerType getType() {
        return type;
    }

    public boolean isSuccess() {
        return sucess;
    }

    public long getId() {
        return id;
    }

    public String getFailReason() {
        return failReason;
    }

    public User getUser() {
        return user;
    }

    public static enum AnswerType{
        VOTE_CREATE, USER_NAME
    }
}
