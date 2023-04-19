package wtf.triplapeeck.sinon.backend.runnable;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import wtf.triplapeeck.sinon.backend.Logger;
import wtf.triplapeeck.sinon.backend.Main;
import wtf.triplapeeck.sinon.backend.errors.ClosedStorableError;
import wtf.triplapeeck.sinon.backend.errors.InvalidCardActionException;
import wtf.triplapeeck.sinon.backend.errors.UsedTableException;
import wtf.triplapeeck.sinon.backend.games.cards.SpotGroup;
import wtf.triplapeeck.sinon.backend.games.cards.Table;
import wtf.triplapeeck.sinon.backend.games.cards.TableState;
import wtf.triplapeeck.sinon.backend.storable.ChannelStorable;

import java.sql.Time;

import static wtf.triplapeeck.sinon.backend.storable.StorableManager.getChannel;

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
        ChannelStorable channelStorable = null;
        try {
            channelStorable = getChannel(ChannelID);
            channelStorable.relinquishAccess();
        } catch (ClosedStorableError e) {

        }
        int count=0;
        TextChannel channel = null;
        while (true) {
            boolean complete = false;

            try {
                    channelStorable = getChannel(ChannelID);
                    table = channelStorable.getTable();
                    complete = true;
            } catch (UsedTableException ignored) {
                channelStorable.relinquishAccess();
            } catch (ClosedStorableError e) {

            }
            if (complete) {
                break;
            }

        }
       if (table==null) {
            channelStorable.relinquishTable();
            channelStorable.relinquishAccess();
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
                channelStorable.relinquishTable();
        channelStorable.relinquishAccess();

        }
}