package me.portmapping.pbunkers.clients.type;

import com.google.common.collect.Lists;
import com.lunarclient.apollo.Apollo;
import com.lunarclient.apollo.common.location.ApolloBlockLocation;
import com.lunarclient.apollo.module.ApolloModuleManager;
import com.lunarclient.apollo.module.combat.CombatModule;
import com.lunarclient.apollo.module.nametag.Nametag;
import com.lunarclient.apollo.module.nametag.NametagModule;
import com.lunarclient.apollo.module.staffmod.StaffMod;
import com.lunarclient.apollo.module.staffmod.StaffModModule;
import com.lunarclient.apollo.module.waypoint.WaypointModule;
import com.lunarclient.apollo.option.SimpleOption;
import com.lunarclient.apollo.player.ApolloPlayer;
import com.lunarclient.apollo.recipients.Recipients;
import me.portmapping.pbunkers.clients.Client;
import me.portmapping.pbunkers.clients.ClientHook;
import me.portmapping.pbunkers.waypoints.Waypoint;
import me.portmapping.pbunkers.waypoints.WaypointType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

/**
 * Copyright (c) 2023. Keano
 * Use or redistribution of source or file is
 * only permitted if given explicit permission.
 */
public class LunarClient implements Client {

    private NametagModule nametagModule;
    private WaypointModule waypointModule;
    private CombatModule combatModule;
    private StaffModModule staffModModule;

    public LunarClient(ClientHook manager) {
        ApolloModuleManager moduleManager = Apollo.getModuleManager();
        this.nametagModule = moduleManager.getModule(NametagModule.class);
        this.waypointModule = moduleManager.getModule(WaypointModule.class);
        this.combatModule = moduleManager.getModule(CombatModule.class);
        this.staffModModule = moduleManager.getModule(StaffModModule.class);
    }

    @Override
    public void overrideNametags(Player target, Player viewer, List<String> tag) {
        this.nametagModule.overrideNametag(Recipients.ofEveryone(), target.getUniqueId(), Nametag.builder()
                .lines(Lists.newArrayList(
                        Component.text()
                                .content("[StaffMode]")
                                .decorate(TextDecoration.ITALIC)
                                .color(NamedTextColor.GRAY)
                                .build(),
                        Component.text()
                                .content(target.getName())
                                .color(NamedTextColor.RED)
                                .build()
                ))
                .build()
        );
    }

    @Override
    public void sendWaypoint(Player player, Location location, Waypoint waypoint, UnaryOperator<String> replacer) {
        Optional<ApolloPlayer> apolloPlayerOpt = Apollo.getPlayerManager().getPlayer(player.getUniqueId());

        apolloPlayerOpt.ifPresent(apolloPlayer -> {
            this.waypointModule.displayWaypoint(apolloPlayer, com.lunarclient.apollo.module.waypoint.Waypoint.builder()
                    .name("KoTH")
                    .location(ApolloBlockLocation.builder()
                            .world("world")
                            .x(500)
                            .y(100)
                            .z(500)
                            .build()
                    )
                    .color(Color.ORANGE)
                    .preventRemoval(false)
                    .hidden(false)
                    .build()
            );
        });
    }

    @Override
    public void removeWaypoint(Player player, Location location, Waypoint waypoint, UnaryOperator<String> replacer) {
        Optional<ApolloPlayer> apolloPlayerOpt = Apollo.getPlayerManager().getPlayer(player.getUniqueId());
        apolloPlayerOpt.ifPresent(apolloPlayer -> this.waypointModule.removeWaypoint(apolloPlayer, waypoint.getName()));
    }

    @Override
    public void handleJoin(Player player) {
        //TODO
        // - Set legacy combat
    }


    @Override
    public void clearTeamViewer(Player player) {
        //TODO
        // - CLear team viewer
    }

    @Override
    public void giveStaffModules(Player player) {
        Optional<ApolloPlayer> apolloPlayerOpt = Apollo.getPlayerManager().getPlayer(player.getUniqueId());
        apolloPlayerOpt.ifPresent(apolloPlayer -> this.staffModModule.enableStaffMods(apolloPlayer, Collections.singletonList(StaffMod.XRAY)));
    }

    @Override
    public void disableStaffModules(Player player) {
        Optional<ApolloPlayer> apolloPlayerOpt = Apollo.getPlayerManager().getPlayer(player.getUniqueId());
        apolloPlayerOpt.ifPresent(apolloPlayer -> this.staffModModule.disableAllStaffMods(apolloPlayer));
    }
}