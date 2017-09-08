
package com.shepherdjerred.stnpc.commands;

import com.shepherdjerred.stnpc.Main;
import com.shepherdjerred.stnpc.files.Config;
import com.shepherdjerred.stnpc.traits.stNPCTrait;
import net.citizensnpcs.Citizens;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class MainCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("stn")) { // Start stt command
            if (sender instanceof Player) { // Check if the sender is a player

                Player player = (Player) sender;

                if (args.length > 0) { // Check if the argument length is greater than 0

                    if (args[0].equalsIgnoreCase("reload")) { // Reload Command
                        if (player.hasPermission("stNPC.reload")) { // Check if player has permission to use this command

                            // Reload the config files
                            Main.getInstance().reloadConfig();
                            Config.getInstance().loadFiles();

                            String prefix = Main.getInstance().getMessagesString("prefix.server");

                            sender.sendMessage(prefix + "�aConfig reloaded");

                            return true;

                        } else {

                            // Send an error message
                            String prefix = Main.getInstance().getMessagesString("prefix.server");
                            String noPerms = Main.getInstance().getMessagesString("messages.no-perms");

                            sender.sendMessage(prefix + noPerms);

                            return true;

                        }

                    } else if (args[0].equalsIgnoreCase("sound")) { // Trait command
                        if (player.hasPermission("stNPC.setsound")) { // Check if player has permission to use this command

                            if (args.length < 2) { // Check if the argument length is less than 1

                                // Send an error message
                                String prefix = Main.getInstance().getMessagesString("prefix.server");
                                String noArgs = Main.getInstance().getMessagesString("messages.no-args").replaceAll("%args%", "<sound>");

                                sender.sendMessage(prefix + noArgs);

                                return true;

                            }

                            // Create the npcid varable
                            int npcid;

                            NPC npc = ((Citizens) Main.getInstance().getServer().getPluginManager().getPlugin("Citizens")).getNPCSelector().getSelected(sender);

                            // If there is no NPC selected
                            if (npc != null) {

                                npcid = npc.getId();

                            } else {

                                // Send an error message
                                String prefix = Main.getInstance().getMessagesString("prefix.server");
                                String noNpc = Main.getInstance().getMessagesString("messages.no-npc");

                                sender.sendMessage(prefix + noNpc);

                                return true;

                            }

                            npc = CitizensAPI.getNPCRegistry().getById(npcid);

                            // If the selected NPC isn't found or doesn't exist
                            if (npc == null) {

                                // Send an error message
                                String prefix = Main.getInstance().getMessagesString("prefix.server");
                                String noNpc = Main.getInstance().getMessagesString("messages.invalid-npc");

                                sender.sendMessage(prefix + noNpc);

                                return true;

                            }

                            // If the selected NPC doesn't have the stNPC trait
                            if (!npc.hasTrait(stNPCTrait.class)) {

                                // Send an error message
                                String prefix = Main.getInstance().getMessagesString("prefix.server");
                                String noNpc = Main.getInstance().getMessagesString("messages.no-trait").replace("%npc%", npc.getName() + " (" + npc.getId() + ")");

                                sender.sendMessage(prefix + noNpc);

                                return true;

                            } else {

                                // Get an instance of the NPC trait, set the sound
                                stNPCTrait trait = npc.getTrait(stNPCTrait.class);

                                trait.sound = args[1];

                                String prefix = Main.getInstance().getMessagesString("prefix.server");
                                String soundSuccess = Main.getInstance().getMessagesString("messages.sound-success").replace("%npc%", npc.getName() + " (" + npc.getId() + ")")
                                        .replace("%sound%", args[1]);

                                sender.sendMessage(prefix + soundSuccess);

                                return true;

                            }

                        }
                    } else if (args[0].equalsIgnoreCase("command")) { // Trait command
                        if (player.hasPermission("stNPC.setcommand")) { // Check if player has permission to use this command

                            if (args.length < 2) { // Check if the argument length is less than 1

                                // Send an error message
                                String prefix = Main.getInstance().getMessagesString("prefix.server");
                                String noArgs = Main.getInstance().getMessagesString("messages.no-args").replaceAll("%args%", "<command>");

                                sender.sendMessage(prefix + noArgs);

                                return true;

                            }

                            // Create the npcid varable
                            int npcid;

                            NPC npc = ((Citizens) Main.getInstance().getServer().getPluginManager().getPlugin("Citizens")).getNPCSelector().getSelected(sender);

                            // If there is no NPC selected
                            if (npc != null) {

                                npcid = npc.getId();

                            } else {

                                // Send an error message
                                String prefix = Main.getInstance().getMessagesString("prefix.server");
                                String noNpc = Main.getInstance().getMessagesString("messages.no-npc");

                                sender.sendMessage(prefix + noNpc);

                                return true;

                            }

                            npc = CitizensAPI.getNPCRegistry().getById(npcid);

                            // If the selected NPC isn't found or doesn't exist
                            if (npc == null) {

                                // Send an error message
                                String prefix = Main.getInstance().getMessagesString("prefix.server");
                                String noNpc = Main.getInstance().getMessagesString("messages.invalid-npc");

                                sender.sendMessage(prefix + noNpc);

                                return true;

                            }

                            // If the selected NPC doesn't have the stNPC trait
                            if (!npc.hasTrait(stNPCTrait.class)) {

                                // Send an error message
                                String prefix = Main.getInstance().getMessagesString("prefix.server");
                                String noNpc = Main.getInstance().getMessagesString("messages.no-trait").replace("%npc%", npc.getName() + " (" + npc.getId() + ")");

                                sender.sendMessage(prefix + noNpc);

                                return true;

                            } else {

                                // Get an instance of the NPC trait, set the command
                                stNPCTrait trait = npc.getTrait(stNPCTrait.class);

                                StringBuilder stringBuilder = new StringBuilder();

                                for (int i = 1; i < args.length; i++) {
                                    stringBuilder.append(args[i]).append(" ");
                                }

                                String fullCommand = stringBuilder.toString().trim();

                                trait.command = fullCommand;

                                String prefix = Main.getInstance().getMessagesString("prefix.server");
                                String commandSuccess = Main.getInstance().getMessagesString("messages.command-success")
                                        .replace("%npc%", npc.getName() + " (" + npc.getId() + ")").replace("%command%", fullCommand);

                                sender.sendMessage(prefix + commandSuccess);

                                return true;

                            }

                        }
                    } else { // If the argument doesn't match any of the options

                        // Send an error message
                        String prefix = Main.getInstance().getMessagesString("prefix.server");
                        String invalidArg = Main.getInstance().getMessagesString("messages.invalid-arg").replaceAll("%arg%", args[0]).replaceAll("%args%", "<sound|command>");

                        sender.sendMessage(prefix + invalidArg);

                        return true;

                    }
                } else { // If there are no arguments

                    // Send an error message
                    String prefix = Main.getInstance().getMessagesString("prefix.server");
                    String noArgs = Main.getInstance().getMessagesString("messages.no-args").replaceAll("%args%", "<sound|command>");

                    sender.sendMessage(prefix + noArgs);

                    return true;
                }
            } else { // If the sender isn't a player, IE console

                if (args.length > 0) { // Check if the argument length is greater than 0
                    if (args[0].equalsIgnoreCase("reload")) { // Reload Command

                        // Reload the config, send a message
                        Main.getInstance().reloadConfig();
                        Config.getInstance().loadFiles();

                        Main.getInstance().getLogger().info("Config reloaded");

                        return true;

                    } else { // If the argument doesn't match any of the options

                        // Send an error message
                        String invalidArg = Main.getInstance().getMessagesString("messages.invalid-arg").replaceAll("%arg%", args[0]).replaceAll("%args%", "<reload>");
                        Main.getInstance().getLogger().info(invalidArg);

                        return true;

                    }
                } else { // If there are no arguments

                    // Send an error message
                    String noArgs = Main.getInstance().getMessagesString("messages.no-args").replaceAll("%args%", "<reload>");
                    Main.getInstance().getLogger().info(noArgs);

                    return true;

                }
            }

        }
        return false;
    }
}
