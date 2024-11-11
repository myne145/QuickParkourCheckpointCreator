# Quick Parkour Checkpoint Creator

A Paper plugin, that automates setting up Parkour course checkpoints, with holograms above them, like this one:

![2024-11-09_13 30 01](https://github.com/user-attachments/assets/8f1ef821-7252-4988-9e86-e427715df970)

To do that, normally you'd need to create a checkpoint, position yourself properly, change some numbers in a command to create a hologram, and finally create it.


This plugin automates all of that. It works by executing a couple of commands at once:
- ***/pa create checkpoint <course_name>*** <br>to create a checkpoint on desired parkour course
- ***/dh create ... %parkour_current_checkpoint_hologram_<course_name>_<checkpoint_number>%*** <br>to create a hologram above the checkpoint, with its number (using Parkour's built-in support for PlaceholderAPI)
# Usage
The plugin's main command is ***/qpcc***:<br>
- ***/qpcc create <course_name>***<br>creates a checkpoint with a hologram, for a specified Parkour course
- ***/qpcc counter_set <checkpoint_number>***<br> sets the next checkpoint's number (use when you don't want to start with creating the first checkpoint)
- ***/qpcc counter_get***<br> prints the next checkpoints number<br><br>
To speed up the process even more, I recommend binding the ***create*** subcommand to a tool (e.g., using Essential's power tool feature).

# Requirements
These plugins need to be installed on the server:<br>
- [Parkour](https://www.spigotmc.org/resources/parkour.23685/) <br>
- [Decent holograms](https://www.spigotmc.org/resources/decentholograms-1-8-1-21-1-papi-support-no-dependencies.96927/)




