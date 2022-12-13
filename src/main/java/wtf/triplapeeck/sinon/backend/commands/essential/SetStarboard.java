package wtf.triplapeeck.sinon.backend.commands.essential;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.sinon.backend.DataCarriage;
import wtf.triplapeeck.sinon.backend.Page;
import wtf.triplapeeck.sinon.backend.commands.Command;
import wtf.triplapeeck.sinon.backend.listeners.ThreadManager;

public class SetStarboard extends Command {

    @Override
    public void handler(GuildMessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {
        if (ensureAdministrator(carriage) && ensureTaggedChannelListNotEmpty(carriage) && ensureOnlyOneTaggedChannel(carriage)) {
            Long id = carriage.message.getMentionedChannels().get(0).getIdLong();
            carriage.guildStorable.setStarboardChannelID(id);
            carriage.channel.sendMessage("The starboard will post in " + carriage.guild.getTextChannelById(id).getAsMention()).queue();
        }
    }

    @NotNull
    @Override
    public String getDocumentation() {
        return "Used to set up a starboard for your server."+
                "\nUsage: s!setstarboard [channel]"+
                "\nSets the starboard to post in [channel]. [channel] must be a mentioned channel.";
    }

    @Override
    public @NotNull String getName() {
        return "setstarboard";
    }

    @Override
    public @NotNull boolean hasPermission(DataCarriage carriage, User user) {
        return isAdministrator(carriage);
    }

    public SetStarboard() {
        Page.Essential.addCommand(this);}
}
