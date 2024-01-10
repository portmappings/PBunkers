package me.portmapping.pbunkers.game.match;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.portmapping.pbunkers.game.arena.Arena;
import me.portmapping.pbunkers.game.team.Koth;
import me.portmapping.pbunkers.game.team.Team;
import me.portmapping.pbunkers.waypoints.Waypoint;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class Match {
    private final String name;
    private final Arena arena;
    private List<Player> playerList;
    private List<Player> spectatorList;
    private Team redTeam;
    private Team blueTeam;
    private Team yellowTeam;
    private Team greenTeam;
    private MatchState matchState;
    private int gameTime;
    private int startTime = 12;
    private int kothTime = 600;
    private Team winner;
    private List<Block> placedBlocks = new ArrayList<>();
    private List<Team> teamsLeft = new ArrayList<>();




}
