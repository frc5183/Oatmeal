package wtf.triplapeeck.oatmeal.entities.json;

import wtf.triplapeeck.oatmeal.FileRW;
import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.entities.GenericData;

import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;
@Deprecated
public class GenericJSONStorable extends GenericData {
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
    private ConcurrentHashMap<String, String> knownUserList = new ConcurrentHashMap<>();

    public synchronized ConcurrentHashMap<String, String> getKnownUserList() {
        return knownUserList;
    }
    public synchronized void setKnownUserList(ConcurrentHashMap<String, String> userList) {
        this.knownUserList=userList;
    }
    public void Store() {
        factory.saveStorable(this);

    }
    public void load() {}

}
