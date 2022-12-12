package wtf.triplapeeck.sinon.backend.storable;




import wtf.triplapeeck.sinon.backend.Logger;

import java.util.Hashtable;

public class StorableManager {

    private static Hashtable<Long, ChannelStorable> channelList= new Hashtable<>();
    private static Hashtable<Long, GuildStorable> guildList= new Hashtable<>();
    private static Hashtable<String, MemberStorable> memberList= new Hashtable<>();
    private static Hashtable<Long, UserStorable> userList = new Hashtable<>();

    private static Hashtable<Long, TriviaStorable> triviaList = new Hashtable<>();


    public static synchronized ChannelStorable getChannel(Long id) {
        ChannelStorable channelStorable = channelList.get(id);
        if (channelStorable==null) {
            channelStorable= new StorableFactory(id).channelStorable();
            channelList.put(id, channelStorable);

        }
        channelStorable.requestAccess();
        return channelStorable;
    }
    public static synchronized void removeChannel(Long id) {
        channelList.remove(id);
    }



    public static synchronized GuildStorable getGuild(Long id) {
        GuildStorable guildStorable = guildList.get(id);
        if (guildStorable==null) {
            guildStorable= new StorableFactory(id).guildStorable();
            guildList.put(id, guildStorable);

        }
        guildStorable.requestAccess();
        return guildStorable;
    }
    public static synchronized void removeGuild(Long id) {
        guildList.remove(id);
    }

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
}
