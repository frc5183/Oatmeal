package wtf.triplapeeck.oatmeal.commands.essential;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.commands.Command;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;
import wtf.triplapeeck.oatmeal.runnable.NukeRunnable;
import wtf.triplapeeck.oatmeal.DataCarriage;
import wtf.triplapeeck.oatmeal.Page;

public class Nuke extends Command {
    @Override
    public void handler(MessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {
        if (ensureAdministrator(carriage) && ensureFirstArgument(carriage) && ensureGuild(carriage)) {
            try {
                int number = Integer.valueOf(carriage.args[1]);
                if (number<1) {
                    throw new NumberFormatException();
                }
                number++;
                listener.addTask(new NukeRunnable(carriage.guildStorable.getIDLong(), carriage.channel.getIdLong(), number, carriage.channel.sendMessage("Queueing a nuke task for " + number + " messages. (Will delete your message and then " + (number-1) + " more)")));
            } catch (NumberFormatException e) {
                carriage.channel.sendMessage("You have to choose an integer above 0.").queue();
            }
        }
    }

    @NotNull
    @Override
    public String getDocumentation() {
        return "Usage:"
                +"\ns!nuke [number (integer more than zero)]"
                +"\nThis will nuke the amount of messages specified in this channel.";
    }

    @Override
    public @NotNull String getName() {
        return "nuke";
    }

    @Override
    public @NotNull boolean hasPermission(DataCarriage carriage, User user) {
        return isAdministrator(carriage) && isGuild(carriage);
    }
    public Nuke() {
        Page.Essential.addCommand(this);
    }
}
