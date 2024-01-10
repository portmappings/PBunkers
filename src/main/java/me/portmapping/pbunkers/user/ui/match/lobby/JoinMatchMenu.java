package me.portmapping.pbunkers.user.ui.match.lobby;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import me.portmapping.pbunkers.game.match.Match;
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


public class JoinMatchMenu extends Menu {


    @Override
    public String getTitle(Player player) {
        return "Join a match";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        int slot = 0;
        for(Match match : plugin.getMatchManager().matchMap.values()){
            buttons.put(slot,new JoinMatchButton(match));
            slot++;
        }


        return buttons;
    }

    @Override
    public int getSize(){
        return 9 * 4;
    }


    @AllArgsConstructor
    private class  JoinMatchButton extends Button{

        private final Match match;

        @Override
        public ItemStack getButtonItem(Player player) {
            switch (match.getMatchState()){
                case WAITING:
                    return new ItemBuilder(Material.STAINED_CLAY).durability(5).name(CC.translate("&7➥ &3"+match.getName()))
                            .lore(
                                    Lists.newArrayList(
                                            CC.translate(" "),
                                            CC.translate("&7Status: &2Waiting"),
                                            CC.translate("&7Players: &f"+match.getPlayerList().size()+"&7/&f20"),
                                            CC.translate(""),
                                            CC.translate("&eClick to join!")))
                            .build();
                case STARTING:
                    return new ItemBuilder(Material.STAINED_CLAY).durability(4).name(CC.translate("&7➥ &3"+match.getName()))
                            .lore(
                                    Lists.newArrayList(
                                            CC.translate(" "),
                                            CC.translate("&7Status: &eStarting"),
                                            CC.translate("&7Players: &f"+match.getPlayerList().size()+"&7/&f20"),
                                            CC.translate(""),
                                            CC.translate("&eClick to join!")))
                            .build();
                case ACTIVE:
                    return new ItemBuilder(Material.STAINED_CLAY).durability(14).name(CC.translate("&7➥ &3"+match.getName()))
                            .lore(
                                    Lists.newArrayList(
                                            CC.translate(" "),
                                            CC.translate("&7Status: &cIn-Game"),
                                            CC.translate("&7Players: &f"+match.getPlayerList().size()+"&7/&f20"),
                                            CC.translate(""),
                                            CC.translate("&eClick to join!")))
                            .build();
                case ENDING:
                    return new ItemBuilder(Material.STAINED_CLAY).durability(7).name(CC.translate("&7➥ &3"+match.getName()))
                            .lore(
                                    Lists.newArrayList(
                                            CC.translate(" "),
                                            CC.translate("&7Status: &6Ending"),
                                            CC.translate("&7Players: &f"+match.getPlayerList().size()+"&7/&f20"),
                                            CC.translate(""),
                                            CC.translate("&eClick to join!")))
                            .build();
                case RESTARTING:
                    return new ItemBuilder(Material.STAINED_CLAY).durability(7).name(CC.translate("&7➥ &3"+match.getName()))
                            .lore(
                                    Lists.newArrayList(
                                            CC.translate(" "),
                                            CC.translate("&7Status: &4Restarting"),
                                            CC.translate("&7Players: &f"+match.getPlayerList().size()+"&7/&f20"),
                                            CC.translate(""),
                                            CC.translate("&eClick to join!")))
                            .build();
            }
            return null;
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbar){
            PlayerData playerData = instance.getPlayerManager().getPlayerData(player);

            switch (this.match.getMatchState()){
                case STARTING:
                case WAITING:
                    player.closeInventory();
                    instance.getMatchManager().joinPlayer(match,player);
                    break;
                case ACTIVE:
                    player.closeInventory();
                    instance.getMatchManager().joinSpectator(match,player);
                    break;
                case ENDING:
                    break;
                case RESTARTING:
                    break;
            }

        }
    }
}
