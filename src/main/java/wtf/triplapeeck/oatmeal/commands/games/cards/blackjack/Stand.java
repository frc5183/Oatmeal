package wtf.triplapeeck.oatmeal.commands.games.cards.blackjack;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;
import wtf.triplapeeck.oatmeal.DataCarriage;
import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.Page;
import wtf.triplapeeck.oatmeal.commands.Command;
import wtf.triplapeeck.oatmeal.runnable.StandBlackjack;

public class Stand extends Command {
    public Stand() {
        Page.CardGames.addCommand(this);
    }
    @Override
    public void handler(MessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {
        if (ensureCurrencyEnabled(carriage)) {
            try {
            Main.threadManager.addTask(new StandBlackjack(Long.valueOf(carriage.channelStorable.getID()), carriage.memberStorable.getID().toString(), Integer.valueOf(carriage.args[1])==2));
        } catch (ArrayIndexOutOfBoundsException e) {
            Main.threadManager.addTask(new StandBlackjack(Long.valueOf(carriage.channelStorable.getID()), carriage.memberStorable.getID().toString(), false));
        }
    }
    }

    @NotNull
    @Override
    public String getDocumentation() {
        return "Usage:" +
                "\ns!stand [2]" +
                "\nThis will stand in blackjack." +
                "\nIf you have 2 hands, this will default to the first deck" +
                "\nUnless you use s!stand 2";
    }

    @Override
    public @NotNull String getName() {
        return "stand";
    }

    @Override
    public @NotNull boolean hasPermission(DataCarriage carriage, User user) {
        return currencyEnabled(carriage);
    }
}
