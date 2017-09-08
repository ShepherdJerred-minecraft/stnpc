
package com.shepherdjerred.stnpc;

import com.shepherdjerred.stnpc.commands.MainCommand;
import com.shepherdjerred.stnpc.files.Config;
import com.shepherdjerred.stnpc.metrics.MetricsLite;
import com.shepherdjerred.stnpc.traits.stNPCTrait;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.trait.TraitInfo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Level;


public class Main extends JavaPlugin {

    // Provide instance of Main class
    private static Main instance;

    public Main() {
        instance = this;
    }

    public static Main getInstance() {
        return instance;
    }

    public void onEnable() {

        this.saveDefaultConfig();
        Config.getInstance().loadFiles();

        // Disable the plugin if Citizens isn't found
        if (getServer().getPluginManager().getPlugin("Citizens") == null || !getServer().getPluginManager().getPlugin("Citizens").isEnabled()) {
            getLogger().log(Level.SEVERE, "Citizens is not found or not enabled");

            Bukkit.getPluginManager().disablePlugin(this);
        }

        // Register the trait
        CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(stNPCTrait.class).withName("stnpc"));

        // Register commands
        this.getCommand("stn").setExecutor(new MainCommand());

        // Setup Metrics
        try {
            MetricsLite metrics = new MetricsLite(this);
            metrics.start();
        } catch (IOException e) {

        }
    }

    public String getMessagesString(String input) {
        return ChatColor.translateAlternateColorCodes('&', Config.getInstance().messages.getString(input));
    }
}