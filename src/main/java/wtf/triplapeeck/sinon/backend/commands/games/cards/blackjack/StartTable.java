package wtf.triplapeeck.sinon.backend.commands.games.cards.blackjack;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.sinon.backend.DataCarriage;
import wtf.triplapeeck.sinon.backend.Main;
import wtf.triplapeeck.sinon.backend.Page;
import wtf.triplapeeck.sinon.backend.commands.Command;
import wtf.triplapeeck.sinon.backend.listeners.ThreadManager;
import wtf.triplapeeck.sinon.backend.runnable.TimeoutBlackjackTable;

public class StartTable extends Command {
    public StartTable() {
        Page.CardGames.addCommand(this);
    }
    @Override
    public void handler(GuildMessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {
        if (currencyEnabled(carriage)) {
            Main.threadManager.addTask(new Thread(new TimeoutBlackjackTable(carriage.channelStorable.getID().longValue())));
        }
    }

    @NotNull
    @Override
    public String getDocumentation() {
        return "Usage:" +
                "\ns!starttable" +
                "\nThis will start a 30 second timeout for recruiting players.";
    }

    @Override
    public @NotNull String getName() {
        return "starttable";
    }

    @Override
    public @NotNull boolean hasPermission(DataCarriage carriage, User user) {
        return currencyEnabled(carriage);
    }
}
