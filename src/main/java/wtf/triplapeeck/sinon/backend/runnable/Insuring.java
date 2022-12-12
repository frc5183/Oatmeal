package wtf.triplapeeck.sinon.backend.runnable;

import net.dv8tion.jda.api.entities.TextChannel;
import wtf.triplapeeck.sinon.backend.Logger;
import wtf.triplapeeck.sinon.backend.Main;
import wtf.triplapeeck.sinon.backend.errors.UsedTableException;
import wtf.triplapeeck.sinon.backend.games.cards.PlayingCard;
import wtf.triplapeeck.sinon.backend.games.cards.Table;
import wtf.triplapeeck.sinon.backend.games.cards.TableState;
import wtf.triplapeeck.sinon.backend.storable.ChannelStorable;

import static wtf.triplapeeck.sinon.backend.games.cards.PlayingCard.Face.TEN;
import static wtf.triplapeeck.sinon.backend.games.cards.PlayingCard.Suit.SPADES;
import static wtf.triplapeeck.sinon.backend.storable.StorableManager.getChannel;

public class Insuring implements Runnable {
    private long ChannelID;
    Table table;
    public Insuring(long ChannelID) {
        this.ChannelID=ChannelID;
    }
    @Override
    public void run() {
        ChannelStorable channelStorable = getChannel(ChannelID);
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
