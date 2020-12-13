package com.evancolewright.regioneffects;

import com.evancolewright.regioneffects.listeners.MoveListeners;
import com.evancolewright.worldguard.IWorldGuard;
import com.evancolewright.worldguard.WorldGuard_6;
import com.evancolewright.worldguard.WorldGuard_7;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginManager;

public class RegionEffects extends JavaPlugin implements CommandExecutor
{
    private final String PREFIX = "&eRegionEffects &7> ";
    private final ConsoleCommandSender LOG = getServer().getConsoleSender();
    private final PluginManager pluginManager = getServer().getPluginManager();

    @Getter
    private IWorldGuard worldGuard;

    @Getter
    private RegionManager regionManager;


    @Override
    public void onEnable()
    {
        log(PREFIX + "Beginning loading process......");
        this.getCommand("re").setExecutor(this);
        if (checkWorldGuard())
        {
            this.saveDefaultConfig();
            this.pluginManager.registerEvents(new MoveListeners(this), this);
        } else
        {
            log(PREFIX + "&cError hooking into WorldGuard.  Please install both WorldEdit and WorldGuard prior to using this plugin.");
            this.pluginManager.disablePlugin(this);
        }
        log(PREFIX + "&aSuccessfully loaded regions.");
        this.regionManager = new RegionManager(this);
        this.regionManager.loadRegions();
        log(PREFIX + "&aAll loading processes complete.  Enjoy!  For support, please PM me on SpigotMC.");
    }

    @Override
    public void onDisable()
    {
        // disable logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (command.getName().equalsIgnoreCase("re"))
        {
            sender.sendMessage(regionManager.getPlayerRegionsMap().size() + "");
            return true;
        }
        return false;
    }

    private boolean checkWorldGuard()
    {
        if (this.pluginManager.getPlugin("WorldGuard") != null)
        {
            String wgVersion = this.pluginManager.getPlugin("WorldGuard").getDescription().getVersion();

            if (this.pluginManager.getPlugin("WorldEdit") == null)
            {
                log(PREFIX + "&cWithout WorldEdit, WorldGuard will not function.  Please install both prior to using this plugin!");
                return false;
            }
            if (wgVersion.startsWith("7"))
            {
                this.worldGuard = new WorldGuard_7();
                log(PREFIX + "&aHooked into WorldGuard successfully. (Version: 7+)");
                return true;
            } else if (wgVersion.startsWith("6"))
            {
                this.worldGuard = new WorldGuard_6();
                log(PREFIX + "&aHooked into WorldGuard successfully. (Version: 6+)");
                return true;
            } else
            {
                log(PREFIX + "&cPlease install either WorldGuard 6.x.x or WorldGuard 7.x.x to use this plugin!");
                this.pluginManager.disablePlugin(this);
            }
        } else
        {

        }
        return false;
    }

    private void log(String message)
    {
        LOG.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
