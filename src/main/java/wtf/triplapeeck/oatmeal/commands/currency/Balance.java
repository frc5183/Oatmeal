package wtf.triplapeeck.oatmeal.commands.currency;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.DataCarriage;
import wtf.triplapeeck.oatmeal.Page;
import wtf.triplapeeck.oatmeal.commands.Command;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;

public class Balance extends Command {
    @Override
    public void handler(MessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {

        carriage.channel.sendMessage("You have " + carriage.memberStorable.getRak() + " rak in this server.").queue();
    }
    @Override
    public @NotNull String getDocumentation() {
        return "Returns the user's current balance in rak" +
                "\nUsage: s!rak";
    }
    @Override
    public @NotNull String getName() {
        return "bal";
    }

    @Override
    public @NotNull boolean hasPermission(DataCarriage carriage, User user) {
        return currencyEnabled(carriage);
    }

    public Balance() {
        Page.Currency.addCommand(this);
    }
}
