package wtf.triplapeeck.oatmeal.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.entities.*;
import wtf.triplapeeck.oatmeal.entities.mariadb.MariaGuild;
import wtf.triplapeeck.oatmeal.entities.mariadb.MariaReminder;
import wtf.triplapeeck.oatmeal.entities.mariadb.MariaUser;


import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public abstract class DataManager extends Thread {
    boolean requestToEnd = false;
    protected static final ArrayList<String> temp = new ArrayList<>();
    protected static final ConcurrentHashMap<String, GuildData> guildCache = new ConcurrentHashMap<>();
    protected static final ConcurrentHashMap<String, UserData> userCache = new ConcurrentHashMap<>();
    protected static final ConcurrentHashMap<String, ChannelData> channelCache = new ConcurrentHashMap<>();
    protected static final ConcurrentHashMap<String, MemberData> memberCache = new ConcurrentHashMap<>();
    protected final GsonBuilder gsonBuilder = new GsonBuilder();
    public synchronized void requestToEnd() {
        requestToEnd=true;
    }
    public final Gson gson = gsonBuilder.create();

    protected abstract GuildData getRawGuildData(String id);
    protected abstract void saveGuildData(String id, boolean remove);
    protected abstract UserData getRawUserData(String id);
    protected abstract void saveUserData(String id, boolean remove);
    protected abstract ChannelData getRawChannelData(String id);
    protected abstract void saveChannelData(String id, boolean remove);
    protected abstract MemberData getRawMemberData(String id);
    protected abstract void saveMemberData(String id, boolean remove);

    public abstract void removeReminderData(Long id);
    public abstract void removeReminderDatas(Collection<Long> ids);
    public abstract void saveReminderData(ReminderData reminderData);
    public abstract ReminderData createReminder(String text, Long unix, UserData user);
    public abstract List<? extends ReminderData> getAllReminderData();

    public abstract void removeCustomResponseData(Long id);
    public abstract void removeCustomResponseDatas(Collection<Long> ids);
    public abstract void saveCustomResponseData(CustomResponseData data);
    public abstract CustomResponseData createCustomResponse(String trigger, String response, GuildData guild);
    public abstract List<? extends CustomResponseData> getAllCustomResponseData(GuildData data);

    public synchronized GuildData getGuildData(String id) {
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
    public synchronized UserData getUserData(String id) {
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
    public synchronized ChannelData getChannelData(String id) {
        ChannelData channelData;
        if (channelCache.get(id)==null) {
            channelData = getRawChannelData(id);
            channelCache.put(id, channelData);
        } else {
            channelData=channelCache.get(id);
        }
        channelData.request();
        return channelData;
    }
    public synchronized MemberData getMemberData(String id) {
        MemberData memberData;
        if (memberCache.get(id)==null) {
            memberData = getRawMemberData(id);
            memberCache.put(id, memberData);
        } else {
            memberData=memberCache.get(id);
        }
        memberData.request();
        return memberData;
    }
    public synchronized void saveAll() {
        temp.addAll(guildCache.keySet());
        for (String key: temp) {
            saveGuildData(key, false);
        }
        temp.clear();
        temp.addAll(userCache.keySet());
        for (String key: temp) {
            saveUserData(key, false);
        }
        temp.clear();
        temp.addAll(channelCache.keySet());
        for (String key: temp) {
            saveChannelData(key, false);
        }
        temp.clear();
        temp.addAll(memberCache.keySet());
        for (String key: temp) {
            saveMemberData(key, false);
        }
        temp.clear();
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
                    saveGuildData(key, true);
                }
                temp.clear();
                temp.addAll(userCache.keySet());
                for (String key: temp) {
                    saveUserData(key, true);
                }
                temp.clear();
                temp.addAll(channelCache.keySet());
                for (String key: temp) {
                    saveChannelData(key, true);
                }
                temp.clear();
                temp.addAll(memberCache.keySet());
                for (String key: temp) {
                    saveMemberData(key, true);
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
                    if (!guildData.getSaved() ) {
                        saveGuildData(key, false);
                        guildData.saved();
                    }
                    if (Instant.now().getEpochSecond() > 30 + guildData.getEpoch()) {
                        temp.add(key);
                    }
                } else {
                    guildData.resetEpoch();
                }
            }
            for (String key: temp) {
                GuildData guildData = guildCache.get(key);
                if (guildData!=null && guildData.getAccessCount()==0) {
                        saveGuildData(key, true);
                }
            }
            temp.clear();
            for (String key: userCache.keySet()) {
                UserData userData = userCache.get(key);
                if (userData.getEpoch()!=0 && userData.getAccessCount()==0) {
                    if (!userData.getSaved()) {
                        saveUserData(key, false);
                        userData.saved();
                    }
                    if (Instant.now().getEpochSecond()>30 + userData.getEpoch()) {
                        temp.add(key);
                    }
                } else {
                    userData.resetEpoch();
                }
            }
            for (String key: temp) {
                UserData userData = userCache.get(key);
                if (userData!=null && (userData.getAccessCount() == 0)) {
                        saveUserData(key, true);
                }
            }
            temp.clear();
            for (String key: channelCache.keySet()) {
                ChannelData channelData = channelCache.get(key);
                if (channelData.getEpoch()!=0 && channelData.getAccessCount()==0) {
                    if (!channelData.getSaved()) {
                        saveChannelData(key, false);
                        channelData.saved();
                    }
                    if (Instant.now().getEpochSecond()>30 + channelData.getEpoch()) {
                        temp.add(key);
                    }
                }
            }
            for (String key: temp) {
                ChannelData channelData = channelCache.get(key);
                if (channelData!=null && channelData.getAccessCount() == 0) {
                        saveChannelData(key, true);
                }
            }
            temp.clear();
            for (String key: memberCache.keySet()) {
                MemberData memberData = memberCache.get(key);
                if (memberData.getEpoch()!=0 && memberData.getAccessCount()==0) {
                    if (!memberData.getSaved()) {
                        saveMemberData(key, false);
                        memberData.saved();
                    }
                    if (Instant.now().getEpochSecond()>30 + memberData.getEpoch()) {
                        temp.add(key);
                    }
                }
            }
            for (String key: temp) {
                MemberData memberData = memberCache.get(key);
                if (memberData!=null && memberData.getAccessCount() == 0) {
                        saveMemberData(key, true);
                }
            }
            temp.clear();
            //TODO: REPEAT FOR ALL ENTITY TYPES
        }
    }
}
