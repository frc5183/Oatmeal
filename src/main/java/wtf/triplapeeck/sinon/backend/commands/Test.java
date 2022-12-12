package wtf.triplapeeck.sinon.backend.commands;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.sinon.backend.DataCarriage;
import wtf.triplapeeck.sinon.backend.Page;
import wtf.triplapeeck.sinon.backend.runnable.Waiting;
import wtf.triplapeeck.sinon.backend.listeners.ThreadManager;

public class Test extends Command
{
    @Override
    public void handler(GuildMessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {
        listener.addTask(new Thread(new Waiting(15, null)));
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
        return true;
    }

    public Test() {
        Page.TripOnly.addCommand(this);
    }
}
