package wtf.triplapeeck.sinon.backend.commands.games.cards.blackjack;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.sinon.backend.DataCarriage;
import wtf.triplapeeck.sinon.backend.Main;
import wtf.triplapeeck.sinon.backend.Page;
import wtf.triplapeeck.sinon.backend.commands.Command;
import wtf.triplapeeck.sinon.backend.listeners.ThreadManager;
import wtf.triplapeeck.sinon.backend.runnable.JoinTable;

public class JoinBlackjackTable extends Command {
    public JoinBlackjackTable() {
        Page.CardGames.addCommand(this);
    }
    @Override
    public void handler(GuildMessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {
        if (ensureCurrencyEnabled(carriage)) {
            try {
                String BET = carriage.args[1];
                Main.threadManager.addTask(new Thread(new JoinTable(carriage.channelStorable.getID().longValue(), String.valueOf(carriage.user.getIdLong()) + carriage.guild.getIdLong(), carriage.user.getIdLong(), BET)));
            } catch (ArrayIndexOutOfBoundsException e) {
                carriage.channel.sendMessage("You have to bet").queue();
            }
        }
    }

    @NotNull
    @Override
    public String getDocumentation() {
        return null;
    }

    @Override
    public @NotNull String getName() {
        return "join";
    }

    @Override
    public @NotNull boolean hasPermission(DataCarriage carriage, User user) {
        return currencyEnabled(carriage);
    }
}
