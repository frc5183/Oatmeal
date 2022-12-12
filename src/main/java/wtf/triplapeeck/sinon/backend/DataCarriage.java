package wtf.triplapeeck.sinon.backend;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import wtf.triplapeeck.sinon.backend.commands.Command;
import wtf.triplapeeck.sinon.backend.storable.*;

import java.util.ArrayList;
import java.util.Random;

public class DataCarriage {
    public ArrayList<Command> commandsList;
    public String[] args;
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
