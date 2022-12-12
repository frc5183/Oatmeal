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

public class InsureBlackjack implements Runnable {

    private final long channelId;
    private final String memberID;
    Table table;
    public InsureBlackjack(long channelId, String memberID) {
        this.channelId = channelId;
        this.memberID = memberID;
    }


    @Override
    public void run() {
        ChannelStorable channelStorable = getChannel(channelId);

        Logger.customLog("Insure","Starting. Waiting On Table.");
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
        Logger.customLog("Insure", "Table Requested. Now Owner");
        channel= Main.api.getTextChannelById(channelId);
        if (table==null) {
            channel.sendMessage("There is not currently a table.").queue();
            channelStorable.relinquishTable();
            channelStorable.relinquishAccess();
            Logger.customLog("Insure","Table Relinquished. Finished. ");
            return;
        }
        if (!table.canInsure()) {
            channel.sendMessage("You can't bet insurance right now.").queue();
            channelStorable.relinquishTable();
            channelStorable.relinquishAccess();
            Logger.customLog("Insure","Table Relinquished. Finished. ");
            return;
        }
        SpotGroup player;
        try {
            Logger.customLog("Insure","Attempting To Insure.");
            player=table.findUser(memberID);
            player.Insure();
            channel.sendMessage("You are now insured").queue();
            channelStorable.relinquishTable();
            channelStorable.relinquishAccess();
            Logger.customLog("Insure","Table Relinquished. Finished. ");
        } catch (InvalidCardActionException e) {
            channel.sendMessage(e.getMessage()).queue();
            channelStorable.relinquishTable();
            channelStorable.relinquishAccess();
            Logger.customLog("Insure","Table Relinquished. Finished. ");
        }
    }
}
