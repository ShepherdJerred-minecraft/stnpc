
package com.shepherdjerred.stnpc.traits;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.util.DataKey;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;


public class stNPCTrait extends Trait {

    // Create variables to store properties
    public String sound;
    public String command;

    public stNPCTrait() {
        super("stnpc");
    }

    // TODO Load defaults from config
    // Method to load values/defaults
    public void load(DataKey key) {
        sound = key.getString("sound", "");
        command = key.getString("command", "");
    }

    // Method to save values
    public void save(DataKey key) {
        key.setString("sound", sound);
        key.setString("command", command);
    }

    // TODO Add cooldown to sounds
    // Handle right-clicks
    @EventHandler
    public void click(NPCRightClickEvent event) {

        if (event.getNPC() == this.getNPC()) {
            NPC npc = event.getNPC();
            Player player = event.getClicker();
            player.playSound(npc.getStoredLocation(), sound, 1.0F, 1.0F);

            if (!(command.equals(""))) {

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()));

            }
        }
    }
}