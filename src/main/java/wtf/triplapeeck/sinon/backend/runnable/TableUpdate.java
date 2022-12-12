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

import java.sql.Time;

import static wtf.triplapeeck.sinon.backend.storable.StorableManager.getChannel;

public class TableUpdate implements Runnable {
    private long ChannelID;
    Table table;

    public TableUpdate(long ChannelID) {
        this.ChannelID = ChannelID;
    }

    @Override
    public void run() {
        Logger.customLog("TableUpdate", "Starting. Waiting On Table.");
        ChannelStorable channelStorable = getChannel(ChannelID);


        TextChannel channel = null;
        while (true) {
            boolean complete = false;


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
        Logger.customLog("TableUpdate", "Table Requested. Now Owner");
        if (table==null) {
            channelStorable.relinquishTable();
            channelStorable.relinquishAccess();
            Logger.customLog("TableUpdate", "Table Relinquished. Finished.");
            return;
        }

        table.Update();
        channel= Main.api.getTextChannelById(ChannelID);
        if (table.state==TableState.RECRUITING && !channelStorable.isTableRecruiting()) {
            channel.sendMessage("Recruitment was Interrupted. Restarting Recruitment.").queue();
            Main.threadManager.addTask(new Thread(new TimeoutBlackjackTable(ChannelID)));
        }
        if (table.state==TableState.INSURING && !channelStorable.isTableInsuring()) {
            channelStorable.setTableInsuring(true);
            channel.sendMessage("Insuring was Interrupted. Restarting Insuring.").queue();
            Main.threadManager.addTask(new Thread(new Waiting(15, new Thread(new Insuring( ChannelID) ))));
        }
        channelStorable.relinquishTable();
        channelStorable.relinquishAccess();
        Logger.customLog("TableUpdate", "Table Relinquished. Finished.");
    }
}