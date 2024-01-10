package me.portmapping.pbunkers.tasks;

import lombok.RequiredArgsConstructor;
import me.portmapping.pbunkers.PBunkers;
import me.portmapping.pbunkers.game.match.Match;
import me.portmapping.pbunkers.game.team.Koth;
import me.portmapping.pbunkers.game.team.Team;
import me.portmapping.pbunkers.user.player.PlayerData;
import me.portmapping.pbunkers.user.player.PlayerState;
import me.portmapping.pbunkers.utils.TimeUtils;
import me.portmapping.pbunkers.utils.chat.CC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class KothTask extends BukkitRunnable {

    private final Match match;
    private final PBunkers instance;
    private final Koth koth;

    public KothTask(Match match, PBunkers instace){
        this.match = match;
        this.instance = instace;
        this.koth = new Koth(match);
    }

    @Override public void run() {


        if (koth.getCapSeconds() <= 0) {
            Team team = PBunkers.getInstance().getTeamManager().getByPlayer(koth.getController(),match);
            instance.getMatchManager().setWinner(match,team);
            cancel();
            return;
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (match.getSpectatorList().contains(player)) return;

            if (koth.isInsideArea(player.getLocation())) {
                if(instance.getPlayerManager().getPlayerData(player).getPlayerState() == PlayerState.SPECTATING){
                    return;
                }
                if (koth.getController() == null) {
                    koth.setController(player);
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6[KingOfTheHill] &eSomeone has started controlling the &9Koth&e. &7(" + TimeUtils.formatIntoMMSS(koth.getSeconds()) + ")"));
                }

                if (koth.getCapSeconds() % 30 == 0 && koth.getCapSeconds() != 600) {
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6[KingOfTheHill] &eSomeone is trying to control the &9Koth&e. &7(" + TimeUtils.formatIntoMMSS(koth.getCapSeconds()) + ")"));
                }
            } else {
                if (koth.getController() == player) {
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6[KingOfTheHill] &eSomeone has been knocked off the &9Koth&e. &7(" + TimeUtils.formatIntoMMSS(koth.getCapSeconds()) + ")"));
                    koth.setController(null);
                    match.setKothTime(koth.getSeconds());
                    koth.setCapSeconds(koth.getSeconds());

                }
            }
        }

        if (koth.getController() != null) {
            match.setKothTime(match.getKothTime()-1);
            koth.setCapSeconds(koth.getCapSeconds() - 1);
        }
    }
}
