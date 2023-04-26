package wtf.triplapeeck.oatmeal.commands.essential;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.commands.Command;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;
import wtf.triplapeeck.oatmeal.DataCarriage;

public class SetStarboardLimit extends Command {
    @Override
    public void handler(MessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {
        if (ensureAdministrator(carriage) && ensureFirstArgument(carriage)) {
            try {
                int limit = Integer.parseInt(carriage.args[1]);
                if (limit<1) {
                    throw new NumberFormatException();
                }
                carriage.guildStorable.setStarboardLimit(limit);
            } catch (NumberFormatException e) {
                carriage.channel.sendMessage("Your starboard limit must be a positive integer greater than zero").queue();
            }
        }
    }

    @NotNull
    @Override
    public String getDocumentation() {
        return "Used to set the required number of stars for a message to be posted to the starboard."
                +"\nUsage: s!setstarboardlimit [limit]"
                +"\nSets the starboard limit to [limit]. [limit] must be a positive integer greater than zero.";

    }

    @Override
    public @NotNull String getName() {
        return "setstarboardlimit";
    }

    @Override
    public @NotNull boolean hasPermission(DataCarriage carriage, User user) {
        return isAdministrator(carriage);
    }
}
