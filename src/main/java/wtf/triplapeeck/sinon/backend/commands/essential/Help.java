package wtf.triplapeeck.sinon.backend.commands.essential;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.sinon.backend.DataCarriage;
import wtf.triplapeeck.sinon.backend.Page;
import wtf.triplapeeck.sinon.backend.commands.Command;
import wtf.triplapeeck.sinon.backend.listeners.ThreadManager;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Help extends Command {
    private final ArrayList<Page> pageList = Page.getPages();
    public void handler(@NotNull GuildMessageReceivedEvent event, @NotNull DataCarriage carriage, ThreadManager listener) {

        try {
            AtomicBoolean commandFound= new AtomicBoolean(false);
            String s = carriage.args[1];
            try {


                int g = Integer.parseInt(s)-1;
                Page page = pageList.get(g);
                if (page==Page.TripOnly) {
                    if (carriage.user.getIdLong()==222517551257747456L) {
                        listCommands(carriage, g, page);
                    } else {
                        carriage.channel.sendMessage("This Page is reserved only for Trip-kun").queue();
                    }

                } else if (page==Page.SinonOwner) {
                    if (carriage.userStorable.isOwner()) {
                        listCommands(carriage, g, page);
                    } else {
                        carriage.channel.sendMessage("This Page is reserved only for Sinon's Owners").queue();
                    }

                } else if (page==Page.SinonAdmin) {
                    if (carriage.userStorable.isAdmin()) {
                        listCommands(carriage, g, page);
                    } else {
                        carriage.channel.sendMessage("This Page is reserved only for Sinon's Admins").queue();
                    }
                } else {
                    listCommands(carriage, g, page);
                }

            }
                    catch (IndexOutOfBoundsException e) {
                    carriage.channel.sendMessage("No page with number \"" + s + "\" was found.").queue();
                }

             catch (NumberFormatException e) {
                carriage.commandsList.forEach(c -> {
                    if (s.equals(c.getName())) {
                        carriage.channel.sendMessage(c.getDocumentation()).queue();
                        commandFound.set(true);
                    }
                });
                if (!commandFound.get()) {
                    carriage.channel.sendMessage("No command with name \"" + s + "\" was found.").queue();
                }
            }
        } catch(ArrayIndexOutOfBoundsException e) {
            AtomicInteger atomicInteger = new AtomicInteger(0);
            AtomicReference<String> list = new AtomicReference<>("Available Command Pages: ");
            pageList.forEach(c -> {
                atomicInteger.addAndGet(1);
                if (c==Page.TripOnly) {
                    if (carriage.user.getIdLong()==222517551257747456L) {
                        list.set(list + "\n" + atomicInteger.get()+ " " + c.getName());
                    }

                } else if (c==Page.SinonOwner) {
                    if (carriage.userStorable.isOwner()) {
                        list.set(list + "\n" + atomicInteger.get() + " " + c.getName());
                    }

                } else if (c==Page.SinonAdmin) {
                    if (carriage.userStorable.isAdmin()) {
                        list.set(list + "\n" + atomicInteger.get() + " " + c.getName());
                    }
                } else {
                    list.set(list + "\n" + atomicInteger.get() + " " + c.getName());
                }



            });
            carriage.channel.sendMessage(list.get()).queue();
        }
    }

    private void listCommands(@NotNull DataCarriage carriage, int g, @NotNull Page page) {
        AtomicReference<String> list = new AtomicReference<>(page.getName() + ":");
        pageList.get(g).getCommandList().forEach(c -> {
            if (c.hasPermission(carriage, carriage.user))
            {
                list.set(list + "\n" + c.getName());
            }
        });
        carriage.channel.sendMessage(list.get()).queue();
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


    public Help() {
        Page.Essential.addCommand(this);
    }
}
