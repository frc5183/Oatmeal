package wtf.triplapeeck.oatmeal.runnable;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import wtf.triplapeeck.oatmeal.cards.SpotGroup;
import wtf.triplapeeck.oatmeal.errors.InvalidCardActionException;
import wtf.triplapeeck.oatmeal.errors.UsedTableException;
import wtf.triplapeeck.oatmeal.storable.ChannelStorable;
import wtf.triplapeeck.oatmeal.storable.StorableManager;
import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.cards.Table;
import wtf.triplapeeck.oatmeal.cards.TableState;

public class HitBlackjack implements NamedRunnable {
    String name = "HITBLACKJACK";
    public String getName() {
        return name;
    }
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
        ChannelStorable channelStorable = StorableManager.getChannel(ChannelID);
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
                    table.Update();
                } else {

                    player.spot1.Hit(table.shoe, table);

                    channel.sendMessage(Main.api.retrieveUserById(player.userId).complete().getAsMention() + "'s " + player.spot1.hand.toString()).queue();
                    table.Update();
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
