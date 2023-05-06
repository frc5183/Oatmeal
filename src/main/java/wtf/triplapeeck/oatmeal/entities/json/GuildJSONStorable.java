package wtf.triplapeeck.oatmeal.entities.json;

import wtf.triplapeeck.oatmeal.FileRW;
import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.entities.GuildData;

import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;


public class GuildJSONStorable extends GuildData {
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
    private ConcurrentHashMap<String, String> customCommandList = new ConcurrentHashMap<>();

    private ConcurrentHashMap<String, String> starboardLink = new ConcurrentHashMap<>();


    private String starboardChannelID;

    private int starboardLimit=2;
    public synchronized void setStarboardChannelID(String id) {
        starboardChannelID=id;
    }
    public synchronized Integer getStarboardLimit() {
        return starboardLimit;
    }
    public synchronized void setStarboardLimit(Integer val) {
        starboardLimit=val;
    }
    public synchronized String getStarboardChannelID() {
        return starboardChannelID;
    }
    public synchronized ConcurrentHashMap<String, String> getCustomCommands() {
        return customCommandList;
    }

    public synchronized ConcurrentHashMap<String, String> getStarboardLink() {return starboardLink;}
    public synchronized void setCustomCommands(ConcurrentHashMap<String, String> commandList) {
        this.customCommandList=commandList;
    }
    public synchronized void setStarboardLink(ConcurrentHashMap<String, String> link) {
        this.starboardLink=link;
    }

    private boolean currencyEnabled=true;
    public synchronized Boolean isCurrencyEnabled()  {
        return currencyEnabled;
    }
    public synchronized void setCurrencyEnabled(Boolean val)  {
        currencyEnabled=val;
    }
    private boolean testingEnabled=false;

    public synchronized Boolean isTestingEnabled(){
        return testingEnabled;
    }
    public synchronized void setTestingEnabled(Boolean val)  {
        testingEnabled=val;
    }


    public void load() {

    }


    public void Store() {
        factory.saveStorable(this);

    }

}
