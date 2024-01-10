package me.portmapping.pbunkers.clients;

import lombok.Getter;
import me.portmapping.pbunkers.PBunkers;
import me.portmapping.pbunkers.clients.type.CheatBreakerClient;
import me.portmapping.pbunkers.clients.type.LunarClient;
import me.portmapping.pbunkers.utils.Utils;
import me.portmapping.pbunkers.waypoints.Waypoint;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

/**
 * Copyright (c) 2023. Keano
 * Use or redistribution of source or file is
 * only permitted if given explicit permission.
 */
@Getter
public class ClientHook implements Client {

    private final List<Client> clients;
    private final PBunkers instance;

    public ClientHook(PBunkers instance) {
        this.instance = instance;
        this.clients = new ArrayList<>();
        this.load();
    }

    private void load() {
        if (Utils.verifyPlugin("LunarClient-API", getInstance())) {
            clients.add(new LunarClient(this));
        }

        if (Utils.verifyPlugin("CheatBreakerAPI", getInstance())) {
            clients.add(new CheatBreakerClient(this));
        }
    }

    @Override
    public void overrideNametags(Player target, Player viewer, List<String> tag) {
        for (Client client : clients) {
            client.overrideNametags(target, viewer, tag);
        }
    }

    @Override
    public void sendWaypoint(Player player, Location location, Waypoint waypoint, UnaryOperator<String> replacer) {
        for (Client client : clients) {
            client.sendWaypoint(player, location, waypoint, replacer);
        }
    }

    @Override
    public void removeWaypoint(Player player, Location location, Waypoint waypoint, UnaryOperator<String> replacer) {
        for (Client client : clients) {
            client.removeWaypoint(player, location, waypoint, replacer);
        }
    }

    @Override
    public void handleJoin(Player player) {
        for (Client client : clients) {
            client.handleJoin(player);
        }
    }



    @Override
    public void clearTeamViewer(Player player) {
        for (Client client : clients) {
            client.clearTeamViewer(player);
        }
    }

    @Override
    public void giveStaffModules(Player player) {
        for (Client client : clients) {
            client.giveStaffModules(player);
        }
    }

    @Override
    public void disableStaffModules(Player player) {
        for (Client client : clients) {
            client.disableStaffModules(player);
        }
    }
}