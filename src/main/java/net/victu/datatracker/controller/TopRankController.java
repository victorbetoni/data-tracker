package net.victu.datatracker.controller;

import net.victu.datatracker.DataTrackerAPI;
import net.victu.datatracker.util.Tuple;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum TopRankController {
    INSTANCE;

    private Map<String, List<Tuple<String, Number>>> cachedTopRanks = new HashMap<>();

    public Map<String, List<Tuple<String, Number>>> getCachedTopRanks() {
        return cachedTopRanks;
    }

    public void start() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(DataTrackerAPI.instance(), () -> {
            cachedTopRanks.clear();
            List<String> registeredKeys = DataTrackerAPI.instance().getConfig().getStringList("registered_keys");
            registeredKeys.removeIf(str -> !SingleDataTrackerController.INSTANCE.getTrackers().get(str).isRankeable());
            registeredKeys.forEach(key -> cachedTopRanks.put(key, new ArrayList<>()));
            registeredKeys.forEach(key -> {
                String query = String.format("SELECT * FROM %s ORDER BY value DESC", key);
                try (ResultSet rs = DataTrackerAPI.DATABASE_ACESSOR.getConnection().createStatement().executeQuery(query)) {
                    while(rs.next()) {
                        String player = rs.getString("holder");
                        Number number = (Number) rs.getObject("value");
                        cachedTopRanks.get(key).add(new Tuple<>(player, number));
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
        }, 20, 20*60*5);
    }
}
