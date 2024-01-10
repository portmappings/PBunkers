package me.portmapping.pbunkers.utils.extra;

import me.portmapping.pbunkers.PBunkers;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Copyright (c) 2023. Keano
 * Use or redistribution of source or file is
 * only permitted if given explicit permission.
 */
public class CooldownClearTask extends BukkitRunnable {

    private final PBunkers instance;

    public CooldownClearTask(PBunkers instance) {
        this.instance = instance;
        this.runTaskTimer(instance, 0L, (20 * 60) * 5);
    }

    @Override
    public void run() {
        for (Cooldown cooldown : instance.getCooldowns()) {
            cooldown.clean();
        }
    }
}