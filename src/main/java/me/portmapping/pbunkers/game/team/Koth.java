package me.portmapping.pbunkers.game.team;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.portmapping.pbunkers.PBunkers;
import me.portmapping.pbunkers.game.match.Match;
import me.portmapping.pbunkers.utils.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;




public class Koth  {
    @Getter @Setter private int seconds = 600;
    @Getter @Setter private int capSeconds = 600;
    @Getter @Setter
    Player controller;

    private final Match match;

    public Koth(Match match) {
        this.match = match;
        new BukkitRunnable() {
            @Override public void run() {
                if (match.getGameTime() == 1000) {
                    setSeconds(300);
                    match.setKothTime(300);
                    if (capSeconds > 300) {
                        setCapSeconds(300);
                    }
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&7[&6KingOfTheHill&7] &eThe &9Koth &etime has been decreased. &7(05:00)"));
                }
            }
        }.runTaskTimerAsynchronously(PBunkers.getInstance(), 0L, 20L);
    }

    public boolean isInsideArea(Location location) {
        return new Cuboid(match.getArena().getKothClaimMax().toBukkitLocation(),match.getArena().getKothClaimMin().toBukkitLocation()).contains(location);
    }
}