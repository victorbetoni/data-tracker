package net.victu.datatracker.model;

import net.victu.datatracker.DataTrackerAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class Profile {
    private String holder;
    private Map<String, Object> stats;

    public Profile(String holder, Map<String, Object> data) {
        this.holder = holder;
        this.stats = data;
    }

    public Map<String, Object> getStats() {
        return stats;
    }

    public Object getStat(String key) {
        return stats.get(key);
    }

    public void updateData(String stat, Object data) {
        stats.remove(stat);
        stats.put(stat, data);
        Bukkit.getScheduler().runTaskAsynchronously(DataTrackerAPI.instance(), () -> {
            Object object = this.getStat(stat);
            String query = String.format("UPDATE %s SET value=? WHERE holder=?", stat, stat);
            try(PreparedStatement st = DataTrackerAPI.DATABASE_ACESSOR.getConnection().prepareStatement(query)) {
                st.setObject(1, object);
                st.setString(2, holder);
                st.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    public String getAffected() {
        return holder;
    }

    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(holder);
    }

    public float getKdr() {
        System.out.println(stats.get("kills"));
        System.out.println(stats.get("deaths"));
        if((int) stats.get("deaths") == 0) {
            return (int) stats.get("kills");
        }
        return (float) ((int) stats.get("kills") / (int) stats.get("deaths"));
    }
}
