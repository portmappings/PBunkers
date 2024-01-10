package me.portmapping.pbunkers.clients.type;

import com.lunarclient.bukkitapi.LunarClientAPI;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketTeammates;
import com.lunarclient.bukkitapi.nethandler.client.obj.ServerRule;
import com.lunarclient.bukkitapi.object.LCWaypoint;
import com.lunarclient.bukkitapi.serverrule.LunarClientAPIServerRule;
import me.portmapping.pbunkers.clients.Client;
import me.portmapping.pbunkers.clients.ClientHook;
import me.portmapping.pbunkers.waypoints.Waypoint;
import me.portmapping.pbunkers.waypoints.WaypointType;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.function.UnaryOperator;

/**
 * Copyright (c) 2023. Keano
 * Use or redistribution of source or file is
 * only permitted if given explicit permission.
 */
public class LunarClient implements Client {

    public LunarClient(ClientHook manager) {

    }

    @Override
    public void overrideNametags(Player target, Player viewer, List<String> tag) {
        LunarClientAPI.getInstance().overrideNametag(target, tag, viewer);
    }

    @Override
    public void sendWaypoint(Player player, Location location, Waypoint waypoint, UnaryOperator<String> replacer) {
        LunarClientAPI.getInstance().sendWaypoint(player, new LCWaypoint(
                replacer.apply(waypoint.getName()),
                (waypoint.getWaypointType() == WaypointType.KOTH  ? location.subtract(0, 1, 0) : location),
                Color.decode(replacer.apply(waypoint.getColor())).getRGB(),
                true,
                true
        ));
    }

    @Override
    public void removeWaypoint(Player player, Location location, Waypoint waypoint, UnaryOperator<String> replacer) {
        LunarClientAPI.getInstance().removeWaypoint(player, new LCWaypoint(
                replacer.apply(waypoint.getName()),
                (waypoint.getWaypointType() == WaypointType.KOTH  ? location.subtract(0, 1, 0) : location),
                Color.decode(replacer.apply(waypoint.getColor())).getRGB(),
                true,
                true
        ));
    }

    @Override
    public void handleJoin(Player player) {

            LunarClientAPIServerRule.setRule(ServerRule.LEGACY_COMBAT, true);
            LunarClientAPIServerRule.sendServerRule(player);

    }


    @Override
    public void clearTeamViewer(Player player) {

        LCPacketTeammates packet = new LCPacketTeammates(player.getUniqueId(), 1L, new HashMap<>());
        LunarClientAPI.getInstance().sendTeammates(player, packet);
    }

    @Override
    public void giveStaffModules(Player player) {
        LunarClientAPI.getInstance().giveAllStaffModules(player);
    }

    @Override
    public void disableStaffModules(Player player) {
        LunarClientAPI.getInstance().disableAllStaffModules(player);
    }
}