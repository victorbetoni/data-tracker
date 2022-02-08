package net.victu.datatracker.listener;

import net.victu.datatracker.api.event.DataTrackEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Bukkit.getPluginManager().callEvent(new DataTrackEvent("deaths", event.getEntity().getName(), new HashMap<>()));
        if(event.getEntity().getKiller() == null) {
            return;
        }
        Bukkit.getPluginManager().callEvent(new DataTrackEvent("kills", event.getEntity().getName(), new HashMap<>()));
    }
}
