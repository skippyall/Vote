package de.skipall.vote.spigot.misc;

import de.skipall.vote.core.user.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SpigotUtil {
    public static User toUser(CommandSender user) {
        return new User(!(user instanceof Player), user.hasPermission(""), 123, user.getName());
    }
}
