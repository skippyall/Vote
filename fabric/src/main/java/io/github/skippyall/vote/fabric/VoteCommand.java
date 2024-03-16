package io.github.skippyall.vote.fabric;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class VoteCommand implements CommandRegistrationCallback {
    @Override
    public void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(literal("vote")
                .executes(VoteCommand::openList)
                .then(literal("list")
                        .executes(VoteCommand::openList)
                )
                .then(literal("show")
                        .then(argument("id", LongArgumentType.longArg())
                                .executes(VoteCommand::showVote)
                        )
                )
        );
    }

    public static int openList(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrThrow();
        VoteGui.openVoteList(player);
        return 0;
    }

    public static int showVote(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrThrow();
        long id = LongArgumentType.getLong(context, "id");
        VoteGui.showVote(player, id);
        return 0;
    }
}
