package wtf.triplapeeck.sinon.backend.runnable;

import net.dv8tion.jda.api.entities.TextChannel;
import wtf.triplapeeck.sinon.backend.Logger;
import wtf.triplapeeck.sinon.backend.Main;
import wtf.triplapeeck.sinon.backend.Utils;
import wtf.triplapeeck.sinon.backend.errors.UsedTableException;
import wtf.triplapeeck.sinon.backend.games.cards.Table;
import wtf.triplapeeck.sinon.backend.games.cards.TableState;
import wtf.triplapeeck.sinon.backend.storable.ChannelStorable;


import static java.lang.Thread.sleep;
import static wtf.triplapeeck.sinon.backend.storable.StorableManager.getChannel;

public class TimeoutBlackjackTable implements Runnable {

    TextChannel channel;
    private final long id;

    Table table;

    ChannelStorable channelStorable;
    public TimeoutBlackjackTable(long ID ) {
        id=ID;
    }
    @Override
    public void run() {
        channelStorable = getChannel(id);
        channelStorable.setTableInsuring(true);
        Logger.customLog("Timeout","Starting. Waiting On Table.");
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
        Logger.customLog("Timeout", "Table Requested. Now Owner");
        channel=Main.api.getTextChannelById(id);
        if (table==null) {
            channel.sendMessage("There is not currently a table.").queue();
            channelStorable.relinquishTable();
            channelStorable.relinquishAccess();
            channelStorable.setTableInsuring(false);
            Logger.customLog("Timeout","Table Relinquished. Finished. ");
            return;
        }
        if (table.state==TableState.INACTIVE) {
            table.state=TableState.RECRUITING;

        }
        if (table.state!=TableState.RECRUITING) {
            channel.sendMessage("The table is currently active.").queue();
            channelStorable.relinquishTable();
            channelStorable.relinquishAccess();
            channelStorable.setTableInsuring(false);
            Logger.customLog("Timeout","Table Relinquished. Finished. ");
            return;
        }
        channel.sendMessage("Recruiting for 30 Seconds.").complete();
        channelStorable.relinquishTable();
        try {
            sleep(Utils.threadSeconds(30));
        } catch (InterruptedException ignored) {
        }
        while (true) {
            boolean complete=false;


            try {
                //noinspection InfiniteLoopStatement
                while (true) {
                    table = channelStorable.getTable();
                    complete=true;
                }
            } catch (UsedTableException ignored) {

            }
            if (complete) {
                break;
            }
        }
        Main.listener.pause();
        Logger.basicLog(Logger.Level.INFO, "Paused");
        if (table.getNumberOfPlayers()>0) {
            table.FirstDeal();
        } else {

            table.state=TableState.INACTIVE;
        }
        Main.listener.resume();
        Logger.basicLog(Logger.Level.INFO, "Resumed");
        channelStorable.relinquishTable();
        channelStorable.relinquishAccess();
        channelStorable.setTableInsuring(false);
        Logger.customLog("Timeout","Table Relinquished. Finished. ");
    }
}
