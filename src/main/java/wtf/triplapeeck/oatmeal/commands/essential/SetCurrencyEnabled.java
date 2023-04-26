package wtf.triplapeeck.oatmeal.commands.essential;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.DataCarriage;
import wtf.triplapeeck.oatmeal.Page;
import wtf.triplapeeck.oatmeal.commands.Command;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;

public class SetCurrencyEnabled extends Command {
    @Override
    public void handler(MessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {
        if (ensureAdministrator(carriage) && ensureFirstArgument(carriage)) {
            String preference = carriage.args[1];
            if (preference.equalsIgnoreCase("enable") || preference.equalsIgnoreCase("disable")) {
                carriage.guildStorable.setCurrencyEnabled(preference.equalsIgnoreCase("enable"));
                carriage.channel.sendMessage("Updated your server currency setting to " + preference.toLowerCase() + "d.").queue();
            } else {
                carriage.channel.sendMessage("You have to choose either enable or disable currency.").queue();
            }
        }
    }

    @NotNull
    @Override
    public String getDocumentation() {
        return "Used to enable or disable currency for your server" +
                "\nUsage s!currency [enable/disable]"
                +"\nUpdates your server setting for currency."
                +"\nExample: s!currency disable";

    }

    @Override
    public @NotNull String getName() {
        return "currency";
    }

    @Override
    public @NotNull boolean hasPermission(DataCarriage carriage, User user) {
        return isAdministrator(carriage);
    }
    public SetCurrencyEnabled() {
        Page.Essential.addCommand(this);
    }
}
