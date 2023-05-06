package wtf.triplapeeck.oatmeal.entities;




import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.entities.json.*;

import java.util.Hashtable;
@Deprecated

public class StorableManager {

    private static Hashtable<Long, ChannelJSONStorable> channelList= new Hashtable<>();
    private static Hashtable<String, GuildJSONStorable> guildList= new Hashtable<>();
    private static Hashtable<String, MemberJSONStorable> memberList= new Hashtable<>();
    private static Hashtable<Long, UserJSONStorable> userList = new Hashtable<>();

    private static Hashtable<Long, GenericJSONStorable> genericList = new Hashtable<>();






    public static synchronized MemberJSONStorable getMember(String id) {
        MemberJSONStorable memberStorable = memberList.get(id);
        if (memberStorable==null) {
            memberStorable= new JSONStorableFactory(id).memberStorable();
            memberList.put(id, memberStorable);

        }
            memberStorable.request();
        return memberStorable;
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
