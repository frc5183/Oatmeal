package wtf.triplapeeck.oatmeal;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import wtf.triplapeeck.oatmeal.commands.Command;
import wtf.triplapeeck.oatmeal.storable.ChannelStorable;
import wtf.triplapeeck.oatmeal.storable.GuildStorable;
import wtf.triplapeeck.oatmeal.storable.MemberStorable;
import wtf.triplapeeck.oatmeal.storable.UserStorable;
import wtf.triplapeeck.oatmeal.storable.*;

import java.util.ArrayList;
import java.util.Random;

public class DataCarriage {
    public ArrayList<Command> commandsList;
    public String[] args;
    public String textAfterSubcommand;
    public Random random;
    public User user;
    public Guild guild;
    public MessageChannel channel;

    public UserStorable userStorable;
    public GuildStorable guildStorable;
    public ChannelStorable channelStorable;
    public MemberStorable memberStorable;
    public Message message;

    public JDA api;
}
