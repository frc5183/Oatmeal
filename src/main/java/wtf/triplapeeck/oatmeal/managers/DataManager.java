package wtf.triplapeeck.oatmeal.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.entities.GuildData;
import wtf.triplapeeck.oatmeal.entities.UserData;
import wtf.triplapeeck.oatmeal.entities.mariadb.MariaGuild;
import wtf.triplapeeck.oatmeal.entities.mariadb.MariaUser;

import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public abstract class DataManager extends Thread {
    boolean requestToEnd = false;
    protected static final ArrayList<String> temp = new ArrayList<>();
    protected static final ConcurrentHashMap<String, GuildData> guildCache = new ConcurrentHashMap<>();
    protected static final ConcurrentHashMap<String, UserData> userCache = new ConcurrentHashMap<>();
    protected final GsonBuilder gsonBuilder = new GsonBuilder();
    public synchronized void requestToEnd() {
        requestToEnd=true;
    }
    public final Gson gson = gsonBuilder.create();

    protected abstract GuildData getRawGuildData(String id);
    protected abstract void saveGuildData(String id);
    protected abstract UserData getRawUserData(String id);
    protected abstract void saveUserData(String id);
    public GuildData getGuildData(String id) {
        GuildData guildData;
        if (guildCache.get(id)==null) {
            guildData=getRawGuildData(id);
            guildCache.put(id, guildData);
        } else {
            guildData=guildCache.get(id);
        }
        guildData.request();
        return guildData;
    }
    public UserData getUserData(String id) {
        UserData userData;
        if (userCache.get(id)==null) {
            userData=getRawUserData(id);
            userCache.put(id, userData);
        } else {
            userData=userCache.get(id);
        }
        userData.request();
        return userData;
    }
    @Override
    public void run() {
        while(true) {
            if (requestToEnd) {
                while (Main.threadManager.getState()!=State.TERMINATED) {
                    try {
                      sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                temp.addAll(guildCache.keySet());
                for (String key: temp) {
                    saveGuildData(key);
                }
                temp.clear();
                temp.addAll(userCache.keySet());
                for (String key: temp) {
                    saveUserData(key);
                }
                temp.clear();
                break;
            }

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (String key: guildCache.keySet()) {
                GuildData guildData = guildCache.get(key);
                if (guildData.getEpoch()!=0 && guildData.getAccessCount()==0) {
                    if (Instant.now().getEpochSecond() > 30 + guildData.getEpoch()) {
                        temp.add(key);
                    }
                } else {
                    guildData.resetEpoch();
                }
            }
            for (String key: temp) {
                GuildData guildData = guildCache.get(key);
                if (guildData.getAccessCount()==0) {
                    saveGuildData(key);
                }
            }
            temp.clear();
            for (String key: userCache.keySet()) {
                UserData userData = userCache.get(key);
                if (userData.getEpoch()!=0 && userData.getAccessCount()==0) {
                    if (Instant.now().getEpochSecond()>30 + userData.getEpoch()) {
                        temp.add(key);
                    }
                } else {
                    userData.resetEpoch();
                }
            }
            for (String key: temp) {
                UserData userData = userCache.get(key);
                if (userData.getAccessCount()==0) {
                    saveUserData(key);
                }
            }
            temp.clear();
            //TODO: REPEAT FOR ALL ENTITY TYPES
        }
    }
}
