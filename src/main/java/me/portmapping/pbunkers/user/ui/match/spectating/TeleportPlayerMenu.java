package me.portmapping.pbunkers.user.ui.match.spectating;

import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
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
import java.util.concurrent.atomic.AtomicInteger;


@RequiredArgsConstructor
public class TeleportPlayerMenu extends Menu {

    private final Team team;

    @Override
    public String getTitle(Player player) {
        return "Teleport to someone";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer,Button> buttons = Maps.newHashMap();

        AtomicInteger slot = new AtomicInteger(10);
        team.getPlayers().forEach(teamMembers -> {
            buttons.put(slot.getAndAdd(2),new TeleportPlayerButton(teamMembers));
        });


        return buttons;
    }

    @Override
    public int getSize(){
        return 9 * 3;
    }



    @RequiredArgsConstructor
    private class TeleportPlayerButton extends Button{

        private final Player teleportPlayer;


        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemBuilder(Material.SKULL_ITEM).durability(3).owner(player.getName()).name(CC.RED+teleportPlayer.getName()).lore(CC.YELLOW+"Click to teleport").build();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbar){
            player.closeInventory();
            player.teleport(teleportPlayer.getLocation());
        }
    }
}
