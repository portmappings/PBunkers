package me.portmapping.pbunkers.user.ui.team;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import me.portmapping.pbunkers.game.match.Match;
import me.portmapping.pbunkers.game.team.Team;
import me.portmapping.pbunkers.game.team.TeamColor;
import me.portmapping.pbunkers.user.player.PlayerData;
import me.portmapping.pbunkers.utils.item.ItemBuilder;
import me.portmapping.pbunkers.utils.chat.CC;
import me.portmapping.pbunkers.utils.menu.Button;
import me.portmapping.pbunkers.utils.menu.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
@AllArgsConstructor
public class TeamSelector extends Menu {

    private Match match;

    @Override
    public String getTitle(Player player) {
        return "Choose a team";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        buttons.put(10,new TeamButton(TeamColor.RED,match));
        buttons.put(12,new TeamButton(TeamColor.BLUE,match));
        buttons.put(14,new TeamButton(TeamColor.YELLOW,match));
        buttons.put(16,new TeamButton(TeamColor.GREEN,match));

        return buttons;
    }

    @Override
    public int getSize(){
        return 9*3;
    }



    @AllArgsConstructor
    private class TeamButton extends Button {


        private TeamColor color;
        private Match match;

        @Override
        public ItemStack getButtonItem(Player player) {

            switch (color){
                case RED:
                    return new ItemBuilder(Material.WOOL).durability(14).name(CC.translate("&c&lRED"))
                            .lore(Lists.newArrayList(
                                    formatPlayerSlot(0,TeamColor.RED,match),
                                    formatPlayerSlot(1,TeamColor.RED,match),
                                    formatPlayerSlot(2,TeamColor.RED,match),
                                    formatPlayerSlot(3,TeamColor.RED,match),
                                    CC.translate("&eClick to join"))).build();
                case BLUE:
                    return new ItemBuilder(Material.WOOL).durability(11).name(CC.translate("&1&lBLUE"))
                            .lore(Lists.newArrayList(
                                    formatPlayerSlot(0,TeamColor.BLUE,match),
                                    formatPlayerSlot(1,TeamColor.BLUE,match),
                                    formatPlayerSlot(2,TeamColor.BLUE,match),
                                    formatPlayerSlot(3,TeamColor.BLUE,match),
                                    CC.translate("&eClick to join"))).build();
                case YELLOW:
                    return new ItemBuilder(Material.WOOL).durability(4).name(CC.translate("&e&lYELLOW"))
                            .lore(Lists.newArrayList(
                                    formatPlayerSlot(0,TeamColor.YELLOW,match),
                                    formatPlayerSlot(1,TeamColor.YELLOW,match),
                                    formatPlayerSlot(2,TeamColor.YELLOW,match),
                                    formatPlayerSlot(3,TeamColor.YELLOW,match),
                                CC.translate("&eClick to join"))).build();
                case GREEN:
                    return new ItemBuilder(Material.WOOL).durability(5).name(CC.translate("&2&lGREEN"))
                            .lore(Lists.newArrayList(
                                    formatPlayerSlot(0,TeamColor.GREEN,match),
                                    formatPlayerSlot(1,TeamColor.GREEN,match),
                                    formatPlayerSlot(2,TeamColor.GREEN,match),
                                    formatPlayerSlot(3,TeamColor.GREEN,match),
                                CC.translate("&eClick to join"))).build();
                default:
                    return null;
            }



        }


        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarbutton){
            PlayerData playerData = instance.getPlayerManager().getPlayerData(player);
            Match match = instance.getMatchManager().getMatch(playerData.getCurrentMatchName());
            if(match == null){
                player.sendMessage(CC.RED+"You are not currently in a match");
            }else {
                switch (this.color){
                    case RED:
                        instance.getMatchManager().removeFromAllTeams(match,player);
                        match.getRedTeam().getPlayers().add(player);
                        playerData.setCurrentTeamColor(TeamColor.RED);

                        break;
                    case BLUE:
                        instance.getMatchManager().removeFromAllTeams(match,player);
                        match.getBlueTeam().getPlayers().add(player);
                        playerData.setCurrentTeamColor(TeamColor.BLUE);

                        break;
                    case YELLOW:
                        instance.getMatchManager().removeFromAllTeams(match,player);
                        match.getYellowTeam().getPlayers().add(player);
                        playerData.setCurrentTeamColor(TeamColor.YELLOW);

                        break;
                    case GREEN:
                        instance.getMatchManager().removeFromAllTeams(match,player);
                        match.getGreenTeam().getPlayers().add(player);
                        playerData.setCurrentTeamColor(TeamColor.GREEN);

                        break;
                }
                player.sendMessage(CC.GREEN+"You joined team "+this.color.name());
            }
            new TeamSelector(match).openMenu(player);
        }
    }

    private String formatPlayerSlot(int slot, TeamColor color, Match match){
        Team team = null;

        switch (color){
            case RED:
                team = match.getRedTeam();
                break;
            case BLUE:
                team = match.getBlueTeam();
                break;
            case YELLOW:
                team = match.getYellowTeam();
                break;
            case GREEN:
                team = match.getGreenTeam();
                break;

        }




        if(slot+1>team.getPlayers().size()){
            return CC.translate("&7- Empty");

        }else {
            Player player = team.getPlayers().get(slot);
            return CC.translate("&e- "+player.getName());
        }







    }
}
