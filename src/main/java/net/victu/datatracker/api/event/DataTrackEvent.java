package net.victu.datatracker.api.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Map;

public final class DataTrackEvent extends Event {
    private static HandlerList handlerList = new HandlerList();
    private String trackedInfo;
    private String affected;
    private Map<String, Object> context;

    public DataTrackEvent(String trackedInfo, String affected, Map<String, Object> context) {
        this.affected = affected;
        this.trackedInfo = trackedInfo;
        this.context = context;
    }

    public String getTrackedInfo() {
        return trackedInfo;
    }

    public String getAffected() {
        return affected;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
