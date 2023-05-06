package wtf.triplapeeck.oatmeal.managers;

import wtf.triplapeeck.oatmeal.entities.GuildData;
import wtf.triplapeeck.oatmeal.entities.UserData;
import wtf.triplapeeck.oatmeal.entities.json.*;

public class JSONManager extends DataManager {

    public synchronized GuildJSONStorable getRawGuildData(String id) {
        GuildJSONStorable guildData;
        guildData =  new JSONStorableFactory(id).guildStorable();
        guildData.load();
        return guildData;

    }

    protected void saveGuildData(String id) {
        GuildJSONStorable guildData;
        guildData = (GuildJSONStorable) guildCache.get(id);
        guildCache.remove(id);
        guildData.Store();
    }

    public UserJSONStorable getRawUserData(String id) {
        UserJSONStorable userData;
        userData = new JSONStorableFactory(id).userStorable();
        userData.load();
        return userData;
    }

    protected void saveUserData(String id) {
        UserJSONStorable userData;
        userData = (UserJSONStorable) userCache.get(id);
        userCache.remove(id);
        userData.Store();
    }
    public ChannelJSONStorable getRawChannelData(String id) {
        ChannelJSONStorable channelData;
        channelData = new JSONStorableFactory(id).channelStorable();
        channelData.load();
        return channelData;
    }
    protected void saveChannelData(String id) {
        ChannelJSONStorable channelData;
        channelData = (ChannelJSONStorable) channelCache.get(id);
        channelCache.remove(id);
        channelData.Store();
    }
    public MemberJSONStorable getRawMemberData(String id) {
        MemberJSONStorable memberData;
        memberData = new JSONStorableFactory(id).memberStorable();
        memberData.load();
        return memberData;
    }
    protected void saveMemberData(String id) {
        MemberJSONStorable memberData;
        memberData = (MemberJSONStorable) memberCache.get(id);
        memberCache.remove(id);
        memberData.Store();
    }
    public GenericJSONStorable getRawGenericData(String id) {
        GenericJSONStorable genericData;
        genericData = new JSONStorableFactory(id).genericStorable();
        genericData.load();
        return genericData;
    }
    protected void saveGenericData(String id) {
        GenericJSONStorable genericData;
        genericData = (GenericJSONStorable) genericCache.get(id);
        genericCache.remove(id);
        genericData.Store();
    }
}
