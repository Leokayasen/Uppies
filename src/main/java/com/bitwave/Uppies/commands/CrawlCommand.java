package com.bitwave.Uppies.commands;

import com.bitwave.Uppies.util.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class CrawlCommand implements CommandExecutor {
    private final CrawlManager crawlManager;

    public CrawlCommand(CrawlManager crawlManager) {
        this.crawlManager = crawlManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        crawlManager.toggle(player);
        return true;
    }
}
