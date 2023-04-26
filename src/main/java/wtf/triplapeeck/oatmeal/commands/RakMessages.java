package wtf.triplapeeck.oatmeal.commands;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;
import wtf.triplapeeck.oatmeal.DataCarriage;
import wtf.triplapeeck.oatmeal.Page;

public class RakMessages extends Command {
    @Override
    public void handler(MessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {
        if (ensureCurrencyEnabled(carriage) && ensureFirstArgument(carriage)) {
            String preference = carriage.args[1];
            if (preference.equalsIgnoreCase("enable") || preference.equalsIgnoreCase("disable")) {
                    carriage.userStorable.setCurrencyPreference(preference.equalsIgnoreCase("enable"));
                    carriage.channel.sendMessage("Updated your preference to be messages " + preference.toLowerCase() + "d.").queue();
            } else {
                carriage.channel.sendMessage("You have to choose either enable or disable for your preference.").queue();
            }
        }
    }

    @NotNull
    @Override
    public String getDocumentation() {
        return "Used to enable or disable messages when you receive random RAK gifts" +
                "\nUsage s!rakmessage [enable/disable]"
                +"\nUpdates your preference for receiving RAK messages."
                +"\nExample: s!rakmessage disable";
    }

    @Override
    public @NotNull String getName() {
        return "rakmessage";
    }

    @Override
    public @NotNull boolean hasPermission(DataCarriage carriage, User user) {
        return currencyEnabled(carriage);
    }
    public RakMessages() {
        Page.Currency.addCommand(this);
    }
}
