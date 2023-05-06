package wtf.triplapeeck.oatmeal.entities.mariadb;

import com.google.gson.JsonSyntaxException;
import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.entities.GenericData;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Deprecated
public class MariaGeneric extends GenericData {

    private transient ConcurrentHashMap<String, String> knownUserList = new ConcurrentHashMap<>();
    @Id
    private String id;
    @Column
    private String jsonUsers;
    public synchronized ConcurrentHashMap<String, String> getKnownUserList() {
        return knownUserList;
    }
    public synchronized void setKnownUserList(ConcurrentHashMap<String, String> userList) {
        this.knownUserList=userList;
    }
    public synchronized String getID() {
        return id;
    }
    public MariaGeneric() {}
    public MariaGeneric(String id) {
        this.id=id;
        this.knownUserList=new ConcurrentHashMap<>();
    }
    public void load() {
        try {
            HashMap<String, String> step = Main.dataManager.gson.fromJson(jsonUsers, HashMap.class);
            if (step==null) {
                step = new HashMap<>();
            }
            ConcurrentHashMap<String, String> out = new ConcurrentHashMap<>();
            for (String key : step.keySet()) {
                out.put(key, step.get(key));
            }
            this.setKnownUserList(out);
        } catch (JsonSyntaxException e) {
            this.setKnownUserList(new ConcurrentHashMap<>());
        }
    }
    public void setJsonUsers(String json) {
        jsonUsers=json;
    }
}
