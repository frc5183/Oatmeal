package wtf.triplapeeck.oatmeal.commands.essential;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.DataCarriage;
import wtf.triplapeeck.oatmeal.commands.Command;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;

public class SetAutoThread extends Command {
    @Override
    public void handler(MessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {
        if (ensureAdministrator(carriage) && ensureFirstArgument(carriage) && ensureThreadable(carriage)) {
            String preference = carriage.args[1];
            if (preference.equalsIgnoreCase("enable") || preference.equalsIgnoreCase("disable")) {
                carriage.channelStorable.setAutoThread(preference.equalsIgnoreCase("enable"));
                carriage.channel.sendMessage("Messages in this channel now have auto-threading " + preference.toLowerCase() + "d.").queue();
            } else {
                carriage.channel.sendMessage("You have to choose either enable or disable auto-threading.").queue();
            }
        }
    }
    @NotNull
    @Override
    public CommandCategory getCategory() { return CommandCategory.ESSENTIAL;}

    @NotNull
    @Override
    public String getDocumentation() {
        return "Used to enable or disable auto-threading for your server" +
                "\nAuto threading causes a thread to be made for any message sent within this channel" +
                "\nUsage s!autothread [enable/disable]" +
                "\nUpdates the channel setting for currency" +
                "\nExample: s!autothread enable";
    }

    @Override
    public @NotNull String getName() { return "autothread"; }

    @Override
    public @NotNull boolean hasPermission(DataCarriage carriage, User user) {
        return isAdministrator(carriage) && isThreadable(carriage);
    }
}
