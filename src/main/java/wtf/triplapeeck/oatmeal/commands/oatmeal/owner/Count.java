package wtf.triplapeeck.oatmeal.commands.oatmeal.owner;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.DataCarriage;
import wtf.triplapeeck.oatmeal.Page;
import wtf.triplapeeck.oatmeal.commands.Command;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;

public class Count extends Command {
    @Override
    public void handler(MessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {
        if (ensureIsOwner(carriage)) {
            int count = 0;
            String out = "Servers I'm In: ";
            for (Guild guild : carriage.api.getSelfUser().getMutualGuilds()) {
                count++;
                out += guild.getName() + ", ";
            }
            out = out.substring(0, out.length()-2);
            do {

                carriage.channel.sendMessage(out.substring(0, Math.min(2000, out.length()))).queue();
                try {
                    out = out.substring(2000, -1);
                } catch (IndexOutOfBoundsException e) {
                    out = "";
                }

            } while (out.length() > 0);
            carriage.channel.sendMessage("Total Servers: " + count).queue();
        }
    }

    @NotNull
    @Override
    public String getDocumentation() {
        return "Usage:" +
                "\ns!count" +
                "\nthis will count the number of servers I'm in and show their related info.";
    }

    @Override
    public @NotNull String getName() {
        return "count";
    }

    @Override
    public @NotNull boolean hasPermission(DataCarriage carriage, User user) {
        return isOwner(carriage);
    }
    public Count() {
        Page.SinonOwner.addCommand(this);}
}
