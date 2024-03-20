package wtf.triplapeeck.oatmeal.managers;

import wtf.triplapeeck.oatmeal.cards.Table;
import wtf.triplapeeck.oatmeal.entities.CustomResponseData;
import wtf.triplapeeck.oatmeal.entities.GuildData;
import wtf.triplapeeck.oatmeal.entities.ReminderData;
import wtf.triplapeeck.oatmeal.entities.UserData;
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
            Table table = mariaChannel.loadTable();
            step = gson.toJson(table);
            mariaChannel.releaseTable();
            mariaChannel.tableJson=step;
            ORMLiteDatabaseUtil.updateChannelEntity(mariaChannel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        mariaChannel.release();
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
    public synchronized ReminderData createReminder(String text, Long unix, UserData user) {
        if (user.getClass() == MariaUser.class) {
            return new MariaReminder(unix, text, (MariaUser) user);
        }
        return null; // This case would only ever occur if MariaManager is being used concurrently with another manager, which should never happen
    }

    @Override
    public synchronized List<? extends ReminderData> getAllReminderData() {
        try {
            return ORMLiteDatabaseUtil.getAllReminderEntity();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void removeCustomResponseData(Long id) {
        try {
            ORMLiteDatabaseUtil.deleteCustomResponseEntity(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public synchronized void removeCustomResponseDatas(Collection<Long> ids) {
        try {
            ORMLiteDatabaseUtil.deleteCustomResponseEntities(ids);
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void saveCustomResponseData(CustomResponseData customResponseData) {
        MariaCustomResponse customResponseEntity = (MariaCustomResponse) customResponseData;
        try {
            ORMLiteDatabaseUtil.updateCustomResponseEntity(customResponseEntity);
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized CustomResponseData createCustomResponse(String trigger, String response, GuildData guild) {
        if (guild.getClass()== MariaGuild.class) {
            return new MariaCustomResponse(trigger, response, (MariaGuild) guild);
        }
        return null; // This case would only ever occur if MariaManager is being used concurrently with another manager, which should never happen
    }

    @Override
    public synchronized List<? extends CustomResponseData> getAllCustomResponseData(GuildData data) {
        if (data.getClass() == MariaGuild.class) {
            MariaGuild guild = (MariaGuild) data;
            try {
                return ORMLiteDatabaseUtil.queryAllCustomResponseEntity(guild);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
