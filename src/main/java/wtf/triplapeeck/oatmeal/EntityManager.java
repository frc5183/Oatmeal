package wtf.triplapeeck.oatmeal;

import net.dv8tion.jda.api.entities.Guild;
import wtf.triplapeeck.oatmeal.entities.GuildEntity;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class EntityManager extends Thread {
    boolean requestToEnd=false;
    private static final ArrayList<String> temp = new ArrayList<>();
    private static final ConcurrentHashMap<String, GuildEntity> guildCache = new ConcurrentHashMap<>();

    public synchronized void requestToEnd() {
        requestToEnd=true;
    }

    public synchronized GuildEntity getGuildEntity(String id) {
        /*
        if guildCache.get(id) == null then guildCache.put(id, DATABASE_REQUEST(GuildEntity.class, id))
        guildCache.get(id).request();
        return guildCache.get(id);



        THE FOLLOWING IS TEMPORARY JUST TO HOLD TILL A DATABASE IS AVAILABLE
         */
        return guildCache.get(id);
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
