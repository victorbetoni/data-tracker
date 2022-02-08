package net.victu.datatracker.controller;

import net.victu.datatracker.DataTrackerAPI;
import net.victu.datatracker.api.DataTrackingKey;
import net.victu.datatracker.model.Profile;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public enum SingleProfileController {
    INSTANCE;

    private Map<String, Profile> profiles = new HashMap<>();

    public Profile getProfile(String player) {
        return Optional.ofNullable(profiles.get(player)).orElseGet(() -> {
            List<String> registeredKeys = DataTrackerAPI.instance().getConfig().getStringList("registered_keys");
            Map<String, Object> data = new HashMap<>();
            registeredKeys.forEach(key -> {
                try (PreparedStatement st = DataTrackerAPI.DATABASE_ACESSOR.getConnection().prepareStatement(
                        "INSERT INTO " + key + " VALUES(?,?)")) {
                    DataTrackingKey<?> tracker = SingleDataTrackerController.INSTANCE.getTrackers().get(key);
                    Object defaultValue = Number.class.isAssignableFrom(tracker.getDataType()) ? 0 : "";
                    st.setString(1, player);
                    st.setObject(2, defaultValue);
                    data.put(key, defaultValue);
                    st.executeUpdate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
            Profile profile = new Profile(player, data);
            profiles.put(player, profile);
            return profile;
        });
    }

    public void downloadProfiles() {
        List<String> registeredKeys = DataTrackerAPI.instance().getConfig().getStringList("registered_keys");
        Map<String, Map<String, Object>> data = new HashMap<>();
        registeredKeys.forEach(key -> {
            try (Statement st = DataTrackerAPI.DATABASE_ACESSOR.getConnection().createStatement()) {
                ResultSet set = st.executeQuery("SELECT * FROM " + key);
                while (set.next()) {
                    String holder = set.getString("holder");
                    Object value = set.getObject("value");
                    if (data.containsKey(holder)) {
                        data.get(holder).put(key, value);
                    } else {
                        Map<String, Object> dataMap = new HashMap<>();
                        dataMap.put(key, value);
                        data.put(holder, dataMap);
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        data.forEach((name, profileData) -> profiles.put(name, new Profile(name, profileData)));
    }
}
