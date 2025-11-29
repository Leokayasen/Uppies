package com.bitwave.Uppies.listeners;

import com.bitwave.Uppies.util.PoseManager;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

public class PoseListener implements Listener {
    private final PoseManager poseManager;

    public PoseListener(PoseManager poseManager) {
        this.poseManager = poseManager;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!poseManager.isPosing(event.getPlayer())) return;
        if (event.getFrom().toVector().equals(event.getTo().toVector())) return;

        poseManager.resetPose(event.getPlayer());
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        if (poseManager.isPosing(event.getPlayer())) {
            poseManager.resetPose(event.getPlayer());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        poseManager.resetPose(event.getPlayer());
    }
}
