package wtf.triplapeeck.oatmeal.storable;

import wtf.triplapeeck.oatmeal.FileRW;
import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.errors.ClosedStorableError;

import java.math.BigInteger;

public abstract class Storable {

    protected transient boolean closed=false;

    protected transient int accessCount=0;

    protected transient StorableFactory factory;
    public synchronized void setFactory(StorableFactory factory1) throws ClosedStorableError {
        if (closed) {throw new ClosedStorableError();}
        factory=factory1;
        idLong=id.longValue();
    }
    private BigInteger id;
    private transient long idLong;
    protected transient FileRW file = null;
    public synchronized void setFileRW(FileRW file1) throws ClosedStorableError {
        if (closed) {throw new ClosedStorableError();}
        file=file1;
    }

    public synchronized void setID(BigInteger id1)throws ClosedStorableError {
        if (closed) {throw new ClosedStorableError();}
        id=id1;
    }
    public synchronized BigInteger getID() throws ClosedStorableError {
        if (closed) {throw new ClosedStorableError();}
        return id;
    }
    public synchronized long getIDLong() {
        Logger.customLog("CHANNELSTORABLE","IdLONG");
        return idLong;
    }

    public abstract void Save();
    //public abstract void requestAccess();

    //public abstract void relinquishAccess();

}
