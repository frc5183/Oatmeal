package wtf.triplapeeck.oatmeal.events;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.entities.GuildEntity;
import wtf.triplapeeck.oatmeal.errors.database.MissingEntryException;
import wtf.triplapeeck.oatmeal.runnable.NamedRunnable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class GuildEmojiAddEvent implements NamedRunnable {
    String name = "GUILDEMOJIEVENT";
    public String getName() {
        return name;
    }
    MessageReactionAddEvent event;
    public GuildEmojiAddEvent(MessageReactionAddEvent event) {
        this.event=event;
    }

    @Override
    public void run() {

        GuildEntity guildEntity = null;
        try {
            guildEntity = Main.entityManager.getGuildEntity(event.getGuild().getId());
        } catch (MissingEntryException e) {
            guildEntity = new GuildEntity(event.getGuild().getId());
            Main.entityManager.updateGuildEntity(guildEntity);
        }
        if (guildEntity.isStarboardEnabled()) {
            assert guildEntity.getStarboardChannelID() != null;
            TextChannel starboard = event.getGuild().getTextChannelById(guildEntity.getStarboardChannelID());
            if (event.getReaction().getEmoji().getType() == Emoji.Type.CUSTOM) {
                guildEntity.release();
                return;
            }

            if (starboard == null) {
                guildEntity.release();
                return;
            }
            Message message;
            if (event.getEmoji().asUnicode().getAsCodepoints().equalsIgnoreCase("U+2b50")) {
                message = event.getChannel().retrieveMessageById(event.getMessageIdLong()).complete();
            } else {
                guildEntity.release();
                return;
            }
            int count = 0;
            try {
                for (MessageReaction messageReaction : event.getChannel().retrieveMessageById(event.getMessageIdLong()).complete().getReactions()) {
                    try {
                        if (messageReaction.getEmoji().asUnicode().getAsCodepoints().equalsIgnoreCase("U+2b50")) {
                            for (Iterator<User> it = messageReaction.retrieveUsers().stream().iterator(); count >= guildEntity.getStarboardLimit() || it.hasNext(); ) {
                                it.next();
                                count++;

                            }
                        }
                    } catch (IllegalStateException e) {

                    }
                }
            } catch (NoSuchElementException e) {

            }
            if (count >= guildEntity.getStarboardLimit()) {
                ArrayList<MessageEmbed.Field> list = new ArrayList<>();
                list.add(new MessageEmbed.Field("Source", "[Jump!](" + message.getJumpUrl() + ")", false));
                String url = "";
                String proxyUrl = "";
                int height = 0;
                int width = 0;
                for (Message.Attachment a : message.getAttachments()) {
                    if (url == "") {
                        url = a.getUrl();
                        height = a.getHeight();
                        width = a.getWidth();
                        proxyUrl = a.getProxyUrl();
                    }
                }
                MessageEmbed embed = new MessageEmbed(null, null, message.getContentRaw(), EmbedType.RICH, message.getTimeCreated(), 0, null, null, new MessageEmbed.AuthorInfo(message.getAuthor().getName(), null, message.getAuthor().getAvatarUrl(), null), null, new MessageEmbed.Footer(message.getId(), null, null), new MessageEmbed.ImageInfo(url, proxyUrl, width, height), list);
                var Link = guildEntity.getStarboardLink();
                Message starboardMessage = null;
                try {
                    starboardMessage = starboard.retrieveMessageById(Link.get(message.getId())).complete();
                    throw new Error();
                } catch (RuntimeException e) {
                    starboardMessage = starboard.sendMessage("Stars: " + count + " " + message.getChannel().getAsMention()).addEmbeds(embed).complete();

                } catch (Error e) {
                    starboardMessage.editMessage("Stars: " + count + " " + message.getChannel().getAsMention()).setEmbeds(embed).queue();
                } finally {
                    Link.put(message.getId(), starboardMessage.getId());
                    guildEntity.release();
                }

            }
        }
    }

}
