package wtf.triplapeeck.oatmeal.commands.games.cards.blackjack;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;
import wtf.triplapeeck.oatmeal.DataCarriage;
import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.Page;
import wtf.triplapeeck.oatmeal.commands.Command;
import wtf.triplapeeck.oatmeal.runnable.InsureBlackjack;

public class Insure extends Command {
    public Insure() {
        Page.CardGames.addCommand(this);
    }
    @Override
    public void handler(MessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {
        if (ensureCurrencyEnabled(carriage)) {
            Main.threadManager.addTask(new InsureBlackjack(carriage.channelStorable.getID().longValue(), carriage.memberStorable.getID().toString()));
        }
    }

    @NotNull
    @Override
    public String getDocumentation() {
        return "Usage:" +
                "\ns!insure" +
                "\nThis can only be used if you:" +
                "\n1. Are currently in a blackjack table currently offering insurance" +
                "\n2. Aren't already insured.";
    }

    @Override
    public @NotNull String getName() {
        return "insure";
    }

    @Override
    public @NotNull boolean hasPermission(DataCarriage carriage, User user) {
        return currencyEnabled(carriage);
    }
}
