package me.portmapping.pbunkers.clients.type;

import com.cheatbreaker.api.CheatBreakerAPI;
import com.cheatbreaker.api.object.CBWaypoint;
import me.portmapping.pbunkers.clients.Client;
import me.portmapping.pbunkers.clients.ClientHook;
import me.portmapping.pbunkers.waypoints.Waypoint;
import me.portmapping.pbunkers.waypoints.WaypointType;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.List;
import java.util.function.UnaryOperator;

/**
 * Copyright (c) 2023. Keano
 * Use or redistribution of source or file is
 * only permitted if given explicit permission.
 */
public class CheatBreakerClient implements Client {

    public CheatBreakerClient(ClientHook manager) {

    }

    @Override
    public void overrideNametags(Player target, Player viewer, List<String> tag) {
        CheatBreakerAPI.getInstance().overrideNametag(target, tag, viewer);
    }

    @Override
    public void sendWaypoint(Player player, Location location, Waypoint waypoint, UnaryOperator<String> replacer) {
        CheatBreakerAPI.getInstance().sendWaypoint(player, new CBWaypoint(
                replacer.apply(waypoint.getName()),
                (waypoint.getWaypointType() == WaypointType.KOTH ? location.subtract(0, 1, 0) : location),
                Color.decode(replacer.apply(waypoint.getColor())).getRGB(),
                true,
                true
        ));
    }

    @Override
    public void removeWaypoint(Player player, Location location, Waypoint waypoint, UnaryOperator<String> replacer) {
        CheatBreakerAPI.getInstance().removeWaypoint(player, new CBWaypoint(
                replacer.apply(waypoint.getName()),
                (waypoint.getWaypointType() == WaypointType.KOTH  ? location.subtract(0, 1, 0) : location),
                Color.decode(replacer.apply(waypoint.getColor())).getRGB(),
                true,
                true
        ));
    }

    @Override
    public void handleJoin(Player player) {
        /* No server rule for cheat breaker if (getLunarConfig().getBoolean("LUNAR_API.FIX_1_8_HIT_DELAY"))          */
    }



    @Override
    public void clearTeamViewer(Player player) {

    }

    @Override
    public void giveStaffModules(Player player) {
        CheatBreakerAPI.getInstance().giveAllStaffModules(player);
    }

    @Override
    public void disableStaffModules(Player player) {
        CheatBreakerAPI.getInstance().disableAllStaffModules(player);
    }
}