package wtf.triplapeeck.oatmeal.commands.essential;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.DataCarriage;
import wtf.triplapeeck.oatmeal.commands.Command;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;

public class SetStarboard extends Command {

    @Override
    public void handler(MessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {
        if (ensureAdministrator(carriage) && ensureTaggedChannelListNotEmpty(carriage) && ensureOnlyOneTaggedChannel(carriage) && ensureGuild(carriage)) {
            String id = carriage.message.getMentions().getChannels().get(0).getId();
            carriage.guildEntity.setStarboardChannelID(id);
            carriage.channel.sendMessage("The starboard will post in " + carriage.guild.getTextChannelById(id).getAsMention()).queue();
        }
    }

    @NotNull
    @Override
    public CommandCategory getCategory() {
        return CommandCategory.ESSENTIAL;
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
        return isAdministrator(carriage) && isGuild(carriage);
    }
}
