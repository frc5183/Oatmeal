package wtf.triplapeeck.oatmeal.entities.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import wtf.triplapeeck.oatmeal.FileRW;
import wtf.triplapeeck.oatmeal.util.ConfigParser;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
@Deprecated
public class JSONStorableFactory {
    private String id;
    protected transient Gson json;
    protected transient GsonBuilder jsonBuilder = new GsonBuilder();
    protected transient String data;
    protected transient FileRW file;
    protected transient String path;
    public JSONStorableFactory(Long id2) {
        try {
        id=id2.toString();
        path  = ConfigParser.getPath() + id + ".json";
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

    public JSONStorableFactory(String id2) {
        try {
            id = id2;

            path = ConfigParser.getPath() + id + ".json";
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

    public UserJSONStorable userStorable() {
        UserJSONStorable us = json.fromJson(data, UserJSONStorable.class);
        us.setFileRW(file);
        us.setFactory(this);
        return us;
    }
    public GuildJSONStorable guildStorable() {
        GuildJSONStorable gs = json.fromJson(data, GuildJSONStorable.class);
        gs.setFileRW(file);
        gs.setFactory(this);
        return gs;
    }

    public ChannelJSONStorable channelStorable() {
        ChannelJSONStorable cs = json.fromJson(data, ChannelJSONStorable.class);
        cs.setFileRW(file);
        cs.setFactory(this);
        return cs;
    }

    public MemberJSONStorable memberStorable() {
        MemberJSONStorable ms = json.fromJson(data, MemberJSONStorable.class);
        ms.setFileRW(file);
        ms.setFactory(this);
        return ms;
    }
    public GenericJSONStorable genericStorable() {
        GenericJSONStorable gs = json.fromJson(data, GenericJSONStorable.class);
        gs.setFileRW(file);
        gs.setFactory(this);
        return gs;

    }
    public synchronized void saveStorable(Object s) {
        try {
        file.writeAll(json.toJson(s));
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
