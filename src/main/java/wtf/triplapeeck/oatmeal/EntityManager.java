package wtf.triplapeeck.oatmeal;

import net.dv8tion.jda.api.entities.Guild;
import wtf.triplapeeck.oatmeal.entities.GuildEntity;
import wtf.triplapeeck.oatmeal.entities.UserEntity;
import wtf.triplapeeck.oatmeal.util.DatabaseUtil;

import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class EntityManager extends Thread {
    boolean requestToEnd=false;
    private static final ArrayList<String> temp = new ArrayList<>();
    private static final ConcurrentHashMap<String, GuildEntity> guildCache = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, UserEntity> userCache = new ConcurrentHashMap<>();

    public synchronized void requestToEnd() {
        requestToEnd=true;
    }

    public synchronized GuildEntity getGuildEntity(String id) {
        GuildEntity guildEntity;
        if (guildCache.get(id)==null) {
            try {
                guildEntity = Main.dbUtil.getGuildEntity(id);
                if (guildEntity==null) {
                    guildEntity = new GuildEntity(id);
                }
                guildCache.put(id, guildEntity);
            } catch  (SQLException e){
                throw new RuntimeException(e);
            }
        } else {
            guildEntity = guildCache.get(id);
        }
        guildEntity.request();
        return guildEntity;
    }
    public synchronized UserEntity getUserEntity(String id) {
        UserEntity userEntity;
        if (userCache.get(id)==null) {
            try {
                userEntity = Main.dbUtil.getUserEntity(id);
                if (userEntity==null) {
                    userEntity = new UserEntity(Long.valueOf(id));
                }
                userCache.put(id, userEntity);
            } catch  (SQLException e){
                throw new RuntimeException(e);
            }
        } else {
            userEntity = userCache.get(id);
        }
        userEntity.request();
        return userEntity;
    }
    @Override
    public void run() {
        while (true) {
            if (requestToEnd) {
                //PUSH REQUESTS TO DATABASE BEFORE QUITTING

                break;
            }


            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            for (String key: guildCache.keySet() ) {
                GuildEntity guildEntity = guildCache.get(key);
                /*
                IF NOW >= guildEntity.epoch+30 AND GUILD UNUSED THEN temp.add(key)
                IF GUILD UNUSED AND NO EPOCH THEN guildEntity.epoch=NOW
                IF GUILD USED AND EPOCH then NO EPOCH
                 */

            }
            for (String key: temp) {
                //GuildEntity = guildCache.get(key)
                guildCache.remove(key);
                // DATABASE PUSH GuildEntity
            }

            // REPEAT FOR ALL ENTITY TYPES
        }
    }
}
