package me.portmapping.pbunkers.listeners;

import me.portmapping.pbunkers.PBunkers;
import me.portmapping.pbunkers.enums.VillagerType;
import me.portmapping.pbunkers.game.match.Match;
import me.portmapping.pbunkers.game.team.Team;
import me.portmapping.pbunkers.game.team.TeamColor;
import me.portmapping.pbunkers.user.player.PlayerData;
import me.portmapping.pbunkers.utils.chat.CC;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class EntityListeners implements Listener {

    private final PBunkers plugin = PBunkers.getInstance();

    @EventHandler
    public void on(EntityDeathEvent event){
        Player player = event.getEntity().getKiller();
        if(player==null){
            return;
        }
        PlayerData playerData = plugin.getPlayerManager().getPlayerData(player);

        switch (playerData.getPlayerState()){
            case LOBBY:
                break;
            case WAITING:
                break;
            case FIGHTING:
                Match match = plugin.getMatchManager().getMatch(player);
                Entity entity = event.getEntity();
                Team team = plugin.getTeamManager().getByLocation(event.getEntity().getLocation(), match);
                if(team==null){
                    break;
                }
                if(entity.getType() == EntityType.VILLAGER){
                    switch (ChatColor.stripColor(entity.getCustomName())){
                        case "Combat Shop":
                            team.getPlayers().forEach(members -> members.sendTitle(CC.RED+"&cCombat Villager Killed",CC.WHITE+"Spawning in 3 minutes", 20 ,20 ,20));
                           new BukkitRunnable(){
                               @Override
                               public void run(){
                                   plugin.getMatchManager().createVillager(match,team.getTeamColor(),VillagerType.COMBAT);
                               }
                           }.runTaskLater(plugin,3600L);
                            break;
                        case "Build Shop":
                            team.getPlayers().forEach(members -> members.sendTitle(CC.RED+"&cBuild Villager Killed",CC.WHITE+"Spawning in 3 minutes", 20 ,20 ,20));
                            new BukkitRunnable(){
                                @Override
                                public void run(){
                                    plugin.getMatchManager().createVillager(match,team.getTeamColor(),VillagerType.BUILD);
                                }
                            }.runTaskLater(plugin,3600L);
                            break;
                        case "Sell Items":
                            team.getPlayers().forEach(members -> members.sendTitle(CC.RED+"&cSell Villager Killed",CC.WHITE+"Spawning in 3 minutes", 20 ,20 ,20));
                            new BukkitRunnable(){
                                @Override
                                public void run(){
                                    plugin.getMatchManager().createVillager(match,team.getTeamColor(),VillagerType.SELL);
                                }
                            }.runTaskLater(plugin,3600L);
                            break;
                    }
                }
                break;
        }

    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)){
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void onWeather(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

}
