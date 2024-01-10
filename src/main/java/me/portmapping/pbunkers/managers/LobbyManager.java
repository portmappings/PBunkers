package me.portmapping.pbunkers.managers;

import me.portmapping.pbunkers.PBunkers;
import me.portmapping.pbunkers.user.player.PlayerData;
import me.portmapping.pbunkers.user.player.PlayerState;
import me.portmapping.pbunkers.utils.CustomLocation;
import me.portmapping.pbunkers.utils.item.ItemBuilder;
import me.portmapping.pbunkers.utils.chat.CC;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class LobbyManager  {

    private CustomLocation lobbyLocation;

    private final PBunkers instance;
    public LobbyManager(PBunkers instance){
        this.instance = instance;
        this.load();
    }
    private void load(){
        FileConfiguration config = instance.getSettingsConfig().getConfig();

        CustomLocation lobbyLocation = CustomLocation.stringToLocation(config.getString("LOBBY.SPAWN"));
        this.lobbyLocation = lobbyLocation;
    }

    public void sendToLobby(Player player){

        PlayerData playerData = instance.getPlayerManager().getPlayerData(player);
        playerData.setPlayerState(PlayerState.LOBBY);
        playerData.setCurrentMatchName(null);
        playerData.setCurrentTeamColor(null);
        playerData.setPearlCl(0);
        playerData.setBalance(0);
        player.setFlying(false);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.teleport(lobbyLocation.toBukkitLocation());
        player.getInventory().clear();
        player.getInventory().setItem(4,joinMatchItem());



    }

    public ItemStack joinMatchItem(){
        return new ItemBuilder(Material.NETHER_STAR).name(CC.translate("&bPlay")).lore(CC.translate("&eClick to join a match")).build();
    }


}
