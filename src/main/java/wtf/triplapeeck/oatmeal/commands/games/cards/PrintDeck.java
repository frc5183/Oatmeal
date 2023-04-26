package wtf.triplapeeck.oatmeal.commands.games.cards;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;
import wtf.triplapeeck.oatmeal.DataCarriage;
import wtf.triplapeeck.oatmeal.Page;
import wtf.triplapeeck.oatmeal.commands.Command;
import wtf.triplapeeck.oatmeal.cards.Deck;

public class PrintDeck extends Command {
    public void handler(MessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {


        carriage.channel.sendMessage(new Deck().deck.toString()).queue();
    }
    public @NotNull String getDocumentation() { return "" +
            "Prints a 52 Card Deck" +
            "\nUsage: s!printdeck";}
    public @NotNull String getName() {
        return "printdeck";
    }

    @Override
    public @NotNull boolean hasPermission(DataCarriage carriage, User user) {
        return currencyEnabled(carriage);
    }

    public PrintDeck () {
        Page.CardGames.addCommand(this);
    }
}
