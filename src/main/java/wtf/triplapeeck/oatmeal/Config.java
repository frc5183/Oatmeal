package wtf.triplapeeck.oatmeal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to store the configuration of the bot.
 * This class stores the token, owners, and database configuration.
 * It stores it in the current working directory in a file called config.json
 */
public class Config {
    //TODO: Add System Dependent Config File Placement
    public static Config config;
    protected static GsonBuilder jsonBuilder = new GsonBuilder();
    protected static Gson json = jsonBuilder.create();
    public String token  = "";
    public List<Long> owners = new ArrayList<>();
    public static FileRW fileRW;

    //DATABASE CONFIGURATION
    public int version = 1; // BOOTSTRAP. FOR FUTURE VERSION THIS WILL MATCH DatabaseUtil.VERSION
    public String address = "";
    public String path = "D:\\sinondata\\";
    public int port = 0;
    public String username = "";
    public String password = "";
    public String database = "";
    public int maxConnections = 10;


    /**
     * Gets the config object from the config.json file in the current working directory.
     * If the object has already been fetched, it returns the cached version
     * This is the ONLY method that should be used to get a Config object
     * @return The config object
     */
    public static synchronized Config getConfig() {

        if (config != null) return config;
        String sPath = System.getProperty("user.dir") + "/config.json";
        Path path = Path.of(sPath);
        boolean fileExists = Files.exists(path);
        /*
          if the file does not exist, create a new config based on the default values
         */
        try {
            if (!fileExists) {
                new FileOutputStream(sPath, true).close();
            }
            fileRW = new FileRW(path);
            String data = fileRW.readAll();

            if (data == null || data.length() == 0 || data.equals("null")) {
                data = "{\"token\":\"\", \"owners\":[1234567890,1234567890]}";
            }
            config = json.fromJson(data, Config.class);
            return config;
        } catch (IOException e) {
            throw new Error("Config.json is inaccessible in the Current Working Directory");
        }
    }


    /**
     * Saves the config object to the config.json file in the current working directory.
     */
    public static synchronized void saveConfig() {
        try {
            fileRW.writeAll(json.toJson(config));
        } catch (IOException e) {
            Logger.basicLog(Logger.Level.WARN, e.toString());
        }
    }
}
