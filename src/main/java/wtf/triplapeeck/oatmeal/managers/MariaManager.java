package wtf.triplapeeck.oatmeal.managers;

import wtf.triplapeeck.oatmeal.entities.ReminderData;
import wtf.triplapeeck.oatmeal.entities.mariadb.*;
import wtf.triplapeeck.oatmeal.errors.UsedTableException;
import wtf.triplapeeck.oatmeal.util.DatabaseUtil;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

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

    public void saveGuildData(String key, boolean remove) {
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
            DatabaseUtil.updateGuildEntity(guildEntity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void saveUserData(String key, boolean remove) {
        MariaUser userEntity =  (MariaUser) userCache.get(key);
        if (remove) {
            userCache.remove(key);
        }
    }
    public MariaChannel getRawChannelData(String id) {
        MariaChannel mariaChannel;
        try {
            mariaChannel = DatabaseUtil.getChannelEntity(id);
            if (mariaChannel==null) {
                mariaChannel = new MariaChannel(id);
            }
            mariaChannel.load();
        } catch  (SQLException e){
            throw new RuntimeException(e);
        }
        return mariaChannel;
    }
    public void saveChannelData(String key, boolean remove) {
        MariaChannel mariaChannel =  (MariaChannel) channelCache.get(key);
        if (remove) {
            channelCache.remove(key);
        }
        try {
            String step;
            while (true) {
                try {
                    step = gson.toJson(mariaChannel.getTable());
                    break;
                } catch (UsedTableException e) {

                }

            }
            mariaChannel.tableJson=step;
            DatabaseUtil.updateChannelEntity(mariaChannel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public MariaMember getRawMemberData(String id) {
        MariaMember mariaMember;
        try {
            mariaMember = DatabaseUtil.getMemberEntity(id);
            if (mariaMember==null) {
                mariaMember=new MariaMember(id);
            }
            mariaMember.load();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return mariaMember;
    }
    public void saveMemberData(String key, boolean remove) {
        MariaMember mariaMember = (MariaMember) memberCache.get(key);
        if (remove) {
            memberCache.remove(key);
        }
        try {
            DatabaseUtil.updateMemberEntity(mariaMember);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void removeReminderData(Long id) {
        try {
            DatabaseUtil.deleteReminderEntity(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveReminderData(ReminderData reminderData) {
        MariaReminder reminderEntity = (MariaReminder) reminderData;
        try {
            DatabaseUtil.updateReminderEntity(reminderEntity);
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ReminderData createReminder(String text, Long unix, MariaUser user) {
        return new MariaReminder(unix, text, user);
    }

    public void saveReminderData(MariaReminder reminderEntity) {

    }

    @Override
    public List<? extends ReminderData> getAllReminderData() {
        try {
            return DatabaseUtil.getAllReminderEntity();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
