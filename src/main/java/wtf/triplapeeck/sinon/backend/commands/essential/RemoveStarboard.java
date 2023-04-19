package wtf.triplapeeck.sinon.backend.commands.essential;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.sinon.backend.DataCarriage;
import wtf.triplapeeck.sinon.backend.Page;
import wtf.triplapeeck.sinon.backend.commands.Command;
import wtf.triplapeeck.sinon.backend.listeners.ThreadManager;

public class RemoveStarboard extends Command {
    @Override
    public void handler(MessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {
        if (ensureAdministrator(carriage)) {
            carriage.guildStorable.setStarboardChannelID(0L);
            carriage.channel.sendMessage("The starboard will no longer post").queue();


        }
    }
    @NotNull
    @Override
    public String getDocumentation() {
        return "Used to remove the current starboard for your server."+
                "\nUsage: s!removestarboard"+
                "\nRemoves the current starboard. NOTE: While it will no longer post, existing posts will not be removed.";
    }

    @Override
    public @NotNull String getName() {
        return "removestarboard";
    }

    @Override
    public @NotNull boolean hasPermission(DataCarriage carriage, User user) {
        return isAdministrator(carriage);
    }

    public RemoveStarboard() {
        Page.Essential.addCommand(this);}
}
