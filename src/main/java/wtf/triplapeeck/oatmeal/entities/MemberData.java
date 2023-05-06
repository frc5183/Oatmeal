package wtf.triplapeeck.oatmeal.entities;

import java.math.BigInteger;

public abstract class MemberData extends AccessibleEntity implements DataID {
    public abstract BigInteger getRak();
    public abstract void setRak(BigInteger rak);
    public abstract Integer getMessageCount();
    public abstract void setMessageCount(Integer count);
    public abstract void load();
}
