package wtf.triplapeeck.sinon.backend.commands.currency;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.sinon.backend.DataCarriage;
import wtf.triplapeeck.sinon.backend.Page;
import wtf.triplapeeck.sinon.backend.commands.Command;
import wtf.triplapeeck.sinon.backend.listeners.ThreadManager;

public class Balance extends Command {
    @Override
    public void handler(GuildMessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {

        carriage.channel.sendMessage("You have " + carriage.memberStorable.getRak() + " rak in this server.").queue();
    }
    @Override
    public java.lang.@NotNull String getDocumentation() {
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
