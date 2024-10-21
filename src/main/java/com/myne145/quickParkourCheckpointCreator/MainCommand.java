package com.myne145.quickParkourCheckpointCreator;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;
import org.jspecify.annotations.Nullable;

import java.util.Collection;

public class MainCommand implements BasicCommand {
    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] strings) {
        if(strings.length == 0) {
            commandSourceStack.getSender().sendMessage("Command options:\n" +
                    "/qpcc create <course> - creates a checkpoint in a parkour course\n" +
                    "/qpcc counter_set <number> - sets the checkpoint counter used in checkpoint & hologram names");
            return;
        }
        if(strings.length == 2 && strings[0].equals("create")) {
            String courseName = strings[1];
            Bukkit.dispatchCommand(commandSourceStack.getSender(), "pa create checkpoint " + courseName);
            Bukkit.dispatchCommand(commandSourceStack.getSender(), "gamemode spectator");
            Bukkit.dispatchCommand(commandSourceStack.getSender(), "tp @p ~ ~1.5 ~");

            new BukkitRunnable() {

                @Override
                public void run() {
                    commandSourceStack.getSender().sendMessage("fsdkjkldsfpkjsdf");
                }
            }.runTaskLater(QuickParkourCheckpointCreator.plugin, 20);

            Bukkit.dispatchCommand(commandSourceStack.getSender(),
                    "dh create " + courseName + "_checkpoint_1 %parkour_current_checkpoint_hologram_" +
                            courseName + "_ " + QuickParkourCheckpointCreator.checkpointCounter + "%"
            );
            QuickParkourCheckpointCreator.checkpointCounter++;
            commandSourceStack.getSender().sendMessage("Successfully executed all the commands");
            return;
        }

        if(strings.length == 2 && strings[0].equals("counter_set")) {
            try {
                Integer.parseInt(strings[1]);
            } catch (NumberFormatException e) {
                commandSourceStack.getSender().sendMessage("Not a number");
                return;
            }

            QuickParkourCheckpointCreator.checkpointCounter = Integer.parseInt(strings[1]);
            commandSourceStack.getSender().sendMessage("Successfully set the counter to " + QuickParkourCheckpointCreator.checkpointCounter);
            return;
        }
        commandSourceStack.getSender().sendMessage("Invalid options");

    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        return BasicCommand.super.suggest(commandSourceStack, args);
    }

    @Override
    public boolean canUse(CommandSender sender) {
        return BasicCommand.super.canUse(sender);
    }

    @Override
    public @Nullable String permission() {
        return BasicCommand.super.permission();
    }
}
