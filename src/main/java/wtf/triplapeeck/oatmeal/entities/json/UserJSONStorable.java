package wtf.triplapeeck.oatmeal.entities.json;

import wtf.triplapeeck.oatmeal.Config;
import wtf.triplapeeck.oatmeal.FileRW;
import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.commands.miscellaneous.Remind;
import wtf.triplapeeck.oatmeal.entities.UserData;

import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;

public class UserJSONStorable extends UserData {
    protected transient JSONStorableFactory factory;
    public synchronized void setFactory(JSONStorableFactory factory1) {
        factory=factory1;
        idLong=id.longValue();
    }
    private BigInteger id;
    private transient long idLong;
    protected transient FileRW file = null;
    public synchronized void setFileRW(FileRW file1) {
        file=file1;
    }

    public synchronized void setID(BigInteger id1) {
        id=id1;
    }
    public synchronized String getID()  {
        return id.toString();
    }
    public synchronized long getIDLong() {
        Logger.customLog("CHANNELSTORABLE","IdLONG");
        return idLong;
    }

    private ConcurrentHashMap<String, Remind.Reminder> reminderList = new ConcurrentHashMap<>();
    private boolean isOwner=false;
    private boolean isAdmin=false;
    private boolean currencyPreference=true;
    public synchronized Boolean isCurrencyPreference()  {
        return currencyPreference;
    }
    public synchronized ConcurrentHashMap<String, Remind.Reminder> getReminders() {
        return reminderList;
    }
    public synchronized void setReminders(ConcurrentHashMap<String, Remind.Reminder> reminders) {
        this.reminderList=reminders;
    }
    public synchronized  void setCurrencyPreference(Boolean val) {
        currencyPreference=val;
    }

    @Override
    public void load() {

    }

    public synchronized Boolean isAdmin()  {
        return (isAdmin || isOwner());
    }
    public synchronized Boolean isOwner() {
        return (isOwner || Config.getConfig().owners.contains(idLong));
    }

    public synchronized void setOwner(Boolean is) {
        isOwner=(is || Config.getConfig().owners.contains(idLong));
    }

    public synchronized void setAdmin(Boolean is) {
        isAdmin=(is || Config.getConfig().owners.contains(idLong) || isOwner());
    }


    public void Store() {
        factory.saveStorable(this);
    }
}
