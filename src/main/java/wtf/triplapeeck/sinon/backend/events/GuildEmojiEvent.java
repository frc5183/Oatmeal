package wtf.triplapeeck.sinon.backend.events;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.requests.restaction.pagination.PaginationAction;
import wtf.triplapeeck.sinon.backend.Logger;
import wtf.triplapeeck.sinon.backend.storable.GuildStorable;
import wtf.triplapeeck.sinon.backend.storable.StorableManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class GuildEmojiEvent implements Runnable {
    GuildMessageReactionAddEvent event;
    public GuildEmojiEvent(GuildMessageReactionAddEvent event) {
        this.event=event;


    }

    @Override
    public void run() {
        GuildStorable guildStorable = StorableManager.getGuild(event.getGuild().getIdLong());
        TextChannel starboard = event.getGuild().getTextChannelById(guildStorable.getStarboardChannelId());
        if (!event.getReactionEmote().isEmoji()) {
            return;
        }

        if (starboard==null) {
            return;
        }
        Message message = null;
        if (event.getReactionEmote().getAsCodepoints().equalsIgnoreCase("U+2b50")) {
            message = event.getChannel().retrieveMessageById(event.getMessageIdLong()).complete();
        } else {
            return;
        }
        int count=0;
        try {
            for (MessageReaction messageReaction : event.getChannel().retrieveMessageById(event.getMessageIdLong()).complete().getReactions()) {

                if (messageReaction.getReactionEmote().getAsCodepoints().equalsIgnoreCase("U+2b50")) {
                    for (Iterator<User> it = messageReaction.retrieveUsers().stream().iterator(); count >= guildStorable.getStarboardLimit() || it.hasNext(); ) {
                        it.next();
                        count += 1;

                    }
                }
            }
        } catch (NoSuchElementException e) {

        }
         if (count>=guildStorable.getStarboardLimit()) {
            ArrayList<MessageEmbed.Field> list = new ArrayList<MessageEmbed.Field>();
            list.add(new MessageEmbed.Field("Source", "[Jump!](" + message.getJumpUrl() + ")", false));
            MessageEmbed embed = new MessageEmbed(null,null,message.getContentRaw(), EmbedType.RICH,message.getTimeCreated(),0,null,null,new MessageEmbed.AuthorInfo(message.getAuthor().getName(), null, message.getAuthor().getAvatarUrl(), null),null, new MessageEmbed.Footer(message.getId(),null,null),null, list);

            starboard.sendMessage(message.getTextChannel().getAsMention()).embed(embed).queue();
        }
    }
}
