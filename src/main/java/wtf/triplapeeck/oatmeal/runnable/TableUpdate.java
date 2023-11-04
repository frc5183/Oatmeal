package wtf.triplapeeck.oatmeal.runnable;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import wtf.triplapeeck.oatmeal.entities.ChannelData;
import wtf.triplapeeck.oatmeal.errors.UsedTableException;
import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.cards.Table;
import wtf.triplapeeck.oatmeal.cards.TableState;

public class TableUpdate implements NamedRunnable {
    String name = "TABLEUPDATE";
    public String getName() {
        return name;
    }
    private long ChannelID;
    Table table;

    public TableUpdate(long ChannelID) {
        this.ChannelID = ChannelID;
    }

    @Override
    public void run() {
        Logger.customLog("TableUpdate", "Starting. Waiting On Table.");
        ChannelData channelStorable = null;
            channelStorable =  Main.dataManager.getChannelData(String.valueOf(ChannelID));
            channelStorable.release();
        int count=0;
        TextChannel channel = null;
        while (true) {
            boolean complete = false;

            try {
                channelStorable =  Main.dataManager.getChannelData(String.valueOf(ChannelID));
                    table = channelStorable.getTable();
                    complete = true;
            } catch (UsedTableException ignored) {
                channelStorable.release();
            }
            if (complete) {
                break;
            }

        }
       if (table==null) {
            channelStorable.releaseTable();
            channelStorable.release();
            return;
        }
       table.Update();

        channel= Main.api.getTextChannelById(ChannelID);
        try {
            if (table.state == TableState.RECRUITING && !channelStorable.isTableRecruiting()) {
                 Main.threadManager.addTask((new TimeoutBlackjackTable(ChannelID)));
            }
            if (table.state == TableState.INSURING && !channelStorable.isTableInsuring()) {
                channelStorable.setTableInsuring(true);
                Main.threadManager.addTask((new Waiting(15, (new Insuring(ChannelID)))));
            }
        } catch (Error e) {

        } catch (Exception e) {

        }
                channelStorable.releaseTable();
        channelStorable.release();

        }
}