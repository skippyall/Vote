package forRemoval;

import de.skipall.vote.core.user.Session;
import de.skipall.vote.core.user.User;
import net.kyori.adventure.text.Component;

public abstract class MinecraftSession<PlayerType> extends Session {
    PlayerType player;
    public MinecraftSession(User user, PlayerType player) {
        super(user);
        this.player = player;
    }

    public PlayerType getPlayer(){
        return player;
    }



    public abstract void sendMessage(Component message);

    public void sendMessage(String message) {
        sendMessage(Component.text(message));
    }
}
