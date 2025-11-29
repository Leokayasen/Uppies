package com.bitwave.Uppies.commands;

import com.bitwave.Uppies.util.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class LayCommand implements CommandExecutor {
    private final LayManager layManager;

    public LayCommand(LayManager layManager) {
        this.layManager = layManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        layManager.toggle(player);
        return true;
    }
}
