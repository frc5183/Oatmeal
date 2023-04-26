package wtf.triplapeeck.sinon.backend.commands;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.sinon.backend.DataCarriage;
import wtf.triplapeeck.sinon.backend.listeners.ThreadManager;

public class FakeCommand extends Command {
    private String name;
    @Override
    public void handler(MessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {

    }

    @NotNull
    @Override
    public String getDocumentation() {
        return "s!" + name + " is a custom command within this server!"
                +"\nUsage: s!" + name;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public @NotNull boolean hasPermission(DataCarriage carriage, User user) {
        return true;
    }
    public FakeCommand(String fakeName) {
        name=fakeName;
    }
}
