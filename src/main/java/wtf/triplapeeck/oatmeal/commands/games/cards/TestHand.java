package wtf.triplapeeck.oatmeal.commands.games.cards;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;
import wtf.triplapeeck.oatmeal.DataCarriage;
import wtf.triplapeeck.oatmeal.Page;
import wtf.triplapeeck.oatmeal.commands.Command;
import wtf.triplapeeck.oatmeal.cards.Spot;
import wtf.triplapeeck.oatmeal.cards.Table;

public class TestHand extends Command {
    public void handler(MessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {
        Table table = new Table(0);
       Spot player = new Spot();
       int count=carriage.random.nextInt(4)+2;
       for (int i=0; i<count; i++) {
           player.Draw(table.shoe);
       }
        carriage.channel.sendMessage(player.hand.hand.toString() +
                "\n" +
                player.hand.getValue()
        ).queue();

    }
    public @NotNull String getName() {
        return "testhand";
    }

    @Override
    public @NotNull boolean hasPermission(DataCarriage carriage, User user) {
        return (currencyEnabled(carriage) && testingEnabled(carriage));
    }

    public @NotNull String getDocumentation() {
        return "Usage:" +
                "\ns!testhand";
    }


    public TestHand() {
        Page.CardGames.addCommand(this);
    }
}
