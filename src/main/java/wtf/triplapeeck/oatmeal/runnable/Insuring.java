package wtf.triplapeeck.oatmeal.runnable;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import wtf.triplapeeck.oatmeal.cards.Table;
import wtf.triplapeeck.oatmeal.errors.UsedTableException;
import wtf.triplapeeck.oatmeal.storable.ChannelStorable;
import wtf.triplapeeck.oatmeal.storable.StorableManager;
import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.cards.PlayingCard;
import wtf.triplapeeck.oatmeal.cards.TableState;

import static wtf.triplapeeck.oatmeal.cards.PlayingCard.Face.TEN;
import static wtf.triplapeeck.oatmeal.cards.PlayingCard.Suit.SPADES;

public class Insuring implements NamedRunnable {
    String name = "INSURING";
    public String getName() {
        return name;
    }
    private long ChannelID;
    Table table;
    public Insuring(long ChannelID) {
        this.ChannelID=ChannelID;
    }
    @Override
    public void run() {
        ChannelStorable channelStorable = StorableManager.getChannel(ChannelID);
        Logger.customLog("FinishInsuring","Starting. Waiting On Table.");

        TextChannel channel;
        while (true) {
            boolean complete=false;


            try {
                //noinspection InfiniteLoopStatement
                while (true) {
                    table = channelStorable.getTable();
                    complete = true;
                }
            } catch (UsedTableException ignored) {

            }
            if (complete) {
                break;
            }
        }
        Logger.customLog("FinishInsuring","Table Requested. Now Owner");
        channel= Main.api.getTextChannelById(ChannelID);
        if (table.dealer.hand.hand.get(1).sameValue(new PlayingCard(SPADES,TEN))) {
            Logger.customLog("FinishInsuring","Dealing Insurance Out To Players");

            channel.sendMessage("Dealer has a blackjack. Insurance Bets have been won.").queue();
            if (table.player1.isActive() && table.player1.insured) {
                table.player1.winInsurance();
            }
            if (table.player2.isActive()&& table.player2.insured) {
                table.player2.winInsurance();
            }
            if (table.player3.isActive()&& table.player3.insured) {
                table.player3.winInsurance();
            }
            if (table.player4.isActive()&& table.player4.insured) {
                table.player4.winInsurance();
            }
            table.Finish();
        } else {
            channel.sendMessage("Dealer does not have a blackjack. Insurance Bets have been lost.\nThe Dealer's " + table.dealer.hand.toString()).queue();
            table.state= TableState.PLAYING;
        }
                channelStorable.relinquishTable();
        channelStorable.relinquishAccess();

        channelStorable.setTableInsuring(false);
        Logger.customLog("FinishInsuring", "Table Relinquished. Finished.");
    }
}
