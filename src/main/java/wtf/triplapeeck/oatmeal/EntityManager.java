package wtf.triplapeeck.oatmeal;

import net.dv8tion.jda.api.entities.Guild;
import wtf.triplapeeck.oatmeal.entities.GuildEntity;
import wtf.triplapeeck.oatmeal.util.DatabaseUtil;


import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class EntityManager extends Thread {
    boolean requestToEnd=false;
    private static final ArrayList<Long> temp = new ArrayList<>();
    private static final ConcurrentHashMap<Long, GuildEntity> guildCache = new ConcurrentHashMap<>();

    public synchronized void requestToEnd() {
        requestToEnd=true;
    }

    public synchronized GuildEntity getGuildEntity(Long id) {
        if (guildCache.get(id) != null) return guildCache.get(id);
        guildCache.put(id, DatabaseUtil.getGuildEntity(id));
        return guildCache.get(id);
    }
    @Override
    public void run() {
        while (true) {
            if (requestToEnd) {
                for (GuildEntity g : guildCache.values()) {
                    if (DatabaseUtil.getGuildEntity(g.getId()) == null) {
                        DatabaseUtil.createGuildEntity(g);
                    } else {
                        DatabaseUtil.updateGuildEntity(g);
                    }
                }

                break;
            }


            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            for (Long key: guildCache.keySet() ) {
                GuildEntity guildEntity = guildCache.get(key);
                /*
                IF NOW >= guildEntity.epoch+30 AND GUILD UNUSED THEN temp.add(key)
                IF GUILD UNUSED AND NO EPOCH THEN guildEntity.epoch=NOW
                IF GUILD USED AND EPOCH then NO EPOCH
                 */

            }
            for (Long key: temp) {
                //GuildEntity = guildCache.get(key)
                guildCache.remove(key);
                // DATABASE PUSH GuildEntity
            }

            // REPEAT FOR ALL ENTITY TYPES
        }
    }
}
