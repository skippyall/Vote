package de.skipall.vote.spigot.misc;

import de.skipall.vote.core.user.Session;
import de.skipall.vote.core.user.User;
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
}
