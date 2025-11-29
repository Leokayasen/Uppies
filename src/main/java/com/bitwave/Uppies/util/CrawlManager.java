package com.bitwave.Uppies.util;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;

import java.util.HashMap;
import java.util.Map;

public class CrawlManager {
    private final Map<Player, ArmorStand> crawling = new HashMap<>();

    public void toggle(Player player) {
        if (crawling.containsKey(player)) {
            stop(player);
            player.sendMessage("You are no longer crawling.");
            return;
        }

        start(player);
        player.sendMessage("You are now crawling.");
    }

    private void start(Player player) {
        ArmorStand hitbox = player.getWorld().spawn(player.getLocation(), ArmorStand.class, a -> {
            a.setMarker(true);
            a.setInvisible(true);
            a.setSilent(true);
            a.setGravity(false);
            a.setInvulnerable(true);
        });

        crawling.put(player, hitbox);

        player.setSwimming(true);
        player.setPose(Pose.SWIMMING);
    }

    public void tick() {
        for (Player player : crawling.keySet()) {
            ArmorStand hitbox = crawling.get(player);
            Location loc = player.getLocation().clone().add(0,0.95,0);
            hitbox.teleport(loc);

            player.setSwimming(true);
            player.setPose(Pose.SWIMMING);
        }
    }

    public void stop(Player player) {
        ArmorStand hitbox = crawling.remove(player);
        if (hitbox != null) hitbox.remove();
        player.setSwimming(false);
        player.setPose(Pose.STANDING);
    }

    public boolean isCrawling(Player player) {
        return crawling.containsKey(player);
    }

    public void cleanup() {
        crawling.values().forEach(ArmorStand::remove);
        crawling.clear();
    }
}
