package com.evancolewright.regioneffects;

import com.evancolewright.regioneffects.listeners.MoveListeners;
import com.evancolewright.worldguard.IWorldGuard;
import com.evancolewright.worldguard.WorldGuard_6;
import com.evancolewright.worldguard.WorldGuard_7;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginManager;

public class RegionEffects extends JavaPlugin
{

    private final String PREFIX = "&eRegionEffects &7> ";
    private final ConsoleCommandSender LOG = getServer().getConsoleSender();
    private final PluginManager pluginManager = getServer().getPluginManager();

    private IWorldGuard worldGuard;

    @Override
    public void onEnable()
    {
        log(PREFIX + "Beginning loading process......");
        if (checkWorldGuard())
        {
            this.saveDefaultConfig();
            this.pluginManager.registerEvents(new MoveListeners(this), this);
            this.loadRegions();
        }
    }

    @Override
    public void onDisable()
    {

    }

    private boolean checkWorldGuard()
    {
        if (this.pluginManager.getPlugin("WorldGuard") != null)
        {
            String wgVersion = this.pluginManager.getPlugin("WorldGuard").getDescription().getVersion();
            if (wgVersion.startsWith("7") && this.pluginManager.getPlugin("WorldEdit") != null)
            {
                this.worldGuard = new WorldGuard_7();
                log(PREFIX + "&aHooked into WorldGuard successfully. (Version: 7+)");
                return true;
            } else if (wgVersion.startsWith("6") && this.pluginManager.getPlugin("WorldEdit") != null)
            {
                this.worldGuard = new WorldGuard_6();
                log(PREFIX + "&aHooked into WorldGuard successfully. (Version: 6+)");
                return true;
            }
            log(PREFIX + "&cFailed to hook into WorldGuard (Version not supported)");
            return false;
        }
        log(PREFIX + "&cError hooking into WorldGuard.  Please install prior to using this plugin.");
        this.pluginManager.disablePlugin(this);

        return false;
    }

    private void loadRegions()
    {
        for (String world : getConfig().getConfigurationSection("regions").getKeys(false))
        {
            for (String region : getConfig().getConfigurationSection("regions." + world).getKeys(false))
            {
                System.out.print(region);
            }
        }
    }

    public IWorldGuard getWorldGuard()
    {
        return this.worldGuard;
    }

    private void log(String message)
    {
        LOG.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
