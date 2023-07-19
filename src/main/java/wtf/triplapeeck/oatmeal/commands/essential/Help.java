package wtf.triplapeeck.oatmeal.commands.essential;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.DataCarriage;
import wtf.triplapeeck.oatmeal.commands.Command;
import wtf.triplapeeck.oatmeal.commands.CommandHandler;
import wtf.triplapeeck.oatmeal.commands.FakeCommand;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Help extends Command {
    public void handler(@NotNull MessageReceivedEvent event, @NotNull DataCarriage carriage, ThreadManager listener) {
        String categoryString = carriage.args[1];

        CommandCategory category = null;
        try {
            category = CommandCategory.fromInt(Integer.parseInt(categoryString));
        } catch (NumberFormatException e) {
            category = CommandCategory.fromString(categoryString);
        }

        if (category == null) {
            for (Command c : getCommandList(carriage)) {
                if (c.getName().equals(categoryString)) {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle(c.getName());
                    builder.setDescription(c.getDocumentation());
                    builder.setColor(Color.CYAN);
                }
                return;
            }
            carriage.channel.sendMessage("Invalid Category").queue();
            return;
        }

        if (category == CommandCategory.TRIP_ONLY) {
            if (carriage.user.getIdLong()==222517551257747456L) {
                listCommands(carriage, category);
            } else {
                carriage.channel.sendMessage("This Page is reserved only for Trip-kun").queue();
            }
        } else if (category == CommandCategory.SINON_OWNER) {
            if (carriage.userEntity.isOwner()) {
                listCommands(carriage, category);
            } else {
                carriage.channel.sendMessage("This Page is reserved only for Sinon's Owners").queue();
            }
        } else if (category == CommandCategory.SINON_ADMIN) {
            if (carriage.userEntity.isAdmin()) {
                listCommands(carriage, category);
            } else {
                carriage.channel.sendMessage("This Page is reserved only for Sinon's Admins").queue();
            }
        }
        listCommands(carriage, category);
    }

    @NotNull
    @Override
    public CommandCategory getCategory() {
        return CommandCategory.ESSENTIAL;
    }

    private void listCommands(@NotNull DataCarriage carriage, @NotNull CommandCategory page) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(page.getName());
        for (Command c : getCommandList(carriage)) {
            if (c.hasPermission(carriage, carriage.user) && c.getCategory() == page) {
                builder.addField(c.getName(), c.getDocumentation(), false);
            }
        }
        carriage.channel.sendMessageEmbeds(builder.build()).queue();
    }

    public ArrayList<Command> getCommandList(DataCarriage carriage) {
        ArrayList<Command> list = new ArrayList<>(CommandHandler.getCommandList());

        if (carriage.guildEntity.getCustomCommands() == null) return list;

        for (Iterator<String> it = carriage.guildEntity.getCustomCommands().keys().asIterator(); it.hasNext(); ) {
            String str = it.next();
            list.add(new FakeCommand(str));
        }

        return list;
    }

    public @NotNull String getDocumentation() { return "Used to gather info on Sinon's Commands"
            +"\nUsage: s!help" +
            "\nGathers a list of available command pages" +
            "\nUsage: s!help [Number]" +
            "\nGathers a list of available commands within a page group" +
            "\nUsage: s!help [Command]" +
            "\nGathers specific info on a command";}
    public @NotNull String getName() {
        return "help";
    }

    @Override
    public boolean hasPermission(DataCarriage carriage, User user) {
        return true;
    }
}
