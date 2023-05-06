package wtf.triplapeeck.oatmeal.entities;

import java.util.concurrent.ConcurrentHashMap;
@Deprecated
public abstract class GenericData extends AccessibleEntity implements DataID {
    public abstract ConcurrentHashMap<String, String> getKnownUserList();
    public abstract void setKnownUserList(ConcurrentHashMap<String, String> userList);
    public abstract void load();
}
