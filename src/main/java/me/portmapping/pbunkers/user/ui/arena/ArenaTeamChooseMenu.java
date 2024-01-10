package me.portmapping.pbunkers.user.ui.arena;

import lombok.RequiredArgsConstructor;
import me.portmapping.pbunkers.game.team.Team;
import me.portmapping.pbunkers.user.ui.match.spectating.TeleportPlayerMenu;
import me.portmapping.pbunkers.utils.item.ItemBuilder;
import me.portmapping.pbunkers.utils.chat.CC;
import me.portmapping.pbunkers.utils.menu.Button;
import me.portmapping.pbunkers.utils.menu.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ArenaTeamChooseMenu extends Menu {

    @Override
    public String getTitle(Player player) {
        return null;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        return null;
    }

    @Override
    public int getSize(){
        return 9 * 3;
    }


    @RequiredArgsConstructor
    private class TeleportTeamButton extends Button{


        private final Team team;

        @Override
        public ItemStack getButtonItem(Player player) {
            switch (this.team.getTeamColor()){
                case RED:
                    return new ItemBuilder(Material.WOOL).durability(14).name(CC.translate("&c&lRed Team"))
                            .build();
                case BLUE:
                    return new ItemBuilder(Material.WOOL).durability(11).name(CC.translate("&1&lBlue Team"))
                            .build();
                case YELLOW:
                    return new ItemBuilder(Material.WOOL).durability(4).name(CC.translate("&e&lYellow Team"))
                            .build();
                case GREEN:
                    return new ItemBuilder(Material.WOOL).durability(13).name(CC.translate("&a&lGreen Team"))
                           .build();
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
