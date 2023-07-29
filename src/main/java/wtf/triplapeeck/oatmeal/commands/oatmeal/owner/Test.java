package wtf.triplapeeck.oatmeal.commands.oatmeal.owner;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.commands.Command;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;
import wtf.triplapeeck.oatmeal.DataCarriage;
import wtf.triplapeeck.oatmeal.runnable.Waiting;

public class Test extends Command
{
    @Override
    public void handler(MessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {
        if (ensureIsOwner(carriage)) {
            listener.addTask(new Waiting(15, null));
        }
    }

    @NotNull
    @Override
    public CommandCategory getCategory() {
        return CommandCategory.SINON_OWNER;
    }


    @Override
    public @NotNull String getDocumentation() {
        return "TESTONLY";
    }

    @Override
    public @NotNull String getName() {
        return "test";
    }

    @Override
    public boolean hasPermission(DataCarriage carriage, User user) {
        return isOwner(carriage);
    }
}
