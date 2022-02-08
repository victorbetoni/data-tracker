package net.victu.datatracker.command;

import net.victu.datatracker.DataTrackerAPI;
import net.victu.datatracker.controller.SingleProfileController;
import net.victu.datatracker.model.Profile;
import net.victu.datatracker.util.message.Message;
import net.threadly.commandexpress.CommandRunner;
import net.threadly.commandexpress.args.CommandContext;
import net.threadly.commandexpress.result.CommandResult;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class ProfileCommand implements CommandRunner {

    @Override
    public CommandResult execute(CommandContext commandContext, String[] strings) {
        Player player = (Player) commandContext.getSender();
        OfflinePlayer target = strings.length < 1
                ? Bukkit.getOfflinePlayer(player.getName())
                : Bukkit.getOfflinePlayer(strings[0]);

        Profile profile = SingleProfileController.INSTANCE.getProfile(target.getName());

        Message.Builder msg = new Message.Builder().fromConfig("profile_lookup", DataTrackerAPI.instance())
                .addVariable("kdr", String.format("%.2f", profile.getKdr()));

        profile.getStats().forEach((key, obj) -> msg.addVariable(key, obj.toString()));

        msg.build().send(player);
        return CommandResult.builder().build();
    }
}
