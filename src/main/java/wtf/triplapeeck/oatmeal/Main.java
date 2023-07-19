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
import wtf.triplapeeck.oatmeal.entities.DataMode;
import wtf.triplapeeck.oatmeal.listeners.DefaultListener;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;
import wtf.triplapeeck.oatmeal.managers.DataManager;
import wtf.triplapeeck.oatmeal.managers.JSONManager;
import wtf.triplapeeck.oatmeal.managers.MariaManager;
import wtf.triplapeeck.oatmeal.runnable.Heartbeat;
import wtf.triplapeeck.oatmeal.entities.json.JSONStorableFactory;
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
        if (Config.getConfig().dataMode== DataMode.MARIADB) {
            try {
                dbUtil = new DatabaseUtil();
                dataManager = new MariaManager();
                dataManager.start();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (Config.getConfig().dataMode == DataMode.JSON) {
            dataManager= new JSONManager();
            dataManager.start();
        }
    }

    public static CommandHandler commandHandler = new CommandHandler("s!", 0);


    public static void main(String[] args) throws LoginException, InterruptedException, IOException, SQLException {
        listener=new DefaultListener();
        threadManager=new ThreadManager();
        api = JDABuilder.createLight(ConfigParser.getToken(args)).disableCache(new ArrayList<>(Arrays.asList(CacheFlag.values())))
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(listener).build();
        api.awaitReady();
        JSONStorableFactory sf = new JSONStorableFactory(0L);
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
        threadManager.addTask(new Heartbeat());
    }
}