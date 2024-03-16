package io.github.skippyall.vote.core.network.packets;

import io.github.skippyall.vote.core.network.PacketBuffer;
import io.github.skippyall.vote.core.network.handlers.NetworkHandler;

public abstract class Packet {
    public abstract int id();

    public abstract void decode(PacketBuffer.PacketBufferWriter writer);

    public abstract void handle(NetworkHandler handler);
}
