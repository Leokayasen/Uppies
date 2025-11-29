package com.bitwave.Uppies.listeners;

import com.bitwave.Uppies.util.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

public class PoseListener implements Listener {
    private final CrawlManager crawl;
    private final LayManager lay;

    public PoseListener(CrawlManager crawl, LayManager lay) {
        this.crawl = crawl;
        this.lay = lay;
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        if (crawl.isCrawling(event.getPlayer())) crawl.stop(event.getPlayer());
        if (lay.isLaying(event.getPlayer())) lay.stop(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (crawl.isCrawling(event.getPlayer())) crawl.stop(event.getPlayer());
        if (lay.isLaying(event.getPlayer())) lay.stop(event.getPlayer());
    }
}
