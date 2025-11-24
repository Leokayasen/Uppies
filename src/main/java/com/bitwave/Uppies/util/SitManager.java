package com.bitwave.Uppies.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class SitManager {
    private final Map<Player, ArmorStand> sitting = new HashMap<>();

    public void sitOnGround(Player player) {
        if (isSitting(player)) {
            player.sendMessage("You are already sitting.");
            return;
        }

        Location location = player.getLocation().clone();
        location.setY(location.getY() - 0.1);

        ArmorStand armorStand = player.getWorld().spawn(location, ArmorStand.class, stand -> {
            stand.setVisible(false);
            stand.setGravity(false);
            stand.setSmall(true);
            stand.setMarker(true);
        });

        armorStand.addPassenger(player);
        sitting.put(player, armorStand);

        player.sendMessage("You are now sitting.");
    }

    public void sitOnPlayer(Player sitter, Player target) {
        if (sitter.equals(target)) {
            sitter.sendMessage("You cannot sit on yourself.");
            return;
        }

        if (!target.getPassengers().isEmpty()) {
            sitter.sendMessage("That player already has someone riding them.");
            return;
        }

        target.addPassenger(sitter);
        sitter.sendMessage("You are now sitting on " + target.getName() + ".");
        target.sendMessage(sitter.getName() + " is now riding you.");
    }

    public boolean isSitting(Player player) {
        return sitting.containsKey(player);
    }

    public void dismount(Player player) {
        player.leaveVehicle();

        if (sitting.containsKey(player)) {
            ArmorStand stand = sitting.remove(player);
            if (stand != null && !stand.isDead()) stand.remove();
        }
    }

    public void cleanup() {
        for (ArmorStand stand : sitting.values()) {
            if (stand != null && !stand.isDead()) stand.remove();
        }
        sitting.clear();

        for (Player player : Bukkit.getOnlinePlayers()) player.leaveVehicle();
    }
}
