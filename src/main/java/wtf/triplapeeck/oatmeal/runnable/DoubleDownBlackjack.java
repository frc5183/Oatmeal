package wtf.triplapeeck.oatmeal.runnable;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import wtf.triplapeeck.oatmeal.errors.UsedTableException;
import wtf.triplapeeck.oatmeal.storable.ChannelStorable;
import wtf.triplapeeck.oatmeal.storable.StorableManager;
import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.errors.InvalidCardActionException;
import wtf.triplapeeck.oatmeal.cards.SpotGroup;
import wtf.triplapeeck.oatmeal.cards.Table;
import wtf.triplapeeck.oatmeal.cards.TableState;

public class DoubleDownBlackjack implements NamedRunnable {
    String name = "DOUBLEDOWNBLACKJACK";
    public String getName() {
        return name;
    }
    private long ChannelID;
    private String MemberID;
    private boolean secondHand=false;
    Table table;
    public DoubleDownBlackjack(long ChannelID, String MemberID) {
        this.ChannelID=ChannelID;
        this.MemberID=MemberID;
    }
    public DoubleDownBlackjack(long ChannelID, String MemberID, boolean second) {
        this.ChannelID=ChannelID;
        this.MemberID=MemberID;
        secondHand=second;
    }

    @Override
    public void run() {
        Logger.customLog("DoubleDown", "Starting. Waiting On Table.");
        ChannelStorable channelStorable = StorableManager.getChannel(ChannelID);


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
        Logger.customLog("DoubleDown", "Table Requested. Now Owner");
        channel= Main.api.getTextChannelById(ChannelID);
        if (table==null) {
            channel.sendMessage("There is not currently a table.").queue();
            channelStorable.relinquishTable();
            channelStorable.relinquishAccess();
            Logger.customLog("DoubleDown", "Table Relinquished. Finished. ");
            return;
        }
        if (table.state!= TableState.PLAYING) {
            channel.sendMessage("You can't Double Down right now.").queue();
        } else {
            try {
                Logger.customLog("DoubleDown", "Attempting To Double Down.");
                SpotGroup player = table.findUser(MemberID);
                if (secondHand) {
                    if (player.spot2==null) {throw new InvalidCardActionException("You don't have a second hand");}
                    player.DoubleDownSecondSpot(table.shoe, table);
                } else {
                    player.DoubleDownFirstSpot(table.shoe, table);
                }

            }
            catch (InvalidCardActionException e) {
                channel.sendMessage(e.getMessage()).queue();
            }
        }
        channelStorable.relinquishTable();

        Logger.customLog("DoubleDown", "Table Relinquished. Finished.");
    }
}
