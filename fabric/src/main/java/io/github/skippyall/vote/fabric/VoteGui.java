package io.github.skippyall.vote.fabric;

import io.github.skippyall.vote.core.storage.Storages;
import io.github.skippyall.vote.core.vote.Comment;
import io.github.skippyall.vote.core.vote.Vote;
import net.minecraft.block.MapColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.network.packet.s2c.play.OpenWrittenBookS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.*;
import net.minecraft.util.Hand;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class VoteGui {
    public static void openGui(ServerPlayerEntity player, List<Text> texts) {
        ItemStack book = new ItemStack(Items.WRITTEN_BOOK);
        NbtCompound nbt = book.getOrCreateNbt();
        NbtList pages = nbt.getList(WrittenBookItem.PAGES_KEY, NbtElement.STRING_TYPE);
        for(Text text : texts) {
            pages.add(NbtString.of(Text.Serialization.toJsonString(text)));
        }

        ItemStack hand = player.getMainHandStack();
        player.setStackInHand(Hand.MAIN_HAND, book);
        player.networkHandler.sendPacket(new OpenWrittenBookS2CPacket(Hand.MAIN_HAND));
        player.setStackInHand(Hand.MAIN_HAND, hand);
    }

    private static final Text title = Text.literal("Votes\n").setStyle(Style.EMPTY.withBold(true));
    private static final Text voteStart = Text.literal("").append(title);

    public static void openVoteList(ServerPlayerEntity player) {
        List<Text> texts = new ArrayList<>();
        MutableText text = voteStart.copy();

        Map<Long, String> votes = Storages.VOTE_STORAGE.getVoteTitles();
        int line = 1;
        for(Map.Entry<Long, String> entry : votes.entrySet()) {
            Text voteText = Text.literal(entry.getValue()+"\n")
                    .setStyle(Style.EMPTY.withClickEvent(
                            new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/vote show " + entry.getKey()))
                    );
            text.append(voteText);

            line++;
            if(line>=15) {
                texts.add(text);
                text = voteStart.copy();
                line = 0;
            }
        }

        openGui(player, texts);
    }

    public static void showVote(ServerPlayerEntity player, long id) {
        Vote vote = Storages.VOTE_STORAGE.getVote(id);

        List<Text> texts = new ArrayList<>();

        MutableText text = Text.literal("");
        text.append(Text.literal(vote.getDisplayName()+"\n").setStyle(Style.EMPTY.withBold(true)));

        text.append(Text.literal("Vote\n"));

        text.append(Text.literal("Vote").setStyle(Style.EMPTY.withColor(MapColor.BLUE.color)
                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("Click to vote for")))
                .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/vote vote"))
        ));
        texts.add(text);

        Set<Comment> comments = vote.getDiscussion();
        for(Comment comment:comments) {
            MutableText commentText = Text.literal("");
            commentText.append(Text.literal(comment.getWriter().getName()+"\n").withColor(MapColor.GRAY.color));
            commentText.append(Text.literal(comment.getText()));
            texts.add(commentText);
        }

        openGui(player, texts);
    }
}
