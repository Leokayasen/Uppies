package com.bitwave.Uppies;

import org.bukkit.plugin.java.JavaPlugin;
import com.bitwave.Uppies.commands.*;
import com.bitwave.Uppies.listeners.*;
import com.bitwave.Uppies.util.*;

/**
 * Main class for the Uppies plugin.
 * Handles enabling and disabling the plugin, command registration, and event listener setup.
 */
public class Main extends JavaPlugin {
    private SitManager sitManager;
    private CrawlManager crawlManager;
    private LayManager layManager;

    @Override
    public void onEnable() {
        sitManager = new SitManager();
        crawlManager = new CrawlManager();
        layManager = new LayManager();

        getCommand("sit").setExecutor(new SitCommand(sitManager));
        getCommand("ride").setExecutor(new RideCommand(sitManager));
        getCommand("crawl").setExecutor(new CrawlCommand(crawlManager));
        getCommand("lay").setExecutor(new LayCommand(layManager));

        getServer().getPluginManager().registerEvents(new DismountListener(sitManager), this);

        getLogger().info("Uppies is enabled.");
    }

    @Override
    public void onDisable() {
        sitManager.cleanup();
        getLogger().info("Uppies is disabled.");
    }
}
