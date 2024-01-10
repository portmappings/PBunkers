package me.portmapping.pbunkers.clients;


import me.portmapping.pbunkers.waypoints.Waypoint;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.UnaryOperator;

/**
 * Copyright (c) 2023. Keano
 * Use or redistribution of source or file is
 * only permitted if given explicit permission.
 */
public interface Client {

    void overrideNametags(Player target, Player viewer, List<String> tag);

    void sendWaypoint(Player player, Location location, Waypoint waypoint, UnaryOperator<String> replacer);

    void removeWaypoint(Player player, Location location, Waypoint waypoint, UnaryOperator<String> replacer);

    void handleJoin(Player player);


    void clearTeamViewer(Player player);

    void giveStaffModules(Player player);

    void disableStaffModules(Player player);
}