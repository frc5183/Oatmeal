package wtf.triplapeeck.sinon.backend;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import wtf.triplapeeck.sinon.backend.commands.currency.Balance;
import wtf.triplapeeck.sinon.backend.commands.*;
import wtf.triplapeeck.sinon.backend.commands.games.cards.PrintDeck;
import wtf.triplapeeck.sinon.backend.commands.games.cards.TestHand;
import wtf.triplapeeck.sinon.backend.commands.essential.Help;
import wtf.triplapeeck.sinon.backend.commands.games.cards.blackjack.*;
import wtf.triplapeeck.sinon.backend.commands.miscellaneous.Ping;
import wtf.triplapeeck.sinon.backend.commands.sinon.admin.SetAdmin;
import wtf.triplapeeck.sinon.backend.commands.sinon.owner.SetOwner;
import wtf.triplapeeck.sinon.backend.commands.sinon.trip.RebootSinon;
import wtf.triplapeeck.sinon.backend.commands.sinon.trip.RebootVirgo;
import wtf.triplapeeck.sinon.backend.listeners.*;
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
        api = JDABuilder.createDefault(Secrets.getToken()).addEventListeners(listener).build();
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
    }


}