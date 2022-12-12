package wtf.triplapeeck.sinon.backend.storable;

import wtf.triplapeeck.sinon.backend.FileRW;
import wtf.triplapeeck.sinon.backend.Logger;
import wtf.triplapeeck.sinon.backend.errors.ClosedStorableError;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Storable {

    protected transient boolean closed=false;

    protected transient int accessCount=0;

    protected transient StorableFactory factory;
    public synchronized void setFactory(StorableFactory factory1) throws ClosedStorableError {
        if (closed) {throw new ClosedStorableError();}
        factory=factory1;
    }
    private BigInteger id;
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

    public abstract void Save();
    //public abstract void requestAccess();

    //public abstract void relinquishAccess();

}
