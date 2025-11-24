package com.bitwave.Uppies.commands;

import com.bitwave.Uppies.util.SitManager;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class RideCommand implements CommandExecutor {
    private final SitManager sitManager;

    public RideCommand(SitManager sitManager) {
        this.sitManager = sitManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) return true;

        if (args.length != 1) {
            player.sendMessage("Usage: /ride <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage("Player not found.");
            return true;
        }

        sitManager.sitOnPlayer(player, target);
        return true;
    }
}
