package wtf.triplapeeck.sinon.backend.commands.games.cards.blackjack;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.sinon.backend.DataCarriage;
import wtf.triplapeeck.sinon.backend.Main;
import wtf.triplapeeck.sinon.backend.Page;
import wtf.triplapeeck.sinon.backend.commands.Command;
import wtf.triplapeeck.sinon.backend.listeners.ThreadManager;
import wtf.triplapeeck.sinon.backend.runnable.SplitBlackjack;

public class Split extends Command {
    public Split() {
        Page.CardGames.addCommand(this);
    }
    @Override
    public void handler(GuildMessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {
        if (ensureCurrencyEnabled(carriage)) {
            Main.threadManager.addTask(new Thread(new SplitBlackjack(carriage.channelStorable.getID().longValue(), carriage.memberStorable.getID().toString())));
        }
    }

    @NotNull
    @Override
    public String getDocumentation() {
        return "Usage:" +
                "\ns!split" +
                "\nThis will split into two hands in blackjack." +
                "\nOnly works when you have 2 cards of the same value";
    }

    @Override
    public @NotNull String getName() {return "split";}

    @Override
    public @NotNull boolean hasPermission(DataCarriage carriage, User user) {
        return currencyEnabled(carriage);
    }
}
