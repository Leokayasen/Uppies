package com.bitwave.Uppies.util;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;

import java.util.HashMap;
import java.util.Map;

public class LayManager {
    private final Map<Player, ArmorStand> laying = new HashMap<>();

    public void toggle(Player player) {
        if (laying.containsKey(player)) {
            stop(player);
            player.sendMessage("You are no longer laying down.");
            return;
        }

        start(player);
        player.sendMessage("You are now laying down.");
    }

    private void start(Player player) {
        Location loc = player.getLocation().clone();
        loc.setPitch(0);
        loc.setYaw(player.getLocation().getYaw());

        ArmorStand bed = player.getWorld().spawn(loc, ArmorStand.class, a -> {
            a.setMarker(false);
            a.setInvisible(true);
            a.setSilent(true);
            a.setGravity(false);
            a.setInvulnerable(true);
            a.setRotation(player.getLocation().getYaw(), 0);
        });

        bed.addPassenger(player);
        laying.put(player, bed);

        player.setPose(Pose.SLEEPING);
    }

    public void stop(Player player) {
        ArmorStand bed = laying.remove(player);
        if (bed != null) bed.remove();
        player.setPose(Pose.STANDING);
    }

    public boolean isLaying(Player player) {
        return laying.containsKey(player);
    }

    public void cleanup() {
        laying.values().forEach(ArmorStand::remove);
        laying.clear();
    }
}
