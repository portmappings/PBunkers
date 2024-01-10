package me.portmapping.pbunkers.waypoints.listener;


import me.portmapping.pbunkers.PBunkers;
import me.portmapping.pbunkers.waypoints.WaypointManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.function.UnaryOperator;

/**
 * Copyright (c) 2023. Keano
 * Use or redistribution of source or file is
 * only permitted if given explicit permission.
 */
public class WaypointListener  {

    private final PBunkers instance;
    private final WaypointManager manager;
    public WaypointListener(WaypointManager manager) {
        this.instance = manager.getInstance();
        this.manager = manager;
        
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (instance.getClientHook().getClients().isEmpty()) return;

        Player player = e.getPlayer();



    }




}