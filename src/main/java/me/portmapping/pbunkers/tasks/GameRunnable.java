package me.portmapping.pbunkers.tasks;

import me.portmapping.pbunkers.PBunkers;
import me.portmapping.pbunkers.game.match.events.MatchStartEvent;
import me.portmapping.pbunkers.game.match.Match;
import me.portmapping.pbunkers.game.match.MatchState;
import me.portmapping.pbunkers.user.player.PlayerData;
import me.portmapping.pbunkers.utils.chat.CC;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class GameRunnable extends BukkitRunnable {
    private final Match match;
    private final PBunkers plugin;
    private int endingTime = 10;
    public GameRunnable(Match match, PBunkers plugin){
        this.match = match;
        this.plugin = plugin;
    }


    @Override
    public void run() {


        switch (match.getMatchState()){
            case WAITING:
                if(match.getPlayerList().size()>=4){
                    match.setMatchState(MatchState.STARTING);
                }else {
                    match.setStartTime(12);
                }
                break;

            case STARTING:
                if(match.getPlayerList().size()>=4){
                    if(match.getStartTime()!=0){
                        match.setStartTime(match.getStartTime()-1);
                        match.getPlayerList().forEach(player -> player.sendMessage(CC.translate("&eStarting in: "+match.getStartTime())));
                    }else {
                        match.setMatchState(MatchState.ACTIVE);

                        plugin.getServer().getPluginManager().callEvent(new MatchStartEvent(match));
                    }
                }else {
                    match.setMatchState(MatchState.WAITING);
                }
                break;
            case  ACTIVE:
                match.setGameTime(match.getGameTime()+1);
                this.lowerEnderPearlCl(match);
                break;
            case ENDING:
               if(endingTime>0){
                   match.getWinner().getPlayers().forEach(player -> {
                       Firework firework = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
                       FireworkMeta fireworkMeta = firework.getFireworkMeta();
                       fireworkMeta.setPower(2);
                       fireworkMeta.addEffect(FireworkEffect.builder().with(FireworkEffect.Type.CREEPER).withColor(Color.SILVER).build());
                   });
                   endingTime--;
               }else {

                   match.setMatchState(MatchState.RESTARTING);
               }
            case RESTARTING:

        }

    }

    private void lowerEnderPearlCl(Match match){
        match.getPlayerList().forEach(player -> {
            PlayerData playerData = plugin.getPlayerManager().getPlayerData(player);
            if(playerData.getPearlCl()>0){
                playerData.setPearlCl(playerData.getPearlCl()-1);
            }
        });
    }


}
