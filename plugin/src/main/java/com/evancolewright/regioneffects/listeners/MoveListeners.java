package com.evancolewright.regioneffects.listeners;

import com.evancolewright.regioneffects.RegionEffects;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;


public class MoveListeners implements Listener
{
    private RegionEffects plugin;

    public MoveListeners(RegionEffects plugin)
    {
        this.plugin = plugin;
    }
    @EventHandler
    public void onMove(PlayerMoveEvent event)
    {
        final Player player = event.getPlayer();
        final Location location = player.getLocation();

        // Worldguard logic
        for (String s : plugin.getWorldGuard().getRegionsFromLocation(location))
        {
            player.sendMessage(s);
        }

    }
}
