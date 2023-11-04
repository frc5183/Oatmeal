package wtf.triplapeeck.oatmeal;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import wtf.triplapeeck.oatmeal.commands.CommandHandler;
import wtf.triplapeeck.oatmeal.commands.currency.RakMessages;
import wtf.triplapeeck.oatmeal.commands.currency.Balance;
import wtf.triplapeeck.oatmeal.commands.essential.*;
import wtf.triplapeeck.oatmeal.commands.games.cards.PrintDeck;
import wtf.triplapeeck.oatmeal.commands.games.cards.TestHand;
import wtf.triplapeeck.oatmeal.commands.games.cards.blackjack.*;
import wtf.triplapeeck.oatmeal.commands.miscellaneous.Ping;
import wtf.triplapeeck.oatmeal.commands.miscellaneous.Remind;
import wtf.triplapeeck.oatmeal.commands.oatmeal.admin.SetAdmin;
import wtf.triplapeeck.oatmeal.commands.oatmeal.owner.Count;
import wtf.triplapeeck.oatmeal.commands.oatmeal.owner.RebootOatmeal;
import wtf.triplapeeck.oatmeal.commands.oatmeal.owner.SetOwner;
import wtf.triplapeeck.oatmeal.commands.oatmeal.owner.SetStatus;
import wtf.triplapeeck.oatmeal.listeners.DefaultListener;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;
import wtf.triplapeeck.oatmeal.managers.DataManager;
import wtf.triplapeeck.oatmeal.managers.MariaManager;
import wtf.triplapeeck.oatmeal.runnable.Heartbeat;
import wtf.triplapeeck.oatmeal.util.ConfigParser;
import wtf.triplapeeck.oatmeal.util.DatabaseUtil;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
public class Main {
    public static DefaultListener listener;
    public static ThreadManager threadManager;
    public static DataManager dataManager;
    public static JDA api;
    public static DatabaseUtil dbUtil;

    static {
            try {
                dbUtil = new DatabaseUtil();
                dataManager = new MariaManager();
                dataManager.start();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }

    public static CommandHandler commandHandler = new CommandHandler("s!", 0);


    /**
     * The starting point of the program.
     * @param args The first command line argument will be used as the token for the Discord Bot itself. If not included as a command line argument, it MUST be included in config.json
     * @throws LoginException
     * @throws InterruptedException
     * @throws IOException
     * @throws SQLException
     */
    public static void main(String[] args) throws LoginException, InterruptedException, IOException, SQLException {
        //Instantiates the main Discord Event Handler
        listener=new DefaultListener();
        //Instantiates the Thread Manager
        threadManager=new ThreadManager();
        api = JDABuilder.createLight(ConfigParser.getToken(args)).disableCache(new ArrayList<>(Arrays.asList(CacheFlag.values())))
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(listener).build();
        api.awaitReady();
        //Adds Every Command to the Command Handler
        commandHandler.addCommand(new Balance());
        commandHandler.addCommand(new Ping());
        commandHandler.addCommand(new Help());
        commandHandler.addCommand(new PrintDeck());
        commandHandler.addCommand(new SetAdmin());
        commandHandler.addCommand(new SetOwner());
        commandHandler.addCommand(new RebootOatmeal());
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
        //Adds the internal Heartbeat thread to the thread manager.
        threadManager.addTask(new Heartbeat());
    }
}