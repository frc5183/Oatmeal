package wtf.triplapeeck.sinon.backend.commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.sinon.backend.DataCarriage;
import wtf.triplapeeck.sinon.backend.Main;
import wtf.triplapeeck.sinon.backend.listeners.ThreadManager;
import wtf.triplapeeck.sinon.backend.runnable.TableUpdate;
import wtf.triplapeeck.sinon.backend.storable.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class CommandHandler {
    ArrayList<Command> commandList = new ArrayList<>();
    private final String prefix;
    private final Random mainRandomizer = new Random();
    private final int level;
    public CommandHandler(@NotNull String pre, int level1) {
        prefix=pre; level=level1;
    }
    public void addCommand(@NotNull Command command) {
        commandList.add(command);
    }

    public void handle(@NotNull GuildMessageReceivedEvent event, @NotNull JDA api, ThreadManager listener) {
        DataCarriage carriage;
        carriage = new DataCarriage();
        if ( event.getAuthor().getIdLong()==564635010917859332L) {return;}

        Prepare(event, api, carriage);
        HandleMessage(event, carriage);
        if (carriage.args[level].startsWith(prefix)) {
            String command = carriage.args[0].replace(prefix, "");
            commandList.forEach(c -> {
                if (c.getName().equals(command)) {
                    c.handler(event, carriage, listener);
                }
            });
            Clean(carriage);

        } else {
            Clean(carriage);
        }

    }
    private void Prepare(@NotNull GuildMessageReceivedEvent event, JDA api, @NotNull DataCarriage carriage) {
        carriage.api=api;
        carriage.commandsList=commandList;
        carriage.args = event.getMessage().getContentRaw().split("\\s+");
        carriage.random=mainRandomizer;
        carriage.user = event.getAuthor();
        carriage.guild = event.getGuild();
        carriage.channel = event.getChannel();
        carriage.message=event.getMessage();
        carriage.userStorable = StorableManager.getUser(carriage.user.getIdLong());
        carriage.guildStorable = StorableManager.getGuild(carriage.guild.getIdLong());
        carriage.channelStorable = StorableManager.getChannel(carriage.channel.getIdLong());
        carriage.memberStorable = StorableManager.getMember(String.valueOf(carriage.user.getIdLong()) + carriage.guild.getIdLong());

    }
    private void HandleMessage(GuildMessageReceivedEvent event, DataCarriage carriage) {
        carriage.memberStorable.setMessageCount(carriage.memberStorable.getMessageCount()+1);
        carriage.message = event.getMessage();
        double lucky5int=mainRandomizer.nextInt(25)-12.5;
        boolean lucky5 = (lucky5int+carriage.memberStorable.getMessageCount()) >50;
        int lucky5rak = mainRandomizer.nextInt(25)+25;
        if(!carriage.user.isBot() && lucky5  && carriage.guildStorable.getCurrencyEnabled()) {
            carriage.memberStorable.setRak(carriage.memberStorable.getRak().add(BigInteger.valueOf(lucky5rak)));
            if (carriage.userStorable.getCurrencyPreference()) {
                carriage.channel.sendMessage("Here, have " + lucky5rak + " rak. Enjoy!" +
                        "\n*To opt out of Random RAK gain notification use the command s!opt out RAK (NOT YET FUNCTIONAL. DAMAGE FROM BUBBLES IS STILL BEING REPAIRED.* )").queue();
            }
            carriage.memberStorable.setMessageCount(0);
        }

    }
    private void Clean(DataCarriage carriage) {
        Main.threadManager.addTask(new Thread(new TableUpdate(carriage.channel.getIdLong())));

        carriage.userStorable.relinquishAccess();
        carriage.memberStorable.relinquishAccess();
        carriage.channelStorable.relinquishAccess();
        carriage.guildStorable.relinquishAccess();
    }

}
