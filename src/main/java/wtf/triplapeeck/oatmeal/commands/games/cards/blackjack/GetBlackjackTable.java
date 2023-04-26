package wtf.triplapeeck.oatmeal.commands.games.cards.blackjack;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;
import wtf.triplapeeck.oatmeal.DataCarriage;
import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.Page;
import wtf.triplapeeck.oatmeal.commands.Command;
import wtf.triplapeeck.oatmeal.runnable.GetTable;

public class GetBlackjackTable extends Command {
    public GetBlackjackTable () {
        Page.CardGames.addCommand(this);
    }
    @Override
    public void handler(MessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {
        if (ensureCurrencyEnabled(carriage)) {
            Main.threadManager.addTask(new GetTable(carriage.channelStorable.getID().longValue()));
        }
    }

    @NotNull
    @Override
    public String getDocumentation() {
        return "Usage:" +
                "\ns!gettable" +
                "\nThis will show you the entire current blackjack table.";
    }

    @Override
    public @NotNull String getName() {
        return "gettable";
    }

    @Override
    public @NotNull boolean hasPermission(DataCarriage carriage, User user) {
        return currencyEnabled(carriage);
    }
}
