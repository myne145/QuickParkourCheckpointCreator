package com.myne145.quickParkourCheckpointCreator;

import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedHashMap;
import java.util.UUID;

public final class Qpcc extends JavaPlugin implements Listener {
    public static LinkedHashMap<UUID, Integer> checkpointCounters = new LinkedHashMap<>();
    public static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            Commands commands = event.registrar();
            commands.register("qpcc", "QuickParkourCheckpointCreator main command", new MainCommand());
        });
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("Successfully enabled QuickParkourCheckpointCreator");
    }

    @Override
    public void onDisable() {
        getLogger().info("Successfully disabled QuickParkourCheckpointCreator");
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        checkpointCounters.remove(event.getPlayer().getUniqueId());
    }

}
