package wtf.triplapeeck.oatmeal.managers;

import wtf.triplapeeck.oatmeal.entities.ReminderData;
import wtf.triplapeeck.oatmeal.entities.mariadb.*;
import wtf.triplapeeck.oatmeal.errors.UsedTableException;
import wtf.triplapeeck.oatmeal.util.ORMLiteDatabaseUtil;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class MariaManager extends DataManager {


    public synchronized MariaGuild getRawGuildData(String id)  {
        MariaGuild guildEntity;
        try {
            guildEntity = ORMLiteDatabaseUtil.getGuildEntity(id);
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
            userEntity = ORMLiteDatabaseUtil.getUserEntity(id);
            if (userEntity==null) {
                userEntity = new MariaUser(id);
            }
            userEntity.load();
        } catch  (SQLException e){
            throw new RuntimeException(e);
        }
        return userEntity;
    }

    public synchronized void saveGuildData(String key, boolean remove) {
        MariaGuild guildEntity =(MariaGuild) guildCache.get(key);
        if (remove) {
            guildCache.remove(key);
        }
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
            ORMLiteDatabaseUtil.updateGuildEntity(guildEntity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public synchronized void saveUserData(String key, boolean remove) {
        MariaUser userEntity =  (MariaUser) userCache.get(key);
        if (remove) {
            userCache.remove(key);
        }
    }
    public synchronized MariaChannel getRawChannelData(String id) {
        MariaChannel mariaChannel;
        try {
            mariaChannel = ORMLiteDatabaseUtil.getChannelEntity(id);
            if (mariaChannel==null) {
                mariaChannel = new MariaChannel(id);
            }
            mariaChannel.load();
        } catch  (SQLException e){
            throw new RuntimeException(e);
        }
        return mariaChannel;
    }
    public synchronized void saveChannelData(String key, boolean remove) {
        MariaChannel mariaChannel =  (MariaChannel) channelCache.get(key);
        if (remove) {
            channelCache.remove(key);
        }
        try {
            String step;
            while (true) {
                if (mariaChannel==null) {
                    return;
                }
                try {
                    step = gson.toJson(mariaChannel.getTable());
                    break;
                } catch (UsedTableException e) {

                }

            }
            mariaChannel.tableJson=step;
            ORMLiteDatabaseUtil.updateChannelEntity(mariaChannel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public synchronized MariaMember getRawMemberData(String id) {
        MariaMember mariaMember;
        try {
            mariaMember = ORMLiteDatabaseUtil.getMemberEntity(id);
            if (mariaMember==null) {
                mariaMember=new MariaMember(id);
            }
            mariaMember.load();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return mariaMember;
    }
    public synchronized void saveMemberData(String key, boolean remove) {
        MariaMember mariaMember = (MariaMember) memberCache.get(key);
        if (remove) {
            memberCache.remove(key);
        }
        try {
            ORMLiteDatabaseUtil.updateMemberEntity(mariaMember);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public synchronized void removeReminderData(Long id) {
        try {
            ORMLiteDatabaseUtil.deleteReminderEntity(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public synchronized void removeReminderDatas(Collection<Long> ids) {
        try {
            ORMLiteDatabaseUtil.deleteReminderEntities(ids);
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void saveReminderData(ReminderData reminderData) {
        MariaReminder reminderEntity = (MariaReminder) reminderData;
        try {
            ORMLiteDatabaseUtil.updateReminderEntity(reminderEntity);
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized ReminderData createReminder(String text, Long unix, MariaUser user) {
        return new MariaReminder(unix, text, user);
    }

    @Override
    public synchronized List<? extends ReminderData> getAllReminderData() {
        try {
            return ORMLiteDatabaseUtil.getAllReminderEntity();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
