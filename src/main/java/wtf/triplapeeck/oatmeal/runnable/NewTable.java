package wtf.triplapeeck.oatmeal.runnable;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import wtf.triplapeeck.oatmeal.cards.Table;
import wtf.triplapeeck.oatmeal.entities.ChannelData;
import wtf.triplapeeck.oatmeal.errors.UsedTableException;
import wtf.triplapeeck.oatmeal.errors.ValidTableException;
import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.Main;

public class NewTable implements NamedRunnable {
    String name = "NEWTABLE";
    public String getName() {
        return name;
    }
    private long id;
    Table table;
    public NewTable(long ID) {
        id=ID;
    }
    @Override
    public void run() {
        ChannelData channelStorable = Main.dataManager.getChannelData(String.valueOf(id));
        Logger.customLog("NewTable","Starting. Waiting On Table.");
        table=channelStorable.loadTable();

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
                channelStorable.releaseTable();
        channelStorable.release();

        Logger.customLog("NewTable","Table Relinquished. Finished. ");
    }
}
