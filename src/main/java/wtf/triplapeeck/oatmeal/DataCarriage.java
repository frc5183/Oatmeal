package wtf.triplapeeck.oatmeal;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import wtf.triplapeeck.oatmeal.commands.Command;
import wtf.triplapeeck.oatmeal.entities.ChannelData;
import wtf.triplapeeck.oatmeal.entities.GuildData;
import wtf.triplapeeck.oatmeal.entities.MemberData;
import wtf.triplapeeck.oatmeal.entities.UserData;

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

    public UserData userEntity;
    public GuildData guildEntity;
    public ChannelData channelStorable;
    public MemberData memberStorable;
    public Message message;

    public JDA api;
}
