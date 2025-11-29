package com.bitwave.Uppies.commands;

import com.bitwave.Uppies.util.PoseManager;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class CrawlCommand implements CommandExecutor {
    private final PoseManager poseManager;

    public CrawlCommand(PoseManager poseManager) {
        this.poseManager = poseManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        poseManager.toggleCrawl(player);
        return true;
    }
}
