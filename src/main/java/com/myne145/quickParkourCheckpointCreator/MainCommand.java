package com.myne145.quickParkourCheckpointCreator;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class MainCommand implements BasicCommand {
    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] strings) {
        //Help message
        if(strings.length == 0) {
            commandSourceStack.getSender().sendMessage("Command options:\n" +
                    "/qpcc create <course> - creates a checkpoint in a parkour course\n" +
                    "/qpcc counter_set <number> - sets the checkpoint counter used in checkpoint & hologram names\n" +
                    "/gpcc counter_get - prints the current counter value");

            //Create subcommand
        } else if(strings.length == 2 && strings[0].equals("create")) {
            if (!(commandSourceStack.getSender() instanceof Player)) {
                commandSourceStack.getSender().sendMessage("This command can only be used by players");
                return;
            }

            String courseName = strings[1];


            Player player = (Player) commandSourceStack.getSender();
            GameMode prevGamemode = player.getGameMode();

            Bukkit.dispatchCommand(commandSourceStack.getSender(), "pa create checkpoint " + courseName);
            player.setGameMode(GameMode.SPECTATOR);
            player.teleport(player.getLocation().add(0, 1.5, 0));

            Bukkit.dispatchCommand(commandSourceStack.getSender(),
                    "dh create " + courseName + "_checkpoint_1 %parkour_current_checkpoint_hologram_" +
                            courseName + "_" + QuickParkourCheckpointCreator.checkpointCounter + "%"
            );

            //Tp player back where he was
            player.teleport(player.getLocation().add(0, -1.5, 0));
            player.setGameMode(prevGamemode);

            QuickParkourCheckpointCreator.checkpointCounter++;
            commandSourceStack.getSender().sendMessage("Successfully executed all the commands");

            //Counter_set subcommand
        } else if(strings.length == 2 && strings[0].equals("counter_set")) {
            try {
                Integer.parseInt(strings[1]);
            } catch (NumberFormatException e) {
                commandSourceStack.getSender().sendMessage("Not a number");
                return;
            }

            QuickParkourCheckpointCreator.checkpointCounter = Integer.parseInt(strings[1]);
            commandSourceStack.getSender().sendMessage("Successfully set the counter to " + QuickParkourCheckpointCreator.checkpointCounter);

            //Counter_get subcommand
        } else if(strings.length == 1 && strings[0].equals("counter_get")) {
            commandSourceStack.getSender().sendMessage("Current counter value is " + QuickParkourCheckpointCreator.checkpointCounter);
        }


        commandSourceStack.getSender().sendMessage("Invalid options");

    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        if(args.length >= 1 && args.length <= 2)
            return new ArrayList<>(Arrays.asList("create", "counter_set", "counter_get"));
        else
            return new ArrayList<>();

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
