package wtf.triplapeeck.oatmeal.runnable;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.requests.RestAction;
import wtf.triplapeeck.oatmeal.Main;

import java.util.List;
import java.util.concurrent.CompletableFuture;


public class NukeRunnable implements NamedRunnable {
    long channel_id;
    MessageChannel channel;
    int toClear;
    RestAction messageSend;
    @Override
    public String getName() {
        return "NUKE";
    }

    @Override
    public void run() {
            channel = Main.api.getChannelById(MessageChannel.class, String.valueOf(channel_id));
            try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
        }
        MessageHistory history = channel.getHistory();
        boolean first = true;
        do {
            if (toClear<=100) {
                history.retrievePast(toClear).complete();
                toClear-=toClear;
            } else {
                history.retrievePast(100).complete();
                toClear-=100;
            }
            if (first) {
                first=false;
                messageSend.complete();
            }
        } while (toClear>0);
        if (history.getRetrievedHistory().size()<=1){
            for (Message message : history.getRetrievedHistory()) {
                message.delete().queue();
            }
        } else {
            List<CompletableFuture<Void>> list = channel.purgeMessages(history.getRetrievedHistory());
            CompletableFuture.allOf(list.toArray(new CompletableFuture[0])).complete(null);

        }
        channel.sendMessage("Nuking Complete!").complete();
    }
    public NukeRunnable(long guildId, long channelId, int clear, RestAction messageToSend) {
        channel_id=channelId;
        toClear=clear;
        messageSend=messageToSend;
    }
}
