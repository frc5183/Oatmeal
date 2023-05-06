package wtf.triplapeeck.oatmeal;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import wtf.triplapeeck.oatmeal.commands.Command;
import wtf.triplapeeck.oatmeal.entities.GuildData;
import wtf.triplapeeck.oatmeal.entities.UserData;
import wtf.triplapeeck.oatmeal.entities.mariadb.MariaGuild;
import wtf.triplapeeck.oatmeal.entities.mariadb.MariaUser;
import wtf.triplapeeck.oatmeal.entities.json.ChannelJSONStorable;
import wtf.triplapeeck.oatmeal.entities.json.MemberJSONStorable;

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
    public ChannelJSONStorable channelStorable;
    public MemberJSONStorable memberStorable;
    public Message message;

    public JDA api;
}
