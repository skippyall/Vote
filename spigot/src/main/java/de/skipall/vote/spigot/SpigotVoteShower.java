package de.skipall.vote.spigot;

import de.skipall.vote.core.action.VoteShower;
import de.skipall.vote.core.vote.Vote;
import de.skipall.vote.spigot.misc.SpigotSession;
import org.bukkit.command.CommandSender;

public class SpigotVoteShower implements VoteShower<SpigotSession> {

    @Override
    public void showVote(Vote vote, SpigotSession session) {
        CommandSender.Spigot player = session.getPlayer().spigot();
        player.
    }

    @Override
    public void hideVote() {

    }
}
