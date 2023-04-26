package wtf.triplapeeck.sinon.backend.runnable;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import wtf.triplapeeck.sinon.backend.Logger;
import wtf.triplapeeck.sinon.backend.Main;
import wtf.triplapeeck.sinon.backend.errors.InvalidCardActionException;
import wtf.triplapeeck.sinon.backend.errors.UsedTableException;
import wtf.triplapeeck.sinon.backend.games.cards.SpotGroup;
import wtf.triplapeeck.sinon.backend.games.cards.Table;
import wtf.triplapeeck.sinon.backend.games.cards.TableState;
import wtf.triplapeeck.sinon.backend.storable.ChannelStorable;

import static wtf.triplapeeck.sinon.backend.storable.StorableManager.getChannel;

public class GetTable implements NamedRunnable {
    String name = "GETTABLE";
    public String getName() {
        return name;
    }
    private long ChannelID;

    Table table;
    public GetTable(long ChannelID) {
        this.ChannelID=ChannelID;
    }
    @Override
    public void run() {
        Logger.customLog("GetTable", "Starting. Waiting On Table.");
        ChannelStorable channelStorable = getChannel(ChannelID);


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
        Logger.customLog("GetTable", "Table Requested. Now Owner");
        channel= Main.api.getTextChannelById(ChannelID);
        if (table==null) {
            channel.sendMessage("There is not currently a table.").queue();
            channelStorable.relinquishTable();
            channelStorable.relinquishAccess();
            Logger.customLog("GetTable", "Table Relinquished. Finished. ");
            return;
        }
        if (table.state!= TableState.PLAYING && table.state!= TableState.INSURING) {
            channel.sendMessage("You can't get the state of the table right now.").queue();
        } else {
            Logger.customLog("GetTable", "Attempting To Get Table");
            String outText="The dealer's face up card is " + table.dealer.hand.hand.get(0).toString() + "\n";
            outText = outTextPlayer(outText, table.player1);
            outText = outTextPlayer(outText, table.player2);
            outText = outTextPlayer(outText, table.player3);
            outText = outTextPlayer(outText, table.player4);
            channel.sendMessage(outText).queue();
        }
                channelStorable.relinquishTable();
        channelStorable.relinquishAccess();

        Logger.customLog("GetTable", "Table Relinquished. Finished.");
    }
    private String outTextPlayer(String outText, SpotGroup player) {
        if (player.spot2!=null) {
            outText+=Main.api.retrieveUserById(player.userId).complete().getAsMention() + "'s First " + player.spot1.hand.toString()+"\n";
            outText+=Main.api.retrieveUserById(player.userId).complete().getAsMention() + "'s Second " + player.spot2.hand.toString()+"\n";
        } else if (player.spot1!=null) {
            outText+=Main.api.retrieveUserById(player.userId).complete().getAsMention() + "'s " + player.spot1.hand.toString()+"\n";
        }
        return outText;
    }
}