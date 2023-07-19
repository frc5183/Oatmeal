package wtf.triplapeeck.oatmeal.commands.essential;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.DataCarriage;
import wtf.triplapeeck.oatmeal.commands.Command;
import wtf.triplapeeck.oatmeal.commands.FakeCommand;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;

public class Help extends Command {
    public void handler(@NotNull MessageReceivedEvent event, @NotNull DataCarriage carriage, ThreadManager listener) {
        if (carriage.args.length < 2) {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Help");
            builder.setDescription("""
                    Oatmeal is a multipurpose bot with many features
                    You can do `s!help <command>` to get more information about a specific command.
                    You can also do `s!help <category>` to get a list of all commands in that category.
                    If you would like to contribute to Oatmeal, visit our Github at https://github.com/frc5183/oatmeal""");
            if (isTrip(carriage)) {
                builder.addField("Categories", """
                       1. Essential
                       2. Miscellaneous
                       3. Card Games
                       4. Currency
                       5. Custom Commands
                                
                       10. Sinon Admin
                       11. Sinon Owner
                       12. Trip Only
                        """, false);
            } else if (isOwner(carriage)) {
                builder.addField("Categories", """
                       1. Essential
                       2. Miscellaneous
                       3. Card Games
                       4. Currency
                       5. Custom Commands
                                
                       10. Sinon Admin
                       11. Sinon Owner
                        """, false);
            } else if (isAdmin(carriage)) {
                builder.addField("Categories", """
                       1. Essential
                       2. Miscellaneous
                       3. Card Games
                       4. Currency
                       5. Custom Commands
                               
                       10. Sinon Admin
                        """, false);
            } else {
                builder.addField("Categories", """
                       1. Essential
                       2. Miscellaneous
                       3. Card Games
                       4. Currency
                       5. Custom Commands
                        """, false);
            }
            builder.setColor(Color.CYAN);

            carriage.channel.sendMessageEmbeds(builder.build()).queue();
        }

        StringBuilder sBuilder = new StringBuilder();
        for (int i = 1; i < carriage.args.length; i++) {
            if (i == 1) {
                sBuilder.append(carriage.args[i]);
            } else {
                sBuilder.append(" ").append(carriage.args[i]);
            }
        }

        String s = sBuilder.toString();

        CommandCategory category;
        try {
            category = CommandCategory.fromInt(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            category = CommandCategory.fromString(s);
        }

        if (category == null) {
            if (getCommandList(carriage).containsKey(s)) {
                Command command = getCommandList(carriage).get(s);
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle(command.getName());
                builder.setDescription(command.getDocumentation());
                builder.setColor(Color.CYAN);
                carriage.channel.sendMessageEmbeds(builder.build()).queue();
                return;
            }
            carriage.channel.sendMessage("Invalid category or command.").queue();
            return;
        }

        if (category == CommandCategory.TRIP_ONLY) {
            if (isTrip(carriage)) {
                listCommands(carriage, category);
            } else {
                carriage.channel.sendMessage("This Page is reserved only for Trip-kun").queue();
            }
        } else if (category == CommandCategory.SINON_OWNER) {
            if (isOwner(carriage)) {
                listCommands(carriage, category);
            } else {
                carriage.channel.sendMessage("This Page is reserved only for Sinon's Owners").queue();
            }
        } else if (category == CommandCategory.SINON_ADMIN) {
            if (isAdmin(carriage)) {
                listCommands(carriage, category);
            } else {
                carriage.channel.sendMessage("This Page is reserved only for Sinon's Admins").queue();
            }
        } else {
            listCommands(carriage, category);
        }
    }

    @NotNull
    @Override
    public CommandCategory getCategory() {
        return CommandCategory.ESSENTIAL;
    }

    private void listCommands(@NotNull DataCarriage carriage, @NotNull CommandCategory category) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(category.getName());
        builder.setDescription(category.getDescription());

        StringBuilder commands = new StringBuilder();
        for (Command c : getCommandList(carriage).values()) {
            if (c.hasPermission(carriage, carriage.user) && c.getCategory() == category) {
                commands.append("\n`").append(c.getName()).append('`');
            }
        }

        builder.addField("Commands", commands.toString(), false);
        carriage.channel.sendMessageEmbeds(builder.build()).queue();
    }

    @NotNull
    private HashMap<String, Command> getCommandList(@NotNull DataCarriage carriage) {
        HashMap<String, Command> list = new HashMap<>(carriage.commandsList);

        if (carriage.guildEntity.getCustomCommands() == null) return list;

        for (Iterator<String> it = carriage.guildEntity.getCustomCommands().keys().asIterator(); it.hasNext(); ) {
            String str = it.next();
            list.put(str, new FakeCommand(str));
        }

        return list;
    }

    public @NotNull String getDocumentation() { return """
                    Used to get information about commands and categories.
                    Usage:
                    `s!help` - Get a list of all categories.
                    `s!help <command>` - Get more information about a specific command.
                    `s!help <category>` - to get a list of all commands in that category.""";}

    public @NotNull String getName() {
        return "help";
    }

    @Override
    public boolean hasPermission(DataCarriage carriage, User user) {
        return true;
    }
}
