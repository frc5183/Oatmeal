package wtf.triplapeeck.oatmeal.events;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import wtf.triplapeeck.oatmeal.storable.GuildStorable;
import wtf.triplapeeck.oatmeal.storable.StorableManager;
import wtf.triplapeeck.oatmeal.runnable.NamedRunnable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class GuildEmojiRemoveEvent implements NamedRunnable {
    String name = "GUILDEMOJIEVENT";
    public String getName() {
        return name;
    }
    MessageReactionRemoveEvent event;
    public GuildEmojiRemoveEvent(MessageReactionRemoveEvent event) {
        this.event=event;


    }
    @Override
    public void run() {
        GuildStorable guildStorable = StorableManager.getGuild(event.getGuild().getId());
        TextChannel starboard = event.getGuild().getTextChannelById(guildStorable.getStarboardChannelId());
        if (event.getReaction().getEmoji().getType()== Emoji.Type.CUSTOM) {
            guildStorable.relinquishAccess();
            return;
        }

        if (starboard==null) {
            guildStorable.relinquishAccess();
            return;
        }
        Message message;
        if (event.getEmoji().asUnicode().getAsCodepoints().equalsIgnoreCase("U+2b50")) {
            message = event.getChannel().retrieveMessageById(event.getMessageIdLong()).complete();
        } else {
            guildStorable.relinquishAccess();
            return;
        }
        int count=0;
        try {
            for (MessageReaction messageReaction : event.getChannel().retrieveMessageById(event.getMessageIdLong()).complete().getReactions()) {
                try {
                if (messageReaction.getEmoji().asUnicode().getAsCodepoints().equalsIgnoreCase("U+2b50")) {
                    for (Iterator<User> it = messageReaction.retrieveUsers().stream().iterator(); count >= guildStorable.getStarboardLimit() || it.hasNext(); ) {
                        it.next();
                        count++;

                    }
                }
            } catch (IllegalStateException e) {

            }
            }
        } catch (NoSuchElementException e) {

        }
        ArrayList<MessageEmbed.Field> list = new ArrayList<>();
        list.add(new MessageEmbed.Field("Source", "[Jump!](" + message.getJumpUrl() + ")", false));
        String url="";
        String proxyUrl="";
        int height=0;
        int width=0;
        for (Message.Attachment a : message.getAttachments()) {
            if (url=="") {
                url=a.getUrl();
                height=a.getHeight();
                width=a.getWidth();
                proxyUrl=a.getProxyUrl();
            }
        }
        MessageEmbed embed = new MessageEmbed(null,null,message.getContentRaw(), EmbedType.RICH,message.getTimeCreated(),0,null,null,new MessageEmbed.AuthorInfo(message.getAuthor().getName(), null, message.getAuthor().getAvatarUrl(), null),null, new MessageEmbed.Footer(message.getId(),null,null),new MessageEmbed.ImageInfo(url, proxyUrl, width, height), list);
        var Link = guildStorable.getStarboardLink();
        Message starboardMessage = null;
        try {
            starboardMessage = starboard.retrieveMessageById(Link.get(message.getIdLong())).complete();
            throw new Error();

        } catch (RuntimeException e) {
            if (count >= guildStorable.getStarboardLimit()) {
                starboardMessage = starboard.sendMessage("Stars: " + count +" " + message.getChannel().getAsMention()).addEmbeds(embed).complete();
            }
        } catch (Error e) {
            if (count < guildStorable.getStarboardLimit()) {
                starboardMessage.delete().queue();
            } else {
                starboardMessage.editMessage("Stars: " + count + " " + message.getChannel().getAsMention()).setEmbeds(embed).queue();
             }
        } finally {
            try {
                Link.put(message.getIdLong(), starboardMessage.getIdLong());
            } catch (NullPointerException e) {
                Link.remove(message.getIdLong());
            }
            guildStorable.relinquishAccess();
        }

    }

}