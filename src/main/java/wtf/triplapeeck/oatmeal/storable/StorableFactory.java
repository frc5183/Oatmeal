package wtf.triplapeeck.oatmeal.storable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import wtf.triplapeeck.oatmeal.FileRW;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class StorableFactory {
    private String id;
    protected transient Gson json;
    protected transient GsonBuilder jsonBuilder = new GsonBuilder();
    protected transient String data;
    protected transient FileRW file;
    protected transient String path;
    public StorableFactory(Long id2) {
        try {
        id=id2.toString();
        path  = "D:\\sinondata\\" + id + ".json";
        boolean t = fileExists(path);
        if (!t) {
            new FileOutputStream(path, true).close();
        }
        file = new FileRW(Path.of(path));
        data = file.readAll();
        if (!t || data.length()==0) {
            data="{id:"+id+"}";
        }
        json=jsonBuilder.create();



        } catch (IOException e) {

            System.out.println(e);

        }
    }
    private boolean fileExists(String path) {
        return Files.exists(Path.of(path));
    }

    public StorableFactory(String id2) {
        try {
            id = id2;

            path = "D:\\sinondata\\" + id + ".json";
            boolean t = fileExists(path);
            if (!t) {
                new FileOutputStream(path, true).close();
            }
            file = new FileRW(Path.of(path));
            data = file.readAll();
            if (!t || data.length() == 0) {
                data = "{id:" + id + "}";
            }
            json = jsonBuilder.create();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public UserStorable userStorable() {
        UserStorable us = json.fromJson(data, UserStorable.class);
        us.setFileRW(file);
        us.setFactory(this);
        return us;
    }
    public GuildStorable guildStorable() {
        GuildStorable gs = json.fromJson(data, GuildStorable.class);
        gs.setFileRW(file);
        gs.setFactory(this);
        return gs;
    }

    public ChannelStorable channelStorable() {
        ChannelStorable cs = json.fromJson(data, ChannelStorable.class);
        cs.setFileRW(file);
        cs.setFactory(this);
        return cs;
    }

    public MemberStorable memberStorable() {
        MemberStorable ms = json.fromJson(data, MemberStorable.class);
        ms.setFileRW(file);
        ms.setFactory(this);
        return ms;
    }
    public TriviaStorable triviaStorable() {
        TriviaStorable ts = json.fromJson(data, TriviaStorable.class);
        ts.setFileRW(file);
        ts.setFactory(this);
        return ts;

    }
    public GenericStorable genericStorable() {
        GenericStorable gs = json.fromJson(data, GenericStorable.class);
        gs.setFileRW(file);
        gs.setFactory(this);
        return gs;

    }
    public synchronized void saveStorable(Storable s) {
        try {
        file.writeAll(json.toJson(s));
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
