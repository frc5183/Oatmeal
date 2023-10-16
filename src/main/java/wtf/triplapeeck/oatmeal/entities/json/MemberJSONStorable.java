package wtf.triplapeeck.oatmeal.entities.json;

import wtf.triplapeeck.oatmeal.FileRW;
import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.entities.MemberData;
import wtf.triplapeeck.oatmeal.errors.ClosedStorableError;

import java.math.BigInteger;
@Deprecated
public class MemberJSONStorable extends MemberData {
    public void load() {}
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
    private BigInteger rak= BigInteger.ZERO;
    public synchronized BigInteger getRak() {
        return rak;
    }
    public synchronized void setRak(BigInteger val) {
        rak=val;
    }
    private int messageCount = 0;
    public synchronized Integer getMessageCount() {
        return messageCount;
    }
    public synchronized void setMessageCount(Integer val) {
        messageCount=val;
    }

    public void Store() {
        factory.saveStorable(this);

    }
}
