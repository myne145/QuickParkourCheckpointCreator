package com.myne145.quickParkourCheckpointCreator;

import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class QuickParkourCheckpointCreator extends JavaPlugin {
    public static int checkpointCounter = 1;
    public static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            Commands commands = event.registrar();
            commands.register("qpcc", "QuickParkourCheckpointCreator main command", new MainCommand());
            commands.register("quickparkourcheckpointcreator", "QuickParkourCheckpointCreator main command", new MainCommand());
        });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
