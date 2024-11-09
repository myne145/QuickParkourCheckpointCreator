package com.myne145.quickParkourCheckpointCreator;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
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

        if(strings.length == 0) {

            commandSourceStack.getSender().sendMessage("§bUse §7§o/qpcc help §r§b to list all the available commands");
        } else if(strings.length == 1 && strings[0].equals("help")) {
            commandSourceStack.getSender().sendMessage("§b§lQPCC HELP:§r\n" +
                    "§8- §b/qpcc create §o<course>§r §8- §7creates a checkpoint with a hologram, for specified Parkour course\n" +
                    "§8- §b/qpcc counter_set §o<number>§r §8- §7sets the next checkpoint's number (use when you don't want to start with creating the first checkpoint)\n" +
                    "§8- §b/gpcc counter_get §8- §7prints the next checkpoints number");

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
            commandSourceStack.getSender().sendMessage("§bSuccessfully executed all the commands!");

            //Counter_set subcommand
        } else if(strings.length == 2 && strings[0].equals("counter_set")) {
            try {
                Integer.parseInt(strings[1]);
            } catch (NumberFormatException e) {
                commandSourceStack.getSender().sendMessage("§bNot a number");
                return;
            }

            QuickParkourCheckpointCreator.checkpointCounter = Integer.parseInt(strings[1]);
            commandSourceStack.getSender().sendMessage("§bSuccessfully set the counter to §r§8" + QuickParkourCheckpointCreator.checkpointCounter);

            //Counter_get subcommand
        } else if(strings.length == 1 && strings[0].equals("counter_get")) {
            commandSourceStack.getSender().sendMessage("§bCurrent counter value is §r§8" + QuickParkourCheckpointCreator.checkpointCounter);
        } else {
            commandSourceStack.getSender().sendMessage("§bInvalid options");
        }
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
