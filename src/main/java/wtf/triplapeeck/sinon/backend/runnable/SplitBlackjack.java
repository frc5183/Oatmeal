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

public class SplitBlackjack implements Runnable {
    private long ChannelID;
    private String MemberID;
    Table table;
    public SplitBlackjack(long ChannelID, String MemberID) {
        this.ChannelID=ChannelID;
        this.MemberID=MemberID;
    }

    @Override
    public void run() {
        ChannelStorable channelStorable = getChannel(ChannelID);
        Logger.customLog("Split","Starting. Waiting On Table.");

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
        Logger.customLog("Split", "Table Requested. Now Owner");
        channel= Main.api.getTextChannelById(ChannelID);
        if (table==null) {
            channel.sendMessage("There is not currently a table.").queue();
            channelStorable.relinquishTable();
            channelStorable.relinquishAccess();
            Logger.customLog("Split","Table Relinquished. Finished. ");
            return;
        }
        if (table.state!= TableState.PLAYING) {
            channel.sendMessage("You can't split right now.").queue();

        } else {
            try {
                Logger.customLog("Split","Attempting To Split.");
                SpotGroup player = table.findUser(MemberID);
                    player.Split(table.shoe, channel);


            }
            catch (InvalidCardActionException e) {
                channel.sendMessage(e.getMessage()).queue();
            }
        }
        channelStorable.relinquishTable();
        channelStorable.relinquishAccess();
        Logger.customLog("Split","Table Relinquished. Finished. ");
    }
}
