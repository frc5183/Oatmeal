package wtf.triplapeeck.oatmeal.managers;

import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.entities.mariadb.MariaGuild;
import wtf.triplapeeck.oatmeal.entities.mariadb.MariaUser;
import wtf.triplapeeck.oatmeal.util.DatabaseUtil;

import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class MariaManager extends DataManager {


    public synchronized MariaGuild getRawGuildData(String id)  {
        MariaGuild guildEntity;
        try {
            guildEntity = DatabaseUtil.getGuildEntity(id);
            if (guildEntity==null) {
                guildEntity = new MariaGuild(id);
            }
            guildEntity.load();
        } catch  (SQLException e){
            throw new RuntimeException(e);
        }
        return guildEntity;
        
    }


    public synchronized MariaUser getRawUserData(String id) {
        MariaUser userEntity;
        try {
            userEntity = DatabaseUtil.getUserEntity(id);
            if (userEntity==null) {
                userEntity = new MariaUser(id);
            }
            userEntity.load();
        } catch  (SQLException e){
            throw new RuntimeException(e);
        }
        return userEntity;
    }

    public void saveGuildData(String key) {
        MariaGuild guildEntity =(MariaGuild) guildCache.get(key);
        guildCache.remove(key);
        try {
            HashMap<String, String> step = new HashMap<>();
            for (String k : guildEntity.getCustomCommands().keySet()) {
                step.put(k, guildEntity.getCustomCommands().get(k));
            }
            guildEntity.setJsonCustomCommands(gson.toJson(step));
            HashMap<String, String> step2 = new HashMap<>();
            for (String k : guildEntity.getStarboardLink().keySet()) {
                step2.put(k, guildEntity.getStarboardLink().get(k));
            }
            guildEntity.setJsonStarboardLink(gson.toJson(step2));
            DatabaseUtil.updateGuildEntity(guildEntity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void saveUserData(String key) {
        MariaUser userEntity =  (MariaUser) userCache.get(key);
        userCache.remove(key);
        try {
            String step = gson.toJson(userEntity.getReminderMap());
            userEntity.setJsonReminders(step);
            DatabaseUtil.updateUserEntity(userEntity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
