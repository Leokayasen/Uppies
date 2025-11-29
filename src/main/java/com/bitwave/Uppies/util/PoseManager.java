package com.bitwave.Uppies.util;

import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import java.util.HashSet;
import java.util.Set;

public class PoseManager {
    private final Set<Player> crawling = new HashSet<>();
    private final Set<Player> laying = new HashSet<>();

    public void toggleCrawl(Player player) {
        if (crawling.contains(player)) {
            resetPose(player);
            player.sendMessage("You are no longer crawling.");
            return;
        }

        laying.remove(player);

        player.setPose(Pose.SWIMMING);
        crawling.add(player);
        player.sendMessage("You are now crawling.");
    }

    public void toggleLay(Player player) {
        if (laying.contains(player)) {
            resetPose(player);
            player.sendMessage("You are no longer laying down.");
            return;
        }

        crawling.remove(player);

        player.setPose(Pose.SLEEPING);
        laying.add(player);
        player.sendMessage("You are now laying down.");
    }
    public boolean isPosing(Player player) {
        return crawling.contains(player) || laying.contains(player);
    }

    public void resetPose(Player player) {
        player.setPose(Pose.STANDING);
        crawling.remove(player);
        laying.remove(player);
    }

    public void cleanup() {
        crawling.forEach(player -> player.setPose(Pose.STANDING));
        laying.forEach(player -> player.setPose(Pose.STANDING));
        crawling.clear();
        laying.clear();
    }
}
