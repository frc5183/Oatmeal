package wtf.triplapeeck.oatmeal.runnable;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import wtf.triplapeeck.oatmeal.cards.Table;
import wtf.triplapeeck.oatmeal.cards.TableState;
import wtf.triplapeeck.oatmeal.errors.UsedTableException;
import wtf.triplapeeck.oatmeal.storable.ChannelStorable;
import wtf.triplapeeck.oatmeal.storable.StorableManager;
import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.util.Utils;


import static java.lang.Thread.sleep;

public class TimeoutBlackjackTable implements NamedRunnable {
    String name = "TIMEOUTBLACKJACKTABLE";
    public String getName() {
        return name;
    }

    TextChannel channel;
    private final long id;

    Table table;

    ChannelStorable channelStorable;
    public TimeoutBlackjackTable(long ID ) {
        id=ID;
    }
    @Override
    public void run() {
        channelStorable = StorableManager.getChannel(id);
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
            Logger.customLog("Timeout","Table Relinquished. Finished. ");
            return;
        }
        if (table.state== TableState.RECRUITING) {
            if (Main.threadManager.containsType(this)) {
                channel.sendMessage("This table is currently recruiting.").queue();
                channelStorable.relinquishTable();
                channelStorable.relinquishAccess();
                Logger.customLog("Timeout","Table Relinquished. Finished. ");
                return;
            }
        }
        if (table.state==TableState.INACTIVE) {
            table.state=TableState.RECRUITING;

        }
        if (table.state!=TableState.RECRUITING) {
            channel.sendMessage("The table is currently active.").queue();
            channelStorable.relinquishTable();
            channelStorable.relinquishAccess();
            Logger.customLog("Timeout","Table Relinquished. Finished. ");
            return;
        }
        channel.sendMessage("Recruiting for 30 Seconds.").complete();
        channelStorable.setTableRecruiting(true);
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
        Logger.basicLog(Logger.Level.INFO, "Paused");
        if (table.getNumberOfPlayers()>0) {
            channelStorable.setTableRecruiting(false);
            table.FirstDeal();
        } else {
            channelStorable.setTableRecruiting(false);
            table.state=TableState.INACTIVE;
        }
        Logger.basicLog(Logger.Level.INFO, "Resumed");
                channelStorable.relinquishTable();
        channelStorable.relinquishAccess();

        Logger.customLog("Timeout","Table Relinquished. Finished. ");
    }
}
