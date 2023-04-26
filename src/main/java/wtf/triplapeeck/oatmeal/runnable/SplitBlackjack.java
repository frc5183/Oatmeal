package wtf.triplapeeck.oatmeal.runnable;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import wtf.triplapeeck.oatmeal.errors.InvalidCardActionException;
import wtf.triplapeeck.oatmeal.errors.UsedTableException;
import wtf.triplapeeck.oatmeal.storable.ChannelStorable;
import wtf.triplapeeck.oatmeal.storable.StorableManager;
import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.cards.SpotGroup;
import wtf.triplapeeck.oatmeal.cards.Table;
import wtf.triplapeeck.oatmeal.cards.TableState;

public class SplitBlackjack implements NamedRunnable {
    String name = "SPLITBLACKJACK";
    public String getName() {
        return name;
    }
    private long ChannelID;
    private String MemberID;
    Table table;
    public SplitBlackjack(long ChannelID, String MemberID) {
        this.ChannelID=ChannelID;
        this.MemberID=MemberID;
    }

    @Override
    public void run() {
        ChannelStorable channelStorable = StorableManager.getChannel(ChannelID);
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
