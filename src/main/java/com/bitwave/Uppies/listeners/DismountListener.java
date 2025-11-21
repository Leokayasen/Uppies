package com.bitwave.Uppies.listeners;

import com.bitwave.Uppies.util.SitManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.entity.Player;

public class DismountListener implements Listener {
    private final SitManager sitManager;

    public DismountListener(SitManager sitManager) {
        this.sitManager = sitManager;
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (event.isSneaking()) sitManager.dismount(player);
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent event) {
        sitManager.dismount(event.getPlayer());
    }
}
