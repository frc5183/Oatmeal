package wtf.triplapeeck.oatmeal.runnable;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import wtf.triplapeeck.oatmeal.cards.Table;
import wtf.triplapeeck.oatmeal.errors.UsedTableException;
import wtf.triplapeeck.oatmeal.errors.ValidTableException;
import wtf.triplapeeck.oatmeal.storable.ChannelStorable;
import wtf.triplapeeck.oatmeal.storable.StorableManager;
import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.Main;

public class NewTable implements NamedRunnable {
    String name = "NEWTABLE";
    public String getName() {
        return name;
    }
    private long id;
    Table table;
    ChannelStorable channelStorable;
    public NewTable(long ID) {
        id=ID;
    }
    @Override
    public void run() {
        channelStorable = StorableManager.getChannel(id);
        Logger.customLog("NewTable","Starting. Waiting On Table.");
        while (true) {
            boolean complete=false;


            try {
                while (true) {
                    table = channelStorable.getTable();
                    complete=true;
                }
            } catch (UsedTableException e) {
            }
            if (complete) {
                break;
            }
        }

        Logger.customLog("NewTable", "Table Requested. Now Owner");
        if (table==null) {
            try {
                Logger.customLog("NewTable","Attempting To Make New Table.");
                channelStorable.setTable( new Table(id));
            } catch (ValidTableException e) {
            }
        } else {
            TextChannel channel = Main.api.getTextChannelById(id);
            channel.sendMessage("A Table Already Exists!").queue();
        }
                channelStorable.relinquishTable();
        channelStorable.relinquishAccess();

        Logger.customLog("NewTable","Table Relinquished. Finished. ");
    }
}
