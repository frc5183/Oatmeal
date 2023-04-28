package wtf.triplapeeck.oatmeal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.j256.ormlite.db.DatabaseType;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config {
    //TODO: Add System Dependent Config File Placement
    public static Config config;
    protected static GsonBuilder jsonBuilder = new GsonBuilder();
    protected static Gson json = jsonBuilder.create();
    public String token  = "";
    public static FileRW fileRW;

    //DATABASE CONFIGURATION
    public String address="";
    public int port=0;
    public String username="";
    public String password="";
    public String database="";


    public static synchronized Config getConfig() {

        if (config!=null) {

            return config;}
        String sPath = System.getProperty("user.dir") + "/config.json";
        Path path = Path.of(sPath);
        boolean fileExists = Files.exists(path);
        try {
            if (!fileExists) {
                new FileOutputStream(sPath, true).close();
            }
            fileRW = new FileRW(path);
            String data = fileRW.readAll();

            if (data==null || data.length()==0 || data.equals("null")) {
                data = "{token:\"\"}";
            }
            config = json.fromJson(data, Config.class);
            return config;
        } catch (IOException e) {
            throw new Error("Config.json is inaccessible in the Current Working Directory");
        }
    }



    public static synchronized void saveConfig() {
        try {

            fileRW.writeAll(json.toJson(config));
        } catch (IOException e) {
            Logger.basicLog(Logger.Level.WARN, e.toString());
        }
    }
}
