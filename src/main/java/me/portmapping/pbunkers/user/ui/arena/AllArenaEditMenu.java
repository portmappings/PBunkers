package me.portmapping.pbunkers.user.ui.arena;

import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import me.portmapping.pbunkers.PBunkers;
import me.portmapping.pbunkers.game.arena.Arena;
import me.portmapping.pbunkers.utils.item.ItemBuilder;
import me.portmapping.pbunkers.utils.chat.CC;
import me.portmapping.pbunkers.utils.menu.Button;
import me.portmapping.pbunkers.utils.menu.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class AllArenaEditMenu extends Menu {

    private final  PBunkers plugin = PBunkers.getInstance();

    @Override
    public String getTitle(Player player) {
        return null;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer,Button> buttons = Maps.newHashMap();
        AtomicInteger value = new AtomicInteger();
        for(Arena arena : plugin.getArenaManager().arenaMap.values()){
            buttons.put(value.getAndIncrement(),new ArenaEditButton(arena));
        }
        return buttons;
    }

    @Override
    public int getSize(){
        return 9 * 6;
    }


    @RequiredArgsConstructor
    private class ArenaEditButton extends Button{

        private final Arena arena;

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemBuilder(arena.getIconMaterial()).durability(arena.getIconData()).name(arena.getName()).lore(CC.YELLOW+"Click to edit").build();
        }
        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbar){

        }
    }
}
