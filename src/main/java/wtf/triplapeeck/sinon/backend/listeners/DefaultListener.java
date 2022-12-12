package wtf.triplapeeck.sinon.backend.listeners;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.sinon.backend.Logger;
import wtf.triplapeeck.sinon.backend.Main;

import static wtf.triplapeeck.sinon.backend.Main.commandHandler;

public class DefaultListener extends ListenerAdapter
{
    public boolean isActive=true;
    public long pauseCount=0;

    public synchronized void pause() {
        isActive=false;
        pauseCount+=1;
    }
    public synchronized void resume() {
        pauseCount-=1;

        if(pauseCount<=0) {
            isActive=true;
        }
    }
    ThreadManager waiter;
    private JDA api;
    public DefaultListener() {
        ;
    }
    @Override
    public void onReady(@NotNull ReadyEvent event) {
        Logger.customLog("Listener", "Ready!");
        api=Main.api;
        waiter =Main.threadManager;
        Logger.customLog("Listener", "Starting ThreadManager");
        waiter.start();
    }
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        Logger.customLog("Listener", "Message Received");
        while (!isActive) {
        }
        commandHandler.handle(event, api, waiter);
    }


}