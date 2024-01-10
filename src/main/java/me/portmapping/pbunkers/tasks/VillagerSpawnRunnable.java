package me.portmapping.pbunkers.tasks;

import lombok.RequiredArgsConstructor;
import me.portmapping.pbunkers.PBunkers;
import me.portmapping.pbunkers.enums.VillagerType;
import me.portmapping.pbunkers.game.match.Match;
import me.portmapping.pbunkers.game.team.TeamColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

@RequiredArgsConstructor
public class VillagerSpawnRunnable extends BukkitRunnable {

    private  final  PBunkers plugin = PBunkers.getInstance();

    private final Match match;

    @Override
    public void run() {

        plugin.getMatchManager().createVillager(match, TeamColor.RED, VillagerType.COMBAT);
        plugin.getMatchManager().createVillager(match, TeamColor.RED, VillagerType.BUILD);
        plugin.getMatchManager().createVillager(match, TeamColor.RED, VillagerType.SELL);


        plugin.getMatchManager().createVillager(match, TeamColor.BLUE, VillagerType.COMBAT);
        plugin.getMatchManager().createVillager(match, TeamColor.BLUE, VillagerType.BUILD);
        plugin.getMatchManager().createVillager(match, TeamColor.BLUE, VillagerType.SELL);

        plugin.getMatchManager().createVillager(match, TeamColor.YELLOW, VillagerType.COMBAT);
        plugin.getMatchManager().createVillager(match, TeamColor.YELLOW, VillagerType.BUILD);
        plugin.getMatchManager().createVillager(match, TeamColor.YELLOW, VillagerType.SELL);

        plugin.getMatchManager().createVillager(match, TeamColor.GREEN, VillagerType.COMBAT);
        plugin.getMatchManager().createVillager(match, TeamColor.GREEN, VillagerType.BUILD);
        plugin.getMatchManager().createVillager(match, TeamColor.GREEN, VillagerType.SELL);

    }

}
