package wtf.triplapeeck.oatmeal.entities;




import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.entities.json.*;

import java.util.Hashtable;

public class StorableManager {

    private static Hashtable<Long, ChannelJSONStorable> channelList= new Hashtable<>();
    private static Hashtable<String, GuildJSONStorable> guildList= new Hashtable<>();
    private static Hashtable<String, MemberJSONStorable> memberList= new Hashtable<>();
    private static Hashtable<Long, UserJSONStorable> userList = new Hashtable<>();

    private static Hashtable<Long, GenericJSONStorable> genericList = new Hashtable<>();

    public static synchronized ChannelJSONStorable getChannel(Long id) {
        ChannelJSONStorable channelStorable = channelList.get(id);
        if (channelStorable==null) {
            channelStorable= new JSONStorableFactory(id).channelStorable();
            channelList.put(id, channelStorable);

        }
        channelStorable.request();
        return channelStorable;
    }
    public static void removeChannel(Long id) {

        Logger.customLog("CHANNELStorable","Stalled");

        channelList.remove(id);

    }



    public static synchronized GuildJSONStorable getGuild(String id) {
        GuildJSONStorable guildStorable = guildList.get(id);
        if (guildStorable==null) {
            guildStorable= new JSONStorableFactory(id).guildStorable();
            guildList.put(id, guildStorable);

        }
        //TODO: IMPLEMENT ACCESSIBLE ENTITY HERE SOMEWHERE
        //request()
        return guildStorable;
    }
    public static synchronized void removeGuild(String id) {guildList.remove(id);}

    public static synchronized MemberJSONStorable getMember(String id) {
        MemberJSONStorable memberStorable = memberList.get(id);
        if (memberStorable==null) {
            memberStorable= new JSONStorableFactory(id).memberStorable();
            memberList.put(id, memberStorable);

        }
            memberStorable.request();
        return memberStorable;
    }
    public static synchronized void removeMember(String id) {
        memberList.remove(id);
    }

    public static synchronized UserJSONStorable getUser(Long id) {
        UserJSONStorable userStorable = userList.get(id);
        if (userStorable==null) {
            userStorable= new JSONStorableFactory(id).userStorable();
            userList.put(id, userStorable);
        }
        userStorable.request();
        return userStorable;
    }
    public static synchronized void removeUser(Long id) {
        userList.remove(id);
    }


    public static synchronized GenericJSONStorable getGeneric(Long id) {
        GenericJSONStorable genericStorable = genericList.get(id);
        if (genericStorable==null) {
            genericStorable= new JSONStorableFactory(id).genericStorable();
            genericList.put(id, genericStorable);
        }
        genericStorable.request();
        return genericStorable;
    }
    public static synchronized void removeGeneric(Long id) {
        genericList.remove(id);
    }
}
