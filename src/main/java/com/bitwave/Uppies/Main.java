package com.bitwave.Uppies;

import org.bukkit.plugin.java.JavaPlugin;
import com.bitwave.Uppies.commands.*;
import com.bitwave.Uppies.listeners.*;
import com.bitwave.Uppies.util.*;

public class Main extends JavaPlugin {
    private SitManager sitManager;
    private PoseManager poseManager;

    @Override
    public void onEnable() {
        sitManager = new SitManager();
        poseManager = new PoseManager();

        getCommand("sit").setExecutor(new SitCommand(sitManager));
        getCommand("ride").setExecutor(new RideCommand(sitManager));
        getCommand("crawl").setExecutor(new CrawlCommand(poseManager));
        getCommand("lay").setExecutor(new LayCommand(poseManager));

        getServer().getPluginManager().registerEvents(new DismountListener(sitManager), this);
        getServer().getPluginManager().registerEvents(new PoseListener(poseManager), this);

        getLogger().info("Uppies is enabled.");
    }

    @Override
    public void onDisable() {
        sitManager.cleanup();
        getLogger().info("Uppies is disabled.");
    }
}
