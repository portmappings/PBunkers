package me.portmapping.pbunkers.listeners;

import me.portmapping.pbunkers.PBunkers;
import me.portmapping.pbunkers.game.match.Match;
import me.portmapping.pbunkers.game.match.events.MatchEnterClaimEvent;
import me.portmapping.pbunkers.game.team.Team;
import me.portmapping.pbunkers.user.player.PlayerData;
import me.portmapping.pbunkers.user.player.PlayerState;
import me.portmapping.pbunkers.user.ui.match.lobby.JoinMatchMenu;
import me.portmapping.pbunkers.user.ui.match.spectating.ChooseTeleportTeam;
import me.portmapping.pbunkers.user.ui.shop.CombatMenu;
import me.portmapping.pbunkers.user.ui.shop.SellMenu;
import me.portmapping.pbunkers.user.ui.team.TeamSelector;
import me.portmapping.pbunkers.utils.item.ItemUtils;
import me.portmapping.pbunkers.utils.PlayerInventoryUtils;
import me.portmapping.pbunkers.utils.chat.CC;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Crops;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerListeners implements Listener {

    PBunkers plugin = PBunkers.getInstance();

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event){
        if(event.getPlayer()==null){
            return;
        }
        if(event.getItem()==null){
            return;
        }
        if(event.getItem().getType() == Material.AIR){
            return;
        }
        Player player = event.getPlayer();
        PlayerData playerData = plugin.getPlayerManager().getPlayerData(player);
        ItemStack item  = event.getItem();
        switch (playerData.getPlayerState()){
            case LOBBY:
                if(event.getItem().isSimilar(plugin.getLobbyManager().joinMatchItem())){
                    if(plugin.getPlayerManager().getPlayerData(player).getPlayerState() == PlayerState.LOBBY){
                        new JoinMatchMenu().openMenu(player);
                    }

                }
                break;
            case WAITING:
                Match match = plugin.getMatchManager().getMatch(plugin.getPlayerManager().getPlayerData(player).getCurrentMatchName());
                if(event.getItem().isSimilar(plugin.getMatchManager().chooseTeamItem())){

                    if(match == null){
                        player.sendMessage(CC.RED+"You are not in a match");
                        return;
                    }
                    new TeamSelector(match).openMenu(player);
                }else if(event.getItem().isSimilar(plugin.getMatchManager().leaveItem())){
                    if(match == null){
                        player.sendMessage(CC.RED+"You are not in a match");
                        return;
                    }
                    plugin.getMatchManager().removePlayer(match,player);
                }
                break;
            case FIGHTING:
                if(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR){
                    if(event.getItem().getType().equals(Material.ENDER_PEARL)){
                        if(playerData.getPearlCl()>0){
                            player.sendMessage(CC.RED+"You're on pearl cooldown");
                            event.setCancelled(true);
                        }else {
                            playerData.setPearlCl(12);
                        }
                    }
                }
                break;
            case SPECTATING:
                Match matchSpectating = plugin.getMatchManager().getMatch(plugin.getPlayerManager().getPlayerData(player).getCurrentMatchName());
                if(event.getItem().isSimilar(plugin.getMatchManager().teleportItem())){
                    if(matchSpectating == null){
                        player.sendMessage(CC.RED+"You are not in a match");
                        return;
                    }
                    new ChooseTeleportTeam(matchSpectating).openMenu(player);


                }
                break;
        }
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        event.setJoinMessage(null);
        plugin.getLobbyManager().sendToLobby(event.getPlayer());


    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        PlayerData playerData = plugin.getPlayerManager().getPlayerData(event.getPlayer());

        switch (playerData.getPlayerState()){
            case LOBBY:
                break;
            case WAITING:
                break;
            case FIGHTING:
                Match match = plugin.getMatchManager().getMatch(playerData.getCurrentMatchName());
                this.handleMove(event,match);

            case SPECTATING:
                break;
        }
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event){
        PlayerData playerData = plugin.getPlayerManager().getPlayerData(event.getPlayer());

        switch (playerData.getPlayerState()){
            case LOBBY:
            case WAITING:
                if(event.getPlayer().getGameMode() != GameMode.CREATIVE){
                    event.setCancelled(true);
                }
                break;
            case FIGHTING:
                Block block = event.getBlock();
                Match match = plugin.getMatchManager().getMatch(playerData.getCurrentMatchName());

                Team teamPlayer = plugin.getTeamManager().getByPlayer(event.getPlayer(), match);
                Team teamBlock = plugin.getTeamManager().getByLocation(block.getLocation(),match);

                if(teamBlock == null){
                    event.getPlayer().sendMessage(CC.RED+"You cannot place blocks here");
                    event.setCancelled(true);
                }else {
                    if(teamBlock != teamPlayer){
                        if(teamBlock.getDTR()<=0){
                            match.getPlacedBlocks().add(event.getBlock());
                        }else {
                            event.getPlayer().sendMessage(CC.RED+"You cannot place blocks here");
                            event.setCancelled(true);
                        }
                    }else {
                        match.getPlacedBlocks().add(event.getBlock());
                    }
                }
                break;
            case SPECTATING:
                event.setCancelled(true);
                event.getPlayer().sendMessage(CC.RED+"You cannot place blocks");
                break;
        }
    }

    @EventHandler
    public void onBreackBlock(BlockBreakEvent event){
        PlayerData playerData = plugin.getPlayerManager().getPlayerData(event.getPlayer());
        

        switch (playerData.getPlayerState()){
            case LOBBY:
            case WAITING:
                if(event.getPlayer().getGameMode() != GameMode.CREATIVE){
                    event.setCancelled(true);
                }
                break;
            case FIGHTING:
                Block block = event.getBlock();
                if(ItemUtils.isOreCanMine(block)){
                    event.setCancelled(true);
                    event.getPlayer().playSound(event.getPlayer().getLocation(),Sound.ORB_PICKUP,0.9F,1F);
                    Material previous = block.getType();
                    block.setType(Material.COBBLESTONE);
                    Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> block.setType(previous), 780L);
                    event.getPlayer().getInventory().addItem(new ItemStack(Objects.requireNonNull(ItemUtils.getTypeByOre(previous))));
                }else {
                    Match match = plugin.getMatchManager().getMatch(playerData.getCurrentMatchName());

                    Team teamPlayer = plugin.getTeamManager().getByPlayer(event.getPlayer(), match);
                    Team teamBlock = plugin.getTeamManager().getByLocation(block.getLocation(),match);

                    if(teamBlock == null){
                        event.getPlayer().sendMessage(CC.RED+"You cannot break blocks here");
                        event.setCancelled(true);
                    }else if (teamBlock != null){
                        if(teamBlock != teamPlayer){
                            if(match.getPlacedBlocks().contains(event.getBlock())){
                                if(teamBlock.getDTR()<=0){

                                }else {
                                    event.getPlayer().sendMessage(CC.RED+"You cannot break blocks here");
                                    event.setCancelled(true);
                                }
                            }else {
                                event.getPlayer().sendMessage(CC.RED+"You cannot break this block");
                            }
                        }else {
                            if(block.getType()!=Material.POTATO){
                                return;
                            }
                            if(((Crops)block.getState().getData()).getState().getData()!=CropState.RIPE.getData()){
                                return;
                            }

                            event.setCancelled(true);
                            block.setType(Material.AIR);
                            event.getPlayer().getInventory().addItem(new ItemStack(Material.BAKED_POTATO,ThreadLocalRandom.current().nextInt(1,2+1)));
                            event.getPlayer().playSound(event.getPlayer().getLocation(),Sound.DIG_GRASS,0.9F,1F);
                            Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> {
                                event.getBlock().setType(Material.POTATO);
                            }, 20L);
                        }
                    }
                }
                break;
            case SPECTATING:
                event.setCancelled(true);
                event.getPlayer().sendMessage(CC.RED+"You cannot break blocks");
                break;

        }
    }


    @EventHandler
    public void onPlayerEntityInteract(PlayerInteractEntityEvent event){
        PlayerData playerData = plugin.getPlayerManager().getPlayerData(event.getPlayer());
        event.setCancelled(true);
        switch (playerData.getPlayerState()){

            case LOBBY:
                break;
            case WAITING:
                break;
            case FIGHTING:
                if(event.getRightClicked().getType()== EntityType.VILLAGER){
                    event.setCancelled(true);
                    if(event.getRightClicked().getCustomName().equalsIgnoreCase(ChatColor.stripColor("Combat Shop"))){
                        new CombatMenu().openMenu(event.getPlayer());
                    }else if(event.getRightClicked().getCustomName().equalsIgnoreCase(ChatColor.stripColor("Build Shop"))){

                    }else if(event.getRightClicked().getCustomName().equalsIgnoreCase(ChatColor.stripColor("Sell Items"))){
                       if(PlayerInventoryUtils.hasResources(event.getPlayer())){
                           new SellMenu().openMenu(event.getPlayer());
                       }else {
                           event.getPlayer().sendMessage(CC.RED+"You don't have any resources");
                       }
                    }
                }
                break;
            case SPECTATING:
                event.getPlayer().sendMessage(CC.RED+"You cannot place blocks");
                break;
        }
    }



    private void handleMove(PlayerMoveEvent event, Match match) {

        Location from = event.getFrom();
        Location to = event.getTo();

        if (from.getBlockX() == to.getBlockX() && from.getBlockY() == to.getBlockY() && from.getBlockZ() == to.getBlockZ()) {
            return;
        }

        Player player = event.getPlayer();
        boolean cancelled = false;

        Team fromFaction = PBunkers.getInstance().getTeamManager().getByLocation(from,match);
        Team toFaction = PBunkers.getInstance().getTeamManager().getByLocation(to,match);

        if (fromFaction != toFaction) {
            MatchEnterClaimEvent calledEvent = new MatchEnterClaimEvent(player, from, to, fromFaction, toFaction);
            Bukkit.getPluginManager().callEvent(calledEvent);
            cancelled = calledEvent.isCancelled();
        }

        if (cancelled) {
            from.setX(from.getBlockX() + 0.5);
            from.setZ(from.getBlockZ() + 0.5);
            event.setTo(from);
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event){

        if(!(event.getEntity() instanceof Player)){
            return;
        }

        Player player = (Player) event.getEntity();

        PlayerData playerData = plugin.getPlayerManager().getPlayerData(player);
        if(playerData.getPlayerState()!=PlayerState.FIGHTING){
            event.setCancelled(true);
        }


    }

    @EventHandler
    public void onPlayerDamageEvent(EntityDamageEvent event){

        if(!(event.getEntity() instanceof Player)){
            return;
        }

        Player player = (Player) event.getEntity();
        PlayerData playerData = plugin.getPlayerManager().getPlayerData(player);
        if(playerData.getPlayerState()!=PlayerState.FIGHTING){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDamageByEntityEvent(EntityDamageByEntityEvent event){

        if(!(event.getEntity() instanceof Player) || (!(event.getDamager() instanceof Player))){
            return;
        }

        Player damaged = (Player) event.getEntity();
        Player damager = (Player) event.getDamager();
        PlayerData damagedData = plugin.getPlayerManager().getPlayerData(damaged);
        PlayerData damagerData = plugin.getPlayerManager().getPlayerData(damager);
        switch (damagerData.getPlayerState()){
            case LOBBY:
            case WAITING:
            case SPECTATING:
                event.setCancelled(true);
                break;
            case FIGHTING:

                    Match damagedMatch = plugin.getMatchManager().getMatch(damaged);
                    Match damagerMatch = plugin.getMatchManager().getMatch(damager);


                    if(damagedMatch == damagerMatch){
                        Team damagedTeam = plugin.getTeamManager().getByPlayer(damaged, damagedMatch);
                        Team damagerTeam = plugin.getTeamManager().getByPlayer(damager, damagerMatch);
                        if(damagedTeam  == damagerTeam){
                            damager.sendMessage(CC.RED+"You cannot hurt your teammates");
                            event.setCancelled(true);
                        }

                }

        }

    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event){

        event.setDeathMessage(null);
        Player player = event.getEntity();
        player.getInventory().clear();
        player.spigot().respawn();
        PlayerData playerData = plugin.getPlayerManager().getPlayerData(player);
        if(playerData.getPlayerState() == PlayerState.FIGHTING){
            Match match = plugin.getMatchManager().getMatch(playerData.getCurrentMatchName());
            Team team = plugin.getTeamManager().getByPlayer(player,match);

            if(team.getDTR()<0){
                plugin.getMatchManager().removePlayer(match,player,true);
                plugin.getTeamManager().getByPlayer(player,match).setDTR(team.getDTR()-1.0);
            }else {
                plugin.getTeamManager().getByPlayer(player,match).setDTR(team.getDTR()-1.0);
                player.teleport(plugin.getTeamManager().getSpawn(match,team));
            }
        }

    }



    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event){
        Player player = event.getPlayer();
        PlayerData playerData  = plugin.getPlayerManager().getPlayerData(player);
        event.setQuitMessage(null);
        if(playerData.getPlayerState() == PlayerState.FIGHTING){
            Match match = plugin.getMatchManager().getMatch(player);
            plugin.getMatchManager().removePlayer(match,player);
        }
    }


}
