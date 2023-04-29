package wtf.triplapeeck.oatmeal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import wtf.triplapeeck.oatmeal.entities.GuildEntity;
import wtf.triplapeeck.oatmeal.entities.UserEntity;

import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class EntityManager extends Thread {
    private GsonBuilder gsonBuilder = new GsonBuilder();
    private Gson gson= gsonBuilder.create();
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
                try {
                    HashMap<String, String> step = gson.fromJson(guildEntity.getJsonCustomCommands(), HashMap.class);
                    if (step==null) {
                        step=new HashMap<>();
                    }
                    ConcurrentHashMap<String, String> out = new ConcurrentHashMap<>();
                    for (String key: step.keySet()) {
                        out.put(key, step.get(key));
                    }
                    guildEntity.setCustomCommands(out);
                    } catch (JsonSyntaxException e) {
                    guildEntity.setCustomCommands(new ConcurrentHashMap<>());
                }
                try {
                    HashMap<String, String> step = gson.fromJson(guildEntity.getJsonStarboardLink(), HashMap.class);
                    if (step==null) {
                        step=new HashMap<>();
                    }
                    ConcurrentHashMap<String, String> out = new ConcurrentHashMap<>();
                    for (String key: step.keySet()) {
                        out.put(key, step.get(key));
                    }
                    guildEntity.setStarboardLink(out);
                } catch (JsonSyntaxException e) {
                    guildEntity.setStarboardLink(new ConcurrentHashMap<>());
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
                    userEntity = new UserEntity(id);
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
                while (Main.threadManager.getState()!=State.TERMINATED) {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                for (String key: guildCache.keySet() ) {
                    temp.add(key);

                }
                for (String key : temp) {
                    GuildEntity guildEntity = guildCache.get(key);
                    try {
                        Main.dbUtil.updateGuildEntity(guildEntity);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                temp.clear();
                break;
            }


            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (String key: guildCache.keySet() ) {
                GuildEntity guildEntity = guildCache.get(key);
                if (guildEntity.getEpoch()!=0 && guildEntity.getAccessCount()==0) {

                    if (Instant.now().getEpochSecond() > 30 + guildEntity.getEpoch()) {
                        temp.add(key);
                    }
                } else {
                    guildEntity.resetEpoch();
                }
            }
            for (String key: temp) {
                GuildEntity guildEntity = guildCache.get(key);
                if (guildEntity.getAccessCount()==0) {
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
                        Main.dbUtil.updateGuildEntity(guildEntity);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            temp.clear();
            // REPEAT FOR ALL ENTITY TYPES
        }
    }
}
