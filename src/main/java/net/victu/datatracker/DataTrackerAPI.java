package net.victu.datatracker;

import net.victu.datatracker.command.ProfileCommand;
import net.victu.datatracker.controller.SingleDataTrackerController;
import net.victu.datatracker.controller.SingleProfileController;
import net.victu.datatracker.controller.TopRankController;
import net.victu.datatracker.listener.DataTrackingListener;
import net.victu.datatracker.listener.PlayerDeathListener;
import net.victu.datatracker.listener.PlayerJoinListener;
import net.victu.datatracker.util.db.SQLiteDatabaseAcessor;
import net.threadly.commandexpress.CommandExpress;
import net.threadly.commandexpress.CommandSpec;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataTrackerAPI extends JavaPlugin {

    private static DataTrackerAPI instance;
    public static SQLiteDatabaseAcessor DATABASE_ACESSOR;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        DATABASE_ACESSOR = new SQLiteDatabaseAcessor(this, new File(this.getDataFolder(), "database.db"));
        DATABASE_ACESSOR.connect();
        this.getConfig().set("registered_keys", new ArrayList<String>());
        DataTrackerAPI.instance().saveConfig();

        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new DataTrackingListener(), this);

        CommandSpec profileSpec = CommandSpec.builder().executor(new ProfileCommand()).build();

        CommandExpress.registerCommandEntryPoint(this, profileSpec, "perfil");

        SingleDataTrackerController.INSTANCE.registerTracker(DefaultDataTrackingKeys.DEATH_TRACKING);
        SingleDataTrackerController.INSTANCE.registerTracker(DefaultDataTrackingKeys.KILLS_TRACKING);

        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
            SingleProfileController.INSTANCE.downloadProfiles();
            TopRankController.INSTANCE.start();
        });
    }

    @Override
    public void onDisable() {
        try {
            DATABASE_ACESSOR.getConnection().close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static DataTrackerAPI instance() {
        return instance;
    }
}
