package io.github.skippyall.vote.fabric;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.skippyall.vote.core.network.NetworkInterface;
import io.github.skippyall.vote.core.user.User;
import io.github.skippyall.vote.core.vote.Vote;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.concurrent.CompletableFuture;

import static net.minecraft.server.command.CommandManager.literal;

public class TestCommand implements CommandRegistrationCallback {
    @Override
    public void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(literal("test")
                .then(literal("createVote")
                        .executes(source -> {
                            CompletableFuture<Vote> futureVote = NetworkInterface.createVote(new User(235));
                            futureVote.handle((vote, ex) -> {
                                if (ex != null) {
                                    source.getSource().sendError(Text.literal(ex.getMessage()));
                                } else {
                                    source.getSource().sendMessage(Text.literal("Vote created. ID:" + vote.getId()));
                                }
                                return null;
                            });
                            return 0;
                        })
                )
        );
    }
}
