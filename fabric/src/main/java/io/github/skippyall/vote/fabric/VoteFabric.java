package io.github.skippyall.vote.fabric;

import io.github.skippyall.vote.core.VoteCore;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

public class VoteFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        FabricEnvironment.init();
        VoteCore.init();

        ServerLifecycleEvents.SERVER_STOPPED.register(VoteFabric::onServerStopped);
        CommandRegistrationCallback.EVENT.register(new VoteCommand());
        BlockEntityRendererRegistry
    }

    public static void onServerStopped(MinecraftServer server) {
        VoteCore.shutdown();
    }
}
