package com.bitwave.Uppies;

import org.bukkit.plugin.java.JavaPlugin;
import com.bitwave.Uppies.commands.*;
import com.bitwave.Uppies.listeners.DismountListener;
import com.bitwave.Uppies.util.SitManager;

public class Main extends JavaPlugin {
    private SitManager sitManager;

    @Override
    public void onEnable() {
        sitManager = new SitManager();

        getCommand("sit").setExecutor(new SitCommand(sitManager));
        getCommand("ride").setExecutor(new RideCommand(sitManager));

        getServer().getPluginManager().registerEvents(new DismountListener(sitManager), this);

        getLogger().info("Uppies is enabled.");
    }

    @Override
    public void onDisable() {
        sitManager.cleanup();
        getLogger().info("Uppies is disabled.");
    }
}
