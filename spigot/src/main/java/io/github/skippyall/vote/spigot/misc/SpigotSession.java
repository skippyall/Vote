package io.github.skippyall.vote.spigot.misc;

import io.github.skippyall.vote.core.user.Session;
import io.github.skippyall.vote.core.user.User;
import org.bukkit.command.CommandSender;

public class SpigotSession extends Session {
    CommandSender player;
    public SpigotSession(User user, CommandSender spigotSession) {
        super(user);
        player = spigotSession;
    }

    @Override
    public void sendMessage(String message) {
        player.sendMessage(message);
    }

    public CommandSender getPlayer(){
        return player;
    }

    public static User findUser(CommandSender sender){
        return null;
    }
}
