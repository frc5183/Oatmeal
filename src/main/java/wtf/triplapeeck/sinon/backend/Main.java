package wtf.triplapeeck.sinon.backend;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import wtf.triplapeeck.sinon.backend.commands.currency.Balance;
import wtf.triplapeeck.sinon.backend.commands.*;
import wtf.triplapeeck.sinon.backend.commands.essential.*;
import wtf.triplapeeck.sinon.backend.commands.games.cards.PrintDeck;
import wtf.triplapeeck.sinon.backend.commands.games.cards.TestHand;
import wtf.triplapeeck.sinon.backend.commands.games.cards.blackjack.*;
import wtf.triplapeeck.sinon.backend.commands.miscellaneous.Ping;
import wtf.triplapeeck.sinon.backend.commands.miscellaneous.Remind;
import wtf.triplapeeck.sinon.backend.commands.sinon.admin.SetAdmin;
import wtf.triplapeeck.sinon.backend.commands.sinon.owner.Count;
import wtf.triplapeeck.sinon.backend.commands.sinon.owner.SetOwner;
import wtf.triplapeeck.sinon.backend.commands.sinon.owner.SetStatus;
import wtf.triplapeeck.sinon.backend.commands.sinon.owner.RebootSinon;
import wtf.triplapeeck.sinon.backend.commands.sinon.trip.RebootVirgo;
import wtf.triplapeeck.sinon.backend.listeners.*;
import wtf.triplapeeck.sinon.backend.runnable.Heartbeat;
import wtf.triplapeeck.sinon.backend.storable.StorableFactory;
import wtf.triplapeeck.sinon.backend.storable.TriviaStorable;

import javax.security.auth.login.LoginException;
import java.io.IOException;


public class Main {
    public static DefaultListener listener;
    public static ThreadManager threadManager;
    public static JDA api;
    public static TriviaStorable ts;
    public static CommandHandler commandHandler = new CommandHandler("s!", 0);
    public static void main(String[] args) throws LoginException, InterruptedException, IOException {
        listener=new DefaultListener();
        threadManager=new ThreadManager();
        api = JDABuilder.createDefault(Secrets.getToken())
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(listener).build();
        api.awaitReady();
        StorableFactory sf = new StorableFactory(0L);
        ts = sf.triviaStorable();
        commandHandler.addCommand(new Balance());
        commandHandler.addCommand(new Ping());
        commandHandler.addCommand(new Help());
        commandHandler.addCommand(new PrintDeck());
        commandHandler.addCommand(new SetAdmin());
        commandHandler.addCommand(new SetOwner());
        commandHandler.addCommand(new RebootVirgo());
        commandHandler.addCommand(new RebootSinon());
        commandHandler.addCommand(new TestHand());
        //commandHandler.addCommand(new Test());
        commandHandler.addCommand(new CreateBlackjackTable());
        commandHandler.addCommand(new StartTable());
        commandHandler.addCommand(new DoubleDown());
        commandHandler.addCommand(new Hit());
        commandHandler.addCommand(new Insure());
        commandHandler.addCommand(new JoinBlackjackTable());
        commandHandler.addCommand(new Split());
        commandHandler.addCommand(new Stand());
        commandHandler.addCommand(new GetBlackjackTable());
        commandHandler.addCommand(new NewCustom());
        commandHandler.addCommand(new RemoveCustom());
        commandHandler.addCommand(new SetStarboard());
        commandHandler.addCommand(new RemoveStarboard());
        commandHandler.addCommand(new SetStarboardLimit());
        commandHandler.addCommand(new RakMessages());
        commandHandler.addCommand(new SetCurrencyEnabled());
        commandHandler.addCommand(new SetStatus());
        commandHandler.addCommand(new Nuke());
        commandHandler.addCommand(new Ban());
        commandHandler.addCommand(new Count());
        commandHandler.addCommand(new Remind());
        threadManager.addTask(new Heartbeat());
    }


}