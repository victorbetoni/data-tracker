package net.victu.datatracker.util.message;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class Message {
    private List<String> lines;
    private Map<String, Object> variables;

    public Message(List<String> lines, Map<String, Object> variables) {
        this.lines = lines;
        this.variables = variables;
    }

    public List<String> getLines() {
        return lines;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void send(CommandSender player) {
        for (String str : lines) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', replaceVariables(str)));
        }
    }

    public String replaceVariables(final String line) {
        final AtomicReference<String> currentLine = new AtomicReference<>(line);
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            if (line.contains("{" + entry.getKey() + "}")) {
                currentLine.set(currentLine.get().replace("{" + entry.getKey() + "}", entry.getValue().toString()));
            }
        }
        return currentLine.get();
    }

public static class Util {
    public static String of(String path, Plugin plugin) {
        FileConfiguration config = plugin.getConfig();
        return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("message." + path)));
    }

    public static List<String> ofLines(String path, Plugin plugin) {
        FileConfiguration config = plugin.getConfig();
        return config.getStringList("message." + path);
    }
}

public static class Builder {
    List<String> lines = new ArrayList<>();
    private Map<String, Object> variables = new HashMap<>();

    public Builder addLine(String line) {
        lines.add(line);
        return this;
    }

    public Builder addLines(String... lines) {
        this.lines.addAll(Arrays.asList(lines));
        return this;
    }

    public Builder addLines(List<String> lines) {
        this.lines.addAll(lines);
        return this;
    }

    public Builder fromConfig(String path, Plugin plugin) {
        FileConfiguration config = plugin.getConfig();
        path = "message." + path;
        if (config.get(path) instanceof List) {
            addLines(config.getStringList(path));
        } else {
            addLine(config.getString(path));
        }
        return this;
    }

    public Builder addVariable(String var, Object value) {
        variables.put(var, value);
        return this;
    }

    public Message build() {
        return new Message(lines, variables);
    }
}
}
