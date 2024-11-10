package com.myne145.quickParkourCheckpointCreator;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static com.myne145.quickParkourCheckpointCreator.Qpcc.checkpointCounters
        ;
public class MainCommand implements BasicCommand {

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] strings) {
        //Fix for executing the command from the console
        if(commandSourceStack.getExecutor() == null || commandSourceStack.getSender() == null) {
            commandSourceStack.getSender().sendMessage("This command can only be used by players");
            return;
        }

        //Initialize the player
        if(!checkpointCounters.containsKey(commandSourceStack.getExecutor().getUniqueId())) {
            checkpointCounters.put(commandSourceStack.getExecutor().getUniqueId(), 1);
        }

        if(strings.length == 0) {
            commandSourceStack.getSender().sendMessage("§bUse §7§o/qpcc help §r§b to list all the available commands");
        } else if(strings.length == 1 && strings[0].equals("help")) {
            commandSourceStack.getSender().sendMessage("§b§lQPCC HELP:§r\n" +
                    "§8- §b/qpcc create §o<course>§r §8- §7creates a checkpoint with a hologram, for specified Parkour course\n" +
                    "§8- §b/qpcc counter_set §o<number>§r §8- §7sets the next checkpoint's number (use when you don't want to start with creating the first checkpoint)\n" +
                    "§8- §b/gpcc counter_get §8- §7prints the next checkpoints number");

            //Create subcommand
        } else if(strings.length == 2 && strings[0].equals("create")) {
//            if (!(commandSourceStack.getSender() instanceof Player)) {
//                commandSourceStack.getSender().sendMessage("This command can only be used by players");
//                return;
//            }

            String courseName = strings[1];


            Player player = (Player) commandSourceStack.getSender();
            GameMode prevGamemode = player.getGameMode();

            Bukkit.dispatchCommand(commandSourceStack.getSender(), "pa create checkpoint " + courseName);

            //A dumb way to position the hologram
            player.setGameMode(GameMode.SPECTATOR);
            player.teleport(player.getLocation().add(0, 1.5, 0));

            int currentCheckpoint = checkpointCounters.get(commandSourceStack.getExecutor().getUniqueId());

            //Create hologram
            Bukkit.dispatchCommand(commandSourceStack.getSender(),
                    "dh create " + courseName + "_checkpoint_" + currentCheckpoint + " %parkour_current_checkpoint_hologram_" +
                            courseName + "_" + currentCheckpoint + "%"
            );

            //Center the hologram
            Bukkit.dispatchCommand(commandSourceStack.getSender(),
                    "dh center " + courseName + "_checkpoint_" + currentCheckpoint);

            //Tp player back where he was
            player.teleport(player.getLocation().add(0, -1.5, 0));
            player.setGameMode(prevGamemode);

            checkpointCounters.put(player.getUniqueId(), checkpointCounters.get(player.getUniqueId()) + 1);
//            Qpcc.checkpointCounter++;
            commandSourceStack.getSender().sendMessage("§bSuccessfully executed all the commands!");

        //Counter_set subcommand
        } else if(strings.length == 2 && strings[0].equals("counter_set")) {
            try {
                Integer.parseInt(strings[1]);
            } catch (NumberFormatException e) {
                commandSourceStack.getSender().sendMessage("§bNot a number");
                return;
            }

            checkpointCounters.put(commandSourceStack.getExecutor().getUniqueId(), Integer.parseInt(strings[1]));

//            Qpcc.checkpointCounter = Integer.parseInt(strings[1]);
            commandSourceStack.getSender().sendMessage("§bSuccessfully set the counter to §r§8" +
                    checkpointCounters.get(commandSourceStack.getExecutor().getUniqueId()));

            //Counter_get subcommand
        } else if(strings.length == 1 && strings[0].equals("counter_get")) {
            commandSourceStack.getSender().sendMessage("§bCurrent counter value is §r§8" +
                    checkpointCounters.get(commandSourceStack.getExecutor().getUniqueId()));
        } else {
            commandSourceStack.getSender().sendMessage("§bInvalid options");
        }
    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        if(!commandSourceStack.getSender().hasPermission("qpcc.*")) {
            return new ArrayList<>(); //prevent autocompletion if user doesnt have the permission
        }

        if(args.length == 0 || args.length == 1)
            return new ArrayList<>(Arrays.asList("create", "counter_set", "counter_get", "help"));
        else
            return new ArrayList<>();

    }

    @Override
    public boolean canUse(CommandSender sender) {
        return sender.hasPermission("qpcc.*");
    }

    @Override
    public @Nullable String permission() {
        return "qpcc.*";
    }
}
