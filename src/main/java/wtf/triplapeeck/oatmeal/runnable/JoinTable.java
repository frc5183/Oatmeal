package wtf.triplapeeck.oatmeal.runnable;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import wtf.triplapeeck.oatmeal.entities.ChannelData;
import wtf.triplapeeck.oatmeal.errors.InvalidCardActionException;
import wtf.triplapeeck.oatmeal.errors.UsedTableException;
import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.cards.Spot;
import wtf.triplapeeck.oatmeal.cards.SpotGroup;
import wtf.triplapeeck.oatmeal.cards.Table;
import wtf.triplapeeck.oatmeal.cards.TableState;

import java.math.BigInteger;

public class JoinTable implements NamedRunnable {
    String name = "JOINTABLE";
    public String getName() {
        return name;
    }

    private long channelId;
    private String memberID;
    private Table table;
    private long userID;
    private String bet;
    public JoinTable(long channelId, String memberID, long userID, String bet) {
        this.channelId = channelId;
        this.memberID = memberID;
        this.userID=userID;
        this.bet=bet;
    }

    @Override
    public void run() {
        ChannelData channelStorable = Main.dataManager.getChannelData(String.valueOf(channelId));
        Logger.customLog("JoinTable","Starting. Waiting On Table.");

        TextChannel channel;
        table=channelStorable.loadTable();
        Logger.customLog("JoinTable", "Table Requested. Now Owner");
        channel= Main.api.getTextChannelById(channelId);
        if (table==null) {
            channel.sendMessage("There is not currently a table.").queue();
            channelStorable.releaseTable();
            channelStorable.release();
            Logger.customLog("JoinTable","Table Relinquished. Finished. ");
            return;
        }
        if (table.state!= TableState.RECRUITING) {
            channel.sendMessage("The table is not currently recruiting users.").queue();
            channelStorable.releaseTable();
            channelStorable.release();
            Logger.customLog("JoinTable","Table Relinquished. Finished. ");
            return;
        }
        SpotGroup player = null;
        try {

            table.findUser(memberID);
            channel.sendMessage("You are already in this table.").queue();
            channelStorable.releaseTable();
            channelStorable.release();
            Logger.customLog("JoinTable","Table Relinquished. Finished. ");
            return;
        } catch (InvalidCardActionException e) {

        }
        try {
            player=table.GetOpenSpotGroup();
            player.id=memberID;
            player.userId=userID;
            player.setBet(new BigInteger(bet));
            player.spot1=new Spot();
            channel.sendMessage("You Have Joined").queue();
        } catch (InvalidCardActionException e) {
            channel.sendMessage(e.getMessage()).queue();
            table.RemoveSpotGroup(player);
        } catch (NumberFormatException e) {
            channel.sendMessage("That is not a valid bet. It must be an integer.").queue();
            table.RemoveSpotGroup(player);
        }
                channelStorable.releaseTable();
        channelStorable.release();

        Logger.customLog("JoinTable","Table Relinquished. Finished. ");
    }
}
