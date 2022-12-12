package wtf.triplapeeck.sinon.backend.commands.games.cards.blackjack;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.sinon.backend.DataCarriage;
import wtf.triplapeeck.sinon.backend.Main;
import wtf.triplapeeck.sinon.backend.Page;
import wtf.triplapeeck.sinon.backend.commands.Command;
import wtf.triplapeeck.sinon.backend.listeners.ThreadManager;
import wtf.triplapeeck.sinon.backend.runnable.DoubleDownBlackjack;
import wtf.triplapeeck.sinon.backend.runnable.HitBlackjack;
import wtf.triplapeeck.sinon.backend.runnable.InsureBlackjack;

public class DoubleDown extends Command {
    public DoubleDown() {
        Page.CardGames.addCommand(this);
    }
    @Override
    public void handler(GuildMessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {
        if (ensureCurrencyEnabled(carriage)) {
            try {

                Main.threadManager.addTask(new Thread(new DoubleDownBlackjack(carriage.channelStorable.getID().longValue(), carriage.memberStorable.getID().toString(), Integer.valueOf(carriage.args[1])==2)));
            } catch (ArrayIndexOutOfBoundsException e) {
                Main.threadManager.addTask(new Thread(new DoubleDownBlackjack(carriage.channelStorable.getID().longValue(), carriage.memberStorable.getID().toString(), false)));
         }
        }
    }

    @NotNull
    @Override
    public String getDocumentation() {
        return "Usage:" +
                "\ns!doubledown [2]" +
                "\nThis will double your bet, add a card to your hand, and then stand in blackjack." +
                "\nIf you have 2 hands, this will default to the first deck" +
                "\nUnless you use s!doubledown 2";
    }

    @Override
    public @NotNull String getName() {
        return "doubledown";
    }

    @Override
    public @NotNull boolean hasPermission(DataCarriage carriage, User user) {
        return currencyEnabled(carriage);
    }
}
