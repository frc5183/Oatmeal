package wtf.triplapeeck.sinon.backend.runnable;

import net.dv8tion.jda.api.entities.TextChannel;
import wtf.triplapeeck.sinon.backend.Logger;
import wtf.triplapeeck.sinon.backend.Main;
import wtf.triplapeeck.sinon.backend.errors.InvalidCardActionException;
import wtf.triplapeeck.sinon.backend.errors.UsedTableException;
import wtf.triplapeeck.sinon.backend.games.cards.SpotGroup;
import wtf.triplapeeck.sinon.backend.games.cards.Table;
import wtf.triplapeeck.sinon.backend.games.cards.TableState;
import wtf.triplapeeck.sinon.backend.storable.ChannelStorable;

import static wtf.triplapeeck.sinon.backend.storable.StorableManager.getChannel;

public class HitBlackjack implements Runnable {
    private long ChannelID;
    private String MemberID;
    private boolean secondHand=false;
    Table table;
    public HitBlackjack(long ChannelID, String MemberID) {
        this.ChannelID=ChannelID;
        this.MemberID=MemberID;
    }
    public HitBlackjack(long ChannelID, String MemberID, boolean second) {
        this.ChannelID=ChannelID;
        this.MemberID=MemberID;
        secondHand=second;
    }

    @Override
    public void run() {
        ChannelStorable channelStorable = getChannel(ChannelID);
        Logger.customLog("Hit","Starting. Waiting On Table.");

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
        Logger.customLog("Hit", "Table Requested. Now Owner");
        channel= Main.api.getTextChannelById(ChannelID);
        if (table==null) {
            channel.sendMessage("There is not currently a table.").queue();
            channelStorable.relinquishTable();
            channelStorable.relinquishAccess();
            Logger.customLog("Hit","Table Relinquished. Finished. ");
            return;
        }
        if (table.state!= TableState.PLAYING) {
            channel.sendMessage("You can't hit right now.").queue();
        } else {
            try {
                Logger.customLog("Hit","Attempting To Hit.");
                SpotGroup player = table.findUser(MemberID);
                if (secondHand) {
                    if (player.spot2==null) {throw new InvalidCardActionException("You don't have a second hand");}

                    player.spot2.Hit(table.shoe, table);
                    channel.sendMessage(Main.api.retrieveUserById(player.userId).complete().getAsMention() + "'s " + player.spot2.hand.toString()).queue();
                } else {

                    player.spot1.Hit(table.shoe, table);
                    channel.sendMessage(Main.api.retrieveUserById(player.userId).complete().getAsMention() + "'s " + player.spot1.hand.toString()).queue();
                }

            }
            catch (InvalidCardActionException e) {
                channel.sendMessage(e.getMessage()).queue();
            }
        }
        channelStorable.relinquishTable();
        channelStorable.relinquishAccess();
        Logger.customLog("Hit","Table Relinquished. Finished. ");
    }
}
