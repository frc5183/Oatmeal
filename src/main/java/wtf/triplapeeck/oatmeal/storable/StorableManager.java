package wtf.triplapeeck.oatmeal.storable;




import wtf.triplapeeck.oatmeal.Logger;

import java.util.Hashtable;

public class StorableManager {

    private static Hashtable<Long, ChannelStorable> channelList= new Hashtable<>();
    private static Hashtable<String, GuildStorable> guildList= new Hashtable<>();
    private static Hashtable<String, MemberStorable> memberList= new Hashtable<>();
    private static Hashtable<Long, UserStorable> userList = new Hashtable<>();

    private static Hashtable<Long, TriviaStorable> triviaList = new Hashtable<>();
    private static Hashtable<Long, GenericStorable> genericList = new Hashtable<>();

    public static synchronized ChannelStorable getChannel(Long id) {
        ChannelStorable channelStorable = channelList.get(id);
        if (channelStorable==null) {
            channelStorable= new StorableFactory(id).channelStorable();
            channelList.put(id, channelStorable);

        }
        channelStorable.requestAccess();
        return channelStorable;
    }
    public static void removeChannel(Long id) {

        Logger.customLog("CHANNELStorable","Stalled");

        channelList.remove(id);

    }



    public static synchronized GuildStorable getGuild(String id) {
        GuildStorable guildStorable = guildList.get(id);
        if (guildStorable==null) {
            guildStorable= new StorableFactory(id).guildStorable();
            guildList.put(id, guildStorable);

        }
        guildStorable.requestAccess();
        return guildStorable;
    }
    public static synchronized void removeGuild(String id) {guildList.remove(id);}

    public static synchronized MemberStorable getMember(String id) {
        MemberStorable memberStorable = memberList.get(id);
        if (memberStorable==null) {
            memberStorable= new StorableFactory(id).memberStorable();
            memberList.put(id, memberStorable);

        }
            memberStorable.requestAccess();
        return memberStorable;
    }
    public static synchronized void removeMember(String id) {
        memberList.remove(id);
    }

    public static synchronized UserStorable getUser(Long id) {
        UserStorable userStorable = userList.get(id);
        if (userStorable==null) {
            userStorable= new StorableFactory(id).userStorable();
            userList.put(id, userStorable);
        }
        userStorable.requestAccess();
        return userStorable;
    }
    public static synchronized void removeUser(Long id) {
        userList.remove(id);
    }

    public static synchronized TriviaStorable getTrivia(Long id) {
        TriviaStorable triviaStorable = triviaList.get(id);
        if (triviaStorable==null) {
            triviaStorable= new StorableFactory(id).triviaStorable();
            triviaList.put(id, triviaStorable);

        }
        triviaStorable.requestAccess();
        return triviaStorable;
    }

    public static synchronized void removeTrivia(Long id) {
        triviaList.remove(id);
    }
    public static synchronized GenericStorable getGeneric(Long id) {
        GenericStorable genericStorable = genericList.get(id);
        if (genericStorable==null) {
            genericStorable= new StorableFactory(id).genericStorable();
            genericList.put(id, genericStorable);
        }
        genericStorable.requestAccess();
        return genericStorable;
    }
    public static synchronized void removeGeneric(Long id) {
        genericList.remove(id);
    }
}
