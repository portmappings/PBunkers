package me.portmapping.pbunkers.providers.tab;

import com.google.common.collect.Lists;
import me.portmapping.pbunkers.PBunkers;
import me.portmapping.pbunkers.game.match.Match;
import me.portmapping.pbunkers.game.team.Team;
import me.portmapping.pbunkers.game.team.TeamColor;
import me.portmapping.pbunkers.tablist.adapter.TabAdapter;
import me.portmapping.pbunkers.tablist.entry.TabEntry;
import me.portmapping.pbunkers.user.player.PlayerData;
import me.portmapping.pbunkers.utils.file.ConfigCursor;
import me.portmapping.pbunkers.utils.chat.CC;
import me.portmapping.pbunkers.utils.file.FileConfig;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class TablistProvider implements TabAdapter {

    private final PBunkers plugin = PBunkers.getInstance();
    private final FileConfig tabListConfig = plugin.getTablistConfig();
    private final ConfigCursor tab = new ConfigCursor(plugin.getTablistConfig(),
            "TABLIST");

    @Override
    public String getHeader(Player player) {
        return CC.translate(CC.translate("&b&lCurlPvP"));
    }

    @Override
    public String getFooter(Player player) {
        return CC.translate("FOOTER");
    }

    @Override
    public List<TabEntry> getLines(Player player) {
        List<TabEntry> lines = Lists.newArrayList();
        PlayerData practicePlayerData = plugin.getPlayerManager().getPlayerData(player.getUniqueId());

        switch (practicePlayerData.getPlayerState()){
            case WAITING:
            case LOBBY:
                int column = 0;
                int row = 0;
                for (Player online : Bukkit.getOnlinePlayers()) {
                    try {
                        lines.add(new TabEntry(column, row, online.getName()).setPing(
                                ((CraftPlayer) online).getHandle().ping));
                        if (column++ < 3) {
                            continue;
                        }
                        column = 0;

                        if (row++ < 19) {
                            continue;
                        }
                        row = 0;
                    } catch (Exception ignored) {
                        break;
                    }
                }
                break;
            case FIGHTING:
                Match match = plugin.getMatchManager().getMatch(player);
                Team team = plugin.getTeamManager().getByPlayer(player,match);
                Team teamAt = plugin.getTeamManager().getByLocation(player.getLocation(),match);

                lines.add(new TabEntry(0,0,CC.translate("&dcurlpvp.net")));

                lines.add(new TabEntry(0,2,CC.translate("&b&lTeam Info")));
                lines.add(new TabEntry(0,3,CC.translate("&fDTR: "+team.getDTR())));
                lines.add(new TabEntry(0,4,CC.translate("&fOnline: "+team.getPlayers().size())));

                lines.add(new TabEntry(0,6,CC.translate("&b&lLocation")));
                lines.add(new TabEntry(0,7,teamAt != null ? TeamColor.getChatColor(teamAt.getTeamColor())+teamAt.getTeamColor().name(): CC.translate("&7Warzone")));
                lines.add(new TabEntry(0,8,CC.translate("&f("+player.getLocation().getBlockX()+", "+player.getLocation().getBlockZ()+") [&c"+getCardinalDirection(player)+"&f]")));

                lines.add(new TabEntry(0,10,CC.translate("&b&lGame Info")));
                lines.add(new TabEntry(0,11,CC.translate("&bMap: &f"+match.getArena().getName())));
                lines.add(new TabEntry(0,12,CC.translate("&bPlayers: &f"+match.getPlayerList().size()+"/20")));

                lines.add(new TabEntry(0,14,CC.translate("&b&lDTR")));
                lines.add(new TabEntry(0,15,CC.translate("&cRed&7: &a"+match.getRedTeam().getDTR())));
                lines.add(new TabEntry(0,16,CC.translate("&9Blue&7: &a"+match.getBlueTeam().getDTR())));
                lines.add(new TabEntry(0,17,CC.translate("&aGreen&7: &a"+match.getGreenTeam().getDTR())));
                lines.add(new TabEntry(0,18,CC.translate("&eYellow&7: &a"+match.getYellowTeam().getDTR())));







                lines.add(new TabEntry(1,0,CC.translate("&b&lBunkers")));

                lines.add(new TabEntry(1,2,CC.translate("&c&lRed Team")));
                lines.add(new TabEntry(1,3,CC.translate("&c"+formatPlayerSlot(0,match.getRedTeam()))));
                lines.add(new TabEntry(1,4,CC.translate("&c"+formatPlayerSlot(1,match.getRedTeam()))));
                lines.add(new TabEntry(1,5,CC.translate("&c"+formatPlayerSlot(2,match.getRedTeam()))));
                lines.add(new TabEntry(1,6,CC.translate("&c"+formatPlayerSlot(3,match.getRedTeam()))));
                lines.add(new TabEntry(1,7,CC.translate("&c"+formatPlayerSlot(4,match.getRedTeam()))));

                lines.add(new TabEntry(1,9,CC.translate("&a&lGreen Team")));
                lines.add(new TabEntry(1,10,CC.translate("&a"+formatPlayerSlot(0,match.getGreenTeam()))));
                lines.add(new TabEntry(1,11,CC.translate("&a"+formatPlayerSlot(1,match.getGreenTeam()))));
                lines.add(new TabEntry(1,12,CC.translate("&a"+formatPlayerSlot(2,match.getGreenTeam()))));
                lines.add(new TabEntry(1,13,CC.translate("&a"+formatPlayerSlot(3,match.getGreenTeam()))));
                lines.add(new TabEntry(1,14,CC.translate("&a"+formatPlayerSlot(4,match.getGreenTeam()))));




                lines.add(new TabEntry(2,0,CC.translate("&dcurlpvp.net")));

                lines.add(new TabEntry(2,2,CC.translate("&9&lBlue Team")));
                lines.add(new TabEntry(2,3,CC.translate("&9"+formatPlayerSlot(0,match.getBlueTeam()))));
                lines.add(new TabEntry(2,4,CC.translate("&9"+formatPlayerSlot(1,match.getBlueTeam()))));
                lines.add(new TabEntry(2,5,CC.translate("&9"+formatPlayerSlot(2,match.getBlueTeam()))));
                lines.add(new TabEntry(2,6,CC.translate("&9"+formatPlayerSlot(3,match.getBlueTeam()))));
                lines.add(new TabEntry(2,7,CC.translate("&9"+formatPlayerSlot(4,match.getBlueTeam()))));

                lines.add(new TabEntry(2,9,CC.translate("&e&lYellow Team")));
                lines.add(new TabEntry(2,10,CC.translate("&e"+formatPlayerSlot(0,match.getYellowTeam()))));
                lines.add(new TabEntry(2,11,CC.translate("&e"+formatPlayerSlot(1,match.getYellowTeam()))));
                lines.add(new TabEntry(2,12,CC.translate("&e"+formatPlayerSlot(2,match.getYellowTeam()))));
                lines.add(new TabEntry(2,13,CC.translate("&e"+formatPlayerSlot(3,match.getYellowTeam()))));
                lines.add(new TabEntry(2,14,CC.translate("&e"+formatPlayerSlot(4,match.getYellowTeam()))));






        }




        return lines;
    }

    private String formatPlayerSlot(int slot, Team team){


        if(slot+1>team.getPlayers().size()){
            return CC.translate("");

        }else {
            Player player = team.getPlayers().get(slot);
            return CC.translate(player.getName());
        }



    }
    public static String getCardinalDirection(Player player) {
        double rot = (player.getLocation().getYaw() - 90) % 360;
        if (rot < 0) rot += 360.0;
        return getDirection(rot);
    }
    private static String getDirection(double rot) {
        if (0 <= rot && rot < 22.5) {
            return "W";
        } else if (22.5 <= rot && rot < 67.5) {
            return "NW";
        } else if (67.5 <= rot && rot < 112.5) {
            return "N";
        } else if (112.5 <= rot && rot < 157.5) {
            return "NE";
        } else if (157.5 <= rot && rot < 202.5) {
            return "E";
        } else if (202.5 <= rot && rot < 247.5) {
            return "SE";
        } else if (247.5 <= rot && rot < 292.5) {
            return "S";
        } else if (292.5 <= rot && rot < 337.5) {
            return "SW";
        } else if (337.5 <= rot && rot < 360.0) {
            return "W";
        } else {
            return null;
        }
    }
}
