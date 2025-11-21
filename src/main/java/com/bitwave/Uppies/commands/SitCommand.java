package com.bitwave.Uppies.commands;

import com.bitwave.Uppies.util.SitManager;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class SitCommand implements CommandExecutor {
    private final SitManager sitManager;

    public SitCommand(SitManager sitManager) {
        this.sitManager = sitManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) return true;

        if (args.length == 0) {
            sitManager.sitOnGround(player);
            return true;
        }

        if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage("&cPlayer not found.");
                return true;
            }
            sitManager.sitOnPlayer(player, target);
            return true;
        }

        player.sendMessage("&cUsage: /sit [player]");
        return true;
    }
}
