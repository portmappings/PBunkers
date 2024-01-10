package me.portmapping.pbunkers.user.ui.match.spectating;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import me.portmapping.pbunkers.game.match.Match;
import me.portmapping.pbunkers.game.team.Team;
import me.portmapping.pbunkers.utils.item.ItemBuilder;
import me.portmapping.pbunkers.utils.chat.CC;
import me.portmapping.pbunkers.utils.menu.Button;
import me.portmapping.pbunkers.utils.menu.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Map;


@RequiredArgsConstructor
public class ChooseTeleportTeam extends Menu {

    private final Match match;

    @Override
    public String getTitle(Player player) {
        return "Choose a team";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer,Button> buttons = Maps.newHashMap();
        buttons.put(10, new TeleportTeamButton(match.getRedTeam()));
        buttons.put(12, new TeleportTeamButton(match.getBlueTeam()));
        buttons.put(14, new TeleportTeamButton(match.getYellowTeam()));
        buttons.put(16, new TeleportTeamButton(match.getGreenTeam()));
        return buttons;
    }



    @RequiredArgsConstructor
    private class TeleportTeamButton extends Button{


        private final Team team;

        @Override
        public ItemStack getButtonItem(Player player) {
            switch (this.team.getTeamColor()){
                case RED:
                return new ItemBuilder(Material.WOOL).durability(14).name(CC.translate("&c&lRed Team"))
                        .lore(
                                Lists.newArrayList(
                                        CC.translate("&eClick to teleport to"),
                                        CC.translate("&ethis team's members")
                        )).build();
                case BLUE:
                    return new ItemBuilder(Material.WOOL).durability(11).name(CC.translate("&1&lBlue Team"))
                            .lore(
                                    Lists.newArrayList(
                                            CC.translate("&eClick to teleport to"),
                                            CC.translate("&ethis team's members")
                                    )).build();
                case YELLOW:
                    return new ItemBuilder(Material.WOOL).durability(4).name(CC.translate("&e&lYellow Team"))
                            .lore(
                                    Lists.newArrayList(
                                            CC.translate("&eClick to teleport to"),
                                            CC.translate("&ethis team's members")
                                    )).build();
                case GREEN:
                    return new ItemBuilder(Material.WOOL).durability(13).name(CC.translate("&a&lGreen Team"))
                            .lore(
                                    Lists.newArrayList(
                                            CC.translate("&eClick to teleport to"),
                                            CC.translate("&ethis team's members")
                                    )).build();
            }
            return null;
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbar){
            player.closeInventory();
            new TeleportPlayerMenu(this.team).openMenu(player);

        }
    }





}
