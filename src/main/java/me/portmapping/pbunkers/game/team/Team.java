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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Setter
@Getter
public class Team {
    private List<Player> players = new ArrayList<>();
    private TeamColor teamColor;
    private double DTR = 6.0;
    private Cuboid cuboid;
    private UUID combatVillager = null;
    private UUID buildVillager = null;
    private UUID sellVillager = null;
    private UUID enchantVillager = null;
    private CustomLocation spawn;
    public Team(TeamColor teamColor, Cuboid cuboid, CustomLocation spawn){
        this.teamColor = teamColor;
        this.cuboid = cuboid;
        this.spawn = spawn;
    }


}
