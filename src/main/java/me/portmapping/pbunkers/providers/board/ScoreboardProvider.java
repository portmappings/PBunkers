package me.portmapping.pbunkers.providers.board;


import me.portmapping.pbunkers.PBunkers;
import me.portmapping.pbunkers.game.match.Match;
import me.portmapping.pbunkers.game.team.Team;
import me.portmapping.pbunkers.game.team.TeamColor;
import me.portmapping.pbunkers.scoreboard.scoreboard.Board;
import me.portmapping.pbunkers.scoreboard.scoreboard.BoardAdapter;
import me.portmapping.pbunkers.scoreboard.scoreboard.cooldown.BoardCooldown;
import me.portmapping.pbunkers.user.player.PlayerData;
import me.portmapping.pbunkers.utils.Formatter;
import me.portmapping.pbunkers.utils.TimeUtils;
import me.portmapping.pbunkers.utils.chat.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Scoreboard;

import java.util.*;


public class ScoreboardProvider implements BoardAdapter, Listener {

  private final PBunkers instance;

  public ScoreboardProvider(PBunkers instance) {
    this.instance = instance;
    Bukkit.getPluginManager().registerEvents(this,instance);
  }

  @Override
  public String getTitle(Player player) {
    return CC.translate("&b&lBunkers");
  }


  @Override
  public List<String> getScoreboard(Player player, Board board, Set<BoardCooldown> cooldowns) {
    PlayerData playerData = instance.getPlayerManager().getPlayerData(player);
    if (playerData == null) {
      instance.getLogger().warning(
              player.getName() + "'s player data is null" + "(" + this.getClass().getName() + ")");
      return null;
    }



    switch (playerData.getPlayerState()) {
      case LOBBY:
        return inLobbyScoreboard(player);
      case WAITING:
        return inArenaWaiting(player);
      case FIGHTING:
        return inActiveMatch(player);

    }

    return null;
  }




  private List<String> inLobbyScoreboard(Player player) {
    PlayerData playerData = instance.getPlayerManager().getPlayerData(player);
    List<String> strings = new LinkedList<>();
    strings.add(CC.translate(CC.LINE));
    strings.add(CC.translate("&b&lProfile:"));
    strings.add(CC.translate(""));
    strings.add(CC.translate(" &b&l"+player.getName()));
    strings.add(CC.translate(" &bWins: &c"+playerData.getWins()));
    strings.add(CC.translate(" &bKills: &a"+playerData.getKills()));
    strings.add(CC.translate(" &bDeaths: &a"+playerData.getDeaths()));
    strings.add(CC.translate(CC.LINE));


    return strings;
  }
  private List<String> inArenaWaiting(Player player) {
    List<String> strings = new LinkedList<>();
    PlayerData playerData = instance.getPlayerManager().getPlayerData(player);
    Match match = instance.getMatchManager().getMatch(playerData.getCurrentMatchName());
    strings.add(CC.translate(CC.LINE));
    strings.add(CC.translate("&b&lStarting in: &c"+match.getStartTime()));
    strings.add(CC.translate("&b&lTeam: &r"+TeamColor.getChatColor(playerData.getCurrentTeamColor())+playerData.getCurrentTeamColor().name()));
    strings.add(CC.translate("&e&lOnline: &c"+match.getPlayerList().size()));
    strings.add(CC.translate(CC.LINE));


    strings.add("");


    return strings;
  }

  private List<String> inActiveMatch(Player player) {
    List<String> strings = new LinkedList<>();
    PlayerData playerData = instance.getPlayerManager().getPlayerData(player);
    Match match = instance.getMatchManager().getMatch(playerData.getCurrentMatchName());
    Team team = instance.getTeamManager().getByPlayer(player,match);
    strings.add(CC.translate(CC.LINE));
    strings.add(CC.translate("&6&lGame Time: &c"+ TimeUtils.formatIntoMMSS(match.getGameTime())));
    strings.add(CC.translate(" "));
    if(instance.getTimerManager().getTeleportTimer().getRemaining(player)>0){
      strings.add(CC.translate("&9&lHQ:"+ Formatter.getRemaining(instance.getTimerManager().getTeleportTimer().getRemaining(player),true)));
    }
    strings.add(CC.translate("&e&lTeam: &r")+ TeamColor.getChatColor(playerData.getCurrentTeamColor())+playerData.getCurrentTeamColor().name());
    strings.add(CC.translate("&a&lDTR: &c"+team.getDTR()));
    strings.add(CC.translate("&4&lKoTH: &c"+TimeUtils.formatIntoMMSS(match.getKothTime())));
    strings.add(CC.translate("&2&lBalance: &c$"+playerData.getBalance()));
    strings.add(CC.translate(CC.LINE));
    strings.add(CC.translate("     &7&ocurlpvp.net"));



    return strings;
  }




}
