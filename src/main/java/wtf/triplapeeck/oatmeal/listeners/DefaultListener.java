package wtf.triplapeeck.oatmeal.listeners;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.events.GuildEmojiAddEvent;
import wtf.triplapeeck.oatmeal.events.GuildEmojiRemoveEvent;

import static wtf.triplapeeck.oatmeal.Main.commandHandler;
import static wtf.triplapeeck.oatmeal.Main.threadManager;

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
        waiter = threadManager;
        Logger.customLog("Listener", "Starting ThreadManager");
        waiter.start();
    }
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Logger.customLog("Listener", "Message Received");
        while (!isActive) {
        }
        Logger.customLog("Listener", "Message Pre");
        commandHandler.handle(event, api, waiter);
        Logger.customLog("Listener", "Message Post");
    }

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        Logger.customLog("Listener", "Reaction Event Received");
        while (!isActive) {
        }
        threadManager.addTask(new GuildEmojiAddEvent(event));
    }

    public void onMessageReactionRemove(@NotNull MessageReactionRemoveEvent event) {
        Logger.customLog("Listener", "Reaction Event Received");
        while (!isActive) {
        }
        threadManager.addTask(new GuildEmojiRemoveEvent(event));
    }
}