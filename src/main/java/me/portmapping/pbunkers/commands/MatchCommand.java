package me.portmapping.pbunkers.commands;

import me.portmapping.pbunkers.PBunkers;
import me.portmapping.pbunkers.game.arena.Arena;
import me.portmapping.pbunkers.game.match.Match;
import me.portmapping.pbunkers.utils.chat.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class MatchCommand implements CommandExecutor {

    private PBunkers plugin = PBunkers.getInstance();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(!(commandSender instanceof Player)){
            return false;
        }

        Player player = (Player) commandSender;

        if(args.length==0){
            this.sendMainHelp(player);
            return false;
        }

        if(args.length==1){
            if(args[0].equalsIgnoreCase("list")){
                this.sendMatchlist(player);
            }
            return false;
        }

        if(args.length==2){
            if(args[0].equalsIgnoreCase("create")){
                Arena arena = plugin.getArenaManager().getArena(args[1]);
                if(arena==null){
                    player.sendMessage(CC.RED+"This arena does not exists");
                    return false;

















                }

                if(plugin.getMatchManager().getMatch(arena.getName())!=null){
                    player.sendMessage(CC.RED+"This match already exists");
                    return false;
                }

                player.sendMessage(CC.GREEN+"You made the match for the arena "+args[1]);
                plugin.getMatchManager().createMatch(arena);

            }else if(args[0].equalsIgnoreCase("join")) {
                Match match = plugin.getMatchManager().getMatch(args[1]);
                if (match == null) {
                    player.sendMessage(CC.RED + "This match does not exists");
                    return false;
                }
                plugin.getMatchManager().joinPlayer(match, player);
            }
        }
        return false;
    }

    public void sendMainHelp(Player player){
            player.sendMessage(CC.translate("&7&m------------------------"));
            player.sendMessage(CC.translate("&c&lMatch Commands"));
            player.sendMessage(CC.translate(" "));
            player.sendMessage(CC.translate("&f/match create <arena>"));
            player.sendMessage(CC.translate("&f/arena delete "));
            player.sendMessage(CC.translate("&7&m------------------------"));
    }


    public void sendMatchlist(Player player){
        player.sendMessage(CC.translate("&7&m------------------------"));
        player.sendMessage(CC.translate("&c&lMatch List"));
        player.sendMessage(CC.translate(" "));
        for (Map.Entry<String, Match> entry : plugin.getMatchManager().matchMap.entrySet()){
            Match match = entry.getValue();
            player.sendMessage(CC.translate("&e"+match.getName()+" &7- &aEnabled"));
        }
        player.sendMessage(CC.translate("&7&m------------------------"));
    }

}
