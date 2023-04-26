package wtf.triplapeeck.oatmeal.commands.games.cards.blackjack;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.DataCarriage;
import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.Page;
import wtf.triplapeeck.oatmeal.commands.Command;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;
import wtf.triplapeeck.oatmeal.runnable.SplitBlackjack;

public class Split extends Command {
    public Split() {
        Page.CardGames.addCommand(this);
    }
    @Override
    public void handler(MessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {
        if (ensureCurrencyEnabled(carriage)) {
            Main.threadManager.addTask(new SplitBlackjack(carriage.channelStorable.getID().longValue(), carriage.memberStorable.getID().toString()));
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
