package me.portmapping.pbunkers.utils.menu.buttons;

import lombok.AllArgsConstructor;
import me.portmapping.pbunkers.utils.menu.Button;
import me.portmapping.pbunkers.utils.menu.Menu;
import me.portmapping.pbunkers.utils.menu.Button;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@AllArgsConstructor
public class BackButton extends Button {

  private Menu back;

  @Override
  public ItemStack getButtonItem(Player player) {
    ItemStack itemStack = new ItemStack(Material.BED);
    ItemMeta itemMeta = itemStack.getItemMeta();

    itemMeta.setDisplayName(ChatColor.RED + "Go back");
    itemStack.setItemMeta(itemMeta);

    return itemStack;
  }

  @Override
  public void clicked(Player player, int i, ClickType clickType, int hb) {
    Button.playNeutral(player);

    this.back.openMenu(player);
  }
}
