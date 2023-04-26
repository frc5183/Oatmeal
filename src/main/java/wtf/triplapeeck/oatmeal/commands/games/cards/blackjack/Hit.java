package wtf.triplapeeck.oatmeal.commands.games.cards.blackjack;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;
import wtf.triplapeeck.oatmeal.DataCarriage;
import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.Page;
import wtf.triplapeeck.oatmeal.commands.Command;
import wtf.triplapeeck.oatmeal.runnable.HitBlackjack;

public class Hit extends Command {
    public Hit() {
        Page.CardGames.addCommand(this);
    }
    @Override
    public void handler(MessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {
        if (ensureCurrencyEnabled(carriage)) {
            try {

            Main.threadManager.addTask(new HitBlackjack(carriage.channelStorable.getID().longValue(), carriage.memberStorable.getID().toString(), Integer.valueOf(carriage.args[1])==2));
        } catch (ArrayIndexOutOfBoundsException e) {
            Main.threadManager.addTask(new HitBlackjack(carriage.channelStorable.getID().longValue(), carriage.memberStorable.getID().toString(), false));
        }
    }
    }

    @NotNull
    @Override
    public String getDocumentation() {
        return "Usage:" +
                "\ns!hit [2]" +
                "\nThis will add a card to your hand in blackjack." +
                "\nIf you have 2 hands, this will default to the first deck" +
                "\nUnless you use s!hit 2";
    }

    @Override
    public @NotNull String getName() {
        return "hit";
    }

    @Override
    public @NotNull boolean hasPermission(DataCarriage carriage, User user) {
        return currencyEnabled(carriage);
    }
}
