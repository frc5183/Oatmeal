package wtf.triplapeeck.oatmeal.entities;

import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ConcurrentHashMap;

public abstract class GuildData extends AccessibleEntity implements DataID {
    @Nullable
    public abstract ConcurrentHashMap<String, String> getCustomCommands();
    public abstract void  setCustomCommands(@Nullable ConcurrentHashMap<String, String> customCommands);
    @Nullable public abstract
    ConcurrentHashMap<String, String> getStarboardLink();
    public abstract void setStarboardLink(@Nullable ConcurrentHashMap<String, String> starboardLink);
    @Nullable
    public abstract String getStarboardChannelID();
    public abstract void setStarboardChannelID(@Nullable String starboardChannelID);
    @Nullable
    public abstract Integer getStarboardLimit();
    public abstract void setStarboardLimit(@Nullable Integer starboardLimit);
    @Nullable
    public abstract Boolean isCurrencyEnabled();
    public abstract void setCurrencyEnabled(@Nullable Boolean currencyEnabled);
    @Nullable
    public abstract Boolean isTestingEnabled();
    public abstract void setTestingEnabled(@Nullable Boolean testingEnabled);
    public abstract void load();

}
