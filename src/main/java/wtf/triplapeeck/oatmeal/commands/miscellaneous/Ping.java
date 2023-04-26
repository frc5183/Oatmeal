package wtf.triplapeeck.oatmeal.commands.miscellaneous;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.commands.Command;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;
import wtf.triplapeeck.oatmeal.DataCarriage;
import wtf.triplapeeck.oatmeal.Page;

public class Ping extends Command {
    @Override
    public void handler(MessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {
        try {
            carriage.channel.sendMessage("Pong!: '" + carriage.message.getContentRaw().substring(7) + "'").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        } catch (StringIndexOutOfBoundsException e) {
            carriage.channel.sendMessage("Pong!").queue();
        }
    }
    @Override
    public @NotNull String getDocumentation() {
        return "Simply responds with Pong! followed by your message" +
                "\nUsed to Verify Sinon is working. " +
                "\nUsage: s!ping [Message]";
    }
    @Override
    public @NotNull String getName() {
        return "ping";
    }

    @Override
    public @NotNull boolean hasPermission(DataCarriage carriage, User user) {
        return true;
    }

    public Ping() {
        Page.Miscellaneous.addCommand(this);
    }
}
