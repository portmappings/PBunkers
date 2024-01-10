package me.portmapping.pbunkers.game.team;


import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.portmapping.pbunkers.utils.Cuboid;
import me.portmapping.pbunkers.utils.CustomLocation;
import me.portmapping.pbunkers.waypoints.Waypoint;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;


@Setter
@Getter
@AllArgsConstructor
public class Team {
    private List<Player> players;
    private TeamColor teamColor;
    private double DTR;
    private Cuboid cuboid;
    private UUID combatVillager;
    private UUID buildVillager;
    private UUID sellVillager;
    private UUID enchantVillager;
    private CustomLocation spawn;

}
