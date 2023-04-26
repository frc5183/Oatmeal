package wtf.triplapeeck.oatmeal.runnable;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import wtf.triplapeeck.oatmeal.cards.SpotGroup;
import wtf.triplapeeck.oatmeal.cards.Table;
import wtf.triplapeeck.oatmeal.errors.InvalidCardActionException;
import wtf.triplapeeck.oatmeal.errors.UsedTableException;
import wtf.triplapeeck.oatmeal.storable.ChannelStorable;
import wtf.triplapeeck.oatmeal.storable.StorableManager;
import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.cards.TableState;

public class StandBlackjack implements NamedRunnable {
    String name = "STANDBLACKJACK";
    public String getName() {
        return name;
    }
    private long ChannelID;
    private String MemberID;
    private boolean secondHand=false;
    Table table;
    public StandBlackjack(long ChannelID, String MemberID) {
        this.ChannelID=ChannelID;
        this.MemberID=MemberID;
    }
    public StandBlackjack(long ChannelID, String MemberID, boolean second) {
        this.ChannelID=ChannelID;
        this.MemberID=MemberID;
        secondHand=second;
    }

    @Override
    public void run() {
        ChannelStorable channelStorable = StorableManager.getChannel(ChannelID);
        Logger.customLog("Stand","Starting. Waiting On Table.");

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
        Logger.customLog("Stand", "Table Requested. Now Owner");
        channel= Main.api.getTextChannelById(ChannelID);
        if (table==null) {
            channel.sendMessage("There is not currently a table.").queue();
            channelStorable.relinquishTable();
            channelStorable.relinquishAccess();
            Logger.customLog("Stand","Table Relinquished. Finished. ");
            return;
        }
        if (table.state!= TableState.PLAYING) {
            channel.sendMessage("You can't Stand right now.").queue();
        } else {
            try {
                Logger.customLog("Stand","Attempting To Stand.");
                SpotGroup player = table.findUser(MemberID);
                if (secondHand) {
                    if (player.spot2==null) {throw new InvalidCardActionException("You don't have a second hand");}
                    player.spot2.Stand(table);
                } else {
                    player.spot1.Stand(table);
                }

            }
            catch (InvalidCardActionException e) {
                channel.sendMessage(e.getMessage()).queue();
            }
        }
                channelStorable.relinquishTable();
        channelStorable.relinquishAccess();

        Logger.customLog("Stand","Table Relinquished. Finished. ");
    }
}
