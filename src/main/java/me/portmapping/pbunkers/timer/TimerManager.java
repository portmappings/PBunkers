package me.portmapping.pbunkers.timer;

import lombok.Getter;
import me.portmapping.pbunkers.PBunkers;
import me.portmapping.pbunkers.timer.type.ProtectionTimer;
import me.portmapping.pbunkers.timer.type.TeleportTimer;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.LinkedHashSet;
import java.util.Set;

public class TimerManager {
    @Getter private final PBunkers plugin;

    @Getter private ProtectionTimer protectionTimer;
    @Getter private TeleportTimer teleportTimer;

    @Getter private final Set<Timer> timers = new LinkedHashSet<>();

    public TimerManager(PBunkers plugin) {
        this.plugin = plugin;

        registerTimer(teleportTimer = new TeleportTimer(plugin));
        registerTimer(protectionTimer = new ProtectionTimer());
    }

    public void registerTimer(Timer timer) {
        timers.add(timer);
        if (timer instanceof Listener) {
            Bukkit.getPluginManager().registerEvents((Listener) timer, plugin);
        }
    }

    public void unregisterTimer(Timer timer) {
        timers.remove(timer);
    }
}