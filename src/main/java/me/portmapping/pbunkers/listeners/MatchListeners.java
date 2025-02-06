package me.portmapping.pbunkers.listeners;

import me.portmapping.pbunkers.PBunkers;
import me.portmapping.pbunkers.game.match.events.MatchStartEvent;
import me.portmapping.pbunkers.game.match.Match;
import me.portmapping.pbunkers.game.match.events.MatchEnterClaimEvent;
import me.portmapping.pbunkers.game.team.Koth;
import me.portmapping.pbunkers.game.team.Team;
import me.portmapping.pbunkers.game.team.TeamColor;
import me.portmapping.pbunkers.tasks.KothTask;
import me.portmapping.pbunkers.tasks.VillagerSpawnRunnable;
import me.portmapping.pbunkers.user.player.PlayerState;
import me.portmapping.pbunkers.utils.Cuboid;
import me.portmapping.pbunkers.utils.chat.CC;
import me.portmapping.pbunkers.waypoints.Waypoint;
import me.portmapping.pbunkers.waypoints.WaypointType;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.function.UnaryOperator;

public class MatchListeners implements Listener {

    private PBunkers plugin = PBunkers.getInstance();

    @EventHandler
    public void onMatchStart(MatchStartEvent event){
        if(event.getMatch()==null){
            return;
        }

        Match match = event.getMatch();


        for (Player player : match.getPlayerList()){
            player.sendTitle(CC.translate("&b&lMatch Started!"),"",20,70,20);
            plugin.getPlayerManager().getPlayerData(player).setPlayerState(PlayerState.FIGHTING);
            player.getInventory().clear();
            player.setFlying(false);
            player.setGameMode(GameMode.SURVIVAL);
            player.setFoodLevel(20);
            player.getInventory().addItem(new ItemStack(Material.STONE_AXE));
            player.getInventory().addItem(new ItemStack(Material.STONE_PICKAXE));
            plugin.getWaypointManager().getKothWaypoint().send(player,new Cuboid(match.getArena().getKothClaimMax().toBukkitLocation(),match.getArena().getKothClaimMin().toBukkitLocation()).getCenter(),UnaryOperator.identity());

        }



        match.getRedTeam().getPlayers().forEach(player -> {
            player.teleport(match.getArena().getRedSpawn().toBukkitLocation());
            plugin.getWaypointManager().getHqWaypoint().send(player,match.getArena().getRedSpawn().toBukkitLocation(), UnaryOperator.identity());
        });
        match.getBlueTeam().getPlayers().forEach(player -> {
            player.teleport(match.getArena().getBlueSpawn().toBukkitLocation());
            plugin.getWaypointManager().getHqWaypoint().send(player,match.getArena().getBlueVillagerBuildSpawn().toBukkitLocation(), UnaryOperator.identity());
        });
        match.getYellowTeam().getPlayers().forEach(player -> {
            player.teleport(match.getArena().getYellowSpawn().toBukkitLocation());
            plugin.getWaypointManager().getHqWaypoint().send(player,match.getArena().getYellowSpawn().toBukkitLocation(), UnaryOperator.identity());
        });
        match.getGreenTeam().getPlayers().forEach(player ->{
            player.teleport(match.getArena().getGreenSpawn().toBukkitLocation());
            plugin.getWaypointManager().getHqWaypoint().send(player,match.getArena().getGreenSpawn().toBukkitLocation(), UnaryOperator.identity());
        });

        match.getRedTeam().setDTR(match.getRedTeam().getPlayers().size());
        match.getBlueTeam().setDTR(match.getBlueTeam().getPlayers().size());
        match.getYellowTeam().setDTR(match.getYellowTeam().getPlayers().size());
        match.getGreenTeam().setDTR(match.getGreenTeam().getPlayers().size());






        new VillagerSpawnRunnable(match).runTaskLater(plugin,120L);
        new KothTask(match,plugin).runTaskTimer(plugin,20L,20L);







    }



    @EventHandler
    public void enterClaimListener(MatchEnterClaimEvent event){
        Team from = event.getFromTeam();
        Team to = event.getToTeam();


        event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&eNow leaving: " + (from == null ? "&4Warzone" : TeamColor.getChatColor(from.getTeamColor()) + from.getTeamColor().name())));
        event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&eNow entering: " + (to == null ? "&4Warzone" : TeamColor.getChatColor(to.getTeamColor()) + to.getTeamColor().name())));

    }


}
