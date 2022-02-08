package net.victu.datatracker.controller;

import net.victu.datatracker.DataTrackerAPI;
import net.victu.datatracker.api.DataTrackingKey;
import org.bukkit.Bukkit;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum SingleDataTrackerController {
    INSTANCE;

    private final Map<String, DataTrackingKey<?>> TRACKERS = new HashMap<>();
    private static final Map<Class<?>, String> SQL_TYPE;

    static {
        SQL_TYPE = new HashMap<>();
        SQL_TYPE.put(Integer.class, "INT");
        SQL_TYPE.put(String.class, "VARCHAR(512)");
        SQL_TYPE.put(Double.class, "DOUBLE");
    }

    public void registerTracker(DataTrackingKey<?> tracker) {
        List<String> keys = DataTrackerAPI.instance().getConfig().getStringList("registered_keys");
        if(!keys.contains(tracker.getKey())) {
            keys.add(tracker.getKey());
        }
        DataTrackerAPI.instance().getConfig().set("registered_keys", keys);
        DataTrackerAPI.instance().saveConfig();
        TRACKERS.put(tracker.getKey(), tracker);
        Bukkit.getScheduler().runTaskAsynchronously(DataTrackerAPI.instance(), () -> {
            Class<?> type = tracker.getDataType();
            if(SQL_TYPE.containsKey(type)) {
                try(Statement st = DataTrackerAPI.DATABASE_ACESSOR.getConnection().createStatement()) {
                    String query = String.format("CREATE TABLE IF NOT EXISTS " + tracker.getKey() + "(holder VARCHAR(64) PRIMARY KEY, value %s);", SQL_TYPE.get(type));
                    st.executeUpdate(query);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else {
                throw new IllegalArgumentException("Unknown data tracking type: " + tracker.getDataType().getName());
            }
        });
    }

    public Map<String, DataTrackingKey<?>> getTrackers() {
        return TRACKERS;
    }
}
