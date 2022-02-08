package net.victu.datatracker.util.db;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class SQLiteDatabaseAcessor {
    private Connection connection;
    private Plugin plugin;
    private File file;

    public SQLiteDatabaseAcessor(Plugin plugin, File file) {
        this.plugin = plugin;
        this.file = file;
    }

    public void connect() {
        try {
            plugin.getLogger().log(Level.INFO, "Estabelecendo conexão com o banco...");
            connection = DriverManager.getConnection("jdbc:sqlite:" + file);
            plugin.getLogger().log(Level.INFO, "Conexão com o banco estabelecida!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            plugin.getLogger().log(Level.SEVERE, "Erro durante conexão com o banco, desativando plugin.");
            plugin.getPluginLoader().disablePlugin(plugin);
        }
    }

    public Connection getConnection() {
        if(this.connection == null) {
            connect();
        }
        return connection;
    }
}
