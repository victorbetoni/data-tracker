package net.victu.datatracker.listener;

import net.victu.datatracker.controller.SingleProfileController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        SingleProfileController.INSTANCE.getProfile(event.getPlayer().getName());
    }
}
