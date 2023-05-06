package wtf.triplapeeck.oatmeal.commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.entities.GenericData;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;
import wtf.triplapeeck.oatmeal.entities.json.GenericJSONStorable;
import wtf.triplapeeck.oatmeal.DataCarriage;
import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.runnable.TableUpdate;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

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

    public void handle(@NotNull MessageReceivedEvent event, @NotNull JDA api, ThreadManager listener) {
        DataCarriage carriage;
        carriage = new DataCarriage();
        if ( event.getAuthor().getIdLong()==564635010917859332L) return;
        Logger.customLog("Listener", "Prepare");
        Prepare(event, api, carriage);
        Logger.customLog("Listener", "HandleMessage");
        HandleMessage(event, carriage);
        Logger.customLog("Listener", "Main");
        if (carriage.args[level].startsWith(prefix)) {
            String command = carriage.args[0].replace(prefix, "");
            AtomicBoolean caught= new AtomicBoolean(false);
            commandList.forEach(c -> {
                if (c.getName().equals(command)) {
                    caught.set(true);
                    c.handler(event, carriage, listener);
                }
            });

            if (!caught.get()) {
                String customCommand = carriage.guildEntity.getCustomCommands().get(carriage.args[level].substring(prefix.length()));
                if (customCommand!=null) {
                    carriage.channel.sendMessage(customCommand).queue();
                }
            }
            Logger.customLog("Listener", "Message Clean");
            Clean(carriage);

        } else {
            Logger.customLog("Listener", "Message Clean");
            Clean(carriage);
        }

    }
    private void Prepare(@NotNull MessageReceivedEvent event, JDA api, @NotNull DataCarriage carriage) {

        carriage.api=api;
        carriage.commandsList=commandList;
        carriage.args = event.getMessage().getContentRaw().split("\\s+");
        try {
            carriage.textAfterSubcommand = event.getMessage().getContentRaw().substring(2+carriage.args[0].length() + carriage.args[1].length());
        } catch (IndexOutOfBoundsException e) {
            carriage.textAfterSubcommand = null;
        }
        carriage.random=mainRandomizer;
        carriage.user = event.getAuthor();
        carriage.channel = event.getChannel();

        try {
            carriage.guild = event.getGuild();
            carriage.guildEntity=Main.dataManager.getGuildData(carriage.guild.getId());

        } catch (IllegalStateException e) {
            carriage.guildEntity = Main.dataManager.getGuildData(carriage.channel.getId() + "0000");

        }

        carriage.message=event.getMessage();
        carriage.userEntity = Main.dataManager.getUserData(carriage.user.getId());

        carriage.channelStorable = Main.dataManager.getChannelData(carriage.channel.getId());
        carriage.memberStorable = Main.dataManager.getMemberData(String.valueOf(carriage.user.getIdLong()) + carriage.guildEntity.getID());
        GenericData gs = Main.dataManager.getGenericData("0");
        gs.getKnownUserList().put(carriage.user.getId(), carriage.user.getId());
        gs.release();
    }
    private void HandleMessage(MessageReceivedEvent event, DataCarriage carriage) {
        carriage.memberStorable.setMessageCount(carriage.memberStorable.getMessageCount()+1);
        carriage.message = event.getMessage();
        double lucky5int=mainRandomizer.nextInt(25)-12.5;
        boolean lucky5 = (lucky5int+carriage.memberStorable.getMessageCount()) >50;
        int lucky5rak = mainRandomizer.nextInt(25)+25;
        if(!carriage.user.isBot() && lucky5  && carriage.guildEntity.isCurrencyEnabled()) {
            carriage.memberStorable.setRak(carriage.memberStorable.getRak().add(BigInteger.valueOf(lucky5rak)));
            if (carriage.userEntity.isCurrencyPreference()) {
                carriage.channel.sendMessage("Here, have " + lucky5rak + " rak. Enjoy!" +
                        "\n*To opt out of Random RAK gain notification use the command s!rakmessage disable)").queue();
            }
            carriage.memberStorable.setMessageCount(0);
        }

    }
    private void Clean(DataCarriage carriage) {
        Logger.customLog("Listener", "Pre Table Update");
        Main.threadManager.addTask(new TableUpdate(carriage.channel.getIdLong()));
        Logger.customLog("Listener", "Post Table Update");
        carriage.userEntity.release();
        carriage.memberStorable.release();
        carriage.channelStorable.release();
        carriage.guildEntity.release();
        Logger.customLog("Listener", "Finished");
    }

}
