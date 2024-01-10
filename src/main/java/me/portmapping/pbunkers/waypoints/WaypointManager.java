package me.portmapping.pbunkers.waypoints;

import lombok.Getter;
import lombok.Setter;

import me.portmapping.pbunkers.PBunkers;
import me.portmapping.pbunkers.utils.Utils;
import me.portmapping.pbunkers.utils.chat.CC;
import me.portmapping.pbunkers.waypoints.listener.WaypointListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

@Getter
@Setter
public class WaypointManager  {

    private Waypoint kothWaypoint;
    private Waypoint hqWaypoint;
    private Waypoint focusWaypoint;
    private Waypoint rallyWaypoint;


    private final PBunkers instance;

    public WaypointManager(PBunkers instance) {
        this.instance = instance;


        this.kothWaypoint = new Waypoint(this,"KOTH",WaypointType.KOTH, "#BD3D21" ,true);
        this.hqWaypoint = new Waypoint(this,"HQ",WaypointType.HQ, "#75E319" ,true);
        this.focusWaypoint = new Waypoint(this,"FOCUS",WaypointType.FOCUS, "#FFC900",true);
        this.rallyWaypoint = new Waypoint(this,"RALLY",WaypointType.RALLY_POINT, "#0FD5D5",true);



        this.loadWorlds();
        new WaypointListener(this);
    }






    public void enableStaffModules(Player player) {
        getInstance().getClientHook().giveStaffModules(player);
    }

    public void disableStaffModules(Player player) {
        getInstance().getClientHook().disableStaffModules(player);
    }

    public void loadWorlds() {
        Bukkit.getScheduler().runTaskLater(getInstance(), () -> {
            for (World world : Bukkit.getServer().getWorlds()) {
                world.setWeatherDuration(Integer.MAX_VALUE);
                world.setThundering(false);
                world.setStorm(false);
                world.setGameRuleValue("mobGriefing", "false");

                if (Utils.isModernVer()) {
                    world.setGameRuleValue("maxEntityCramming", "0");
                    world.setGameRuleValue("doTraderSpawning", "false"); // disable the wandering traders thing
                    world.setGameRuleValue("doPatrolSpawning", "false");
                    world.setGameRuleValue("doInsomnia", "false"); // phantoms
                    world.setGameRuleValue("disableRaids", "true");
                }
            }
        }, 20 * 10L);
    }
}