package com.evancolewright.regioneffects.listeners;

import com.evancolewright.regioneffects.RegionEffectType;
import com.evancolewright.regioneffects.RegionEffects;
import com.evancolewright.regioneffects.RegionManager;
import com.evancolewright.regioneffects.RegionWrapper;
import com.evancolewright.regioneffects.events.RegionChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;


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
        final RegionManager regionManager = this.plugin.getRegionManager();
        final Location to = event.getTo();
        final Location location = event.getPlayer().getLocation();
        plugin.getRegionManager().applyEffects(event.getPlayer(), regionManager.convertList(plugin.getWorldGuard().getRegionsFromLocation(location)));
        final Location from = event.getFrom();

        List<String> toRegions = plugin.getWorldGuard().getRegionsFromLocation(to);
        List<String> fromRegions = plugin.getWorldGuard().getRegionsFromLocation(from);
//        event.getPlayer().sendMessage(toRegions.size() + " :  " + fromRegions.size());

        if (!Arrays.equals(toRegions.toArray(), fromRegions.toArray()))
        {
//            event.getPlayer().sendMessage("Regions change!");
//            event.getPlayer().sendMessage("Old regions: " + fromRegions.toString());
//            event.getPlayer().sendMessage("New regions: " + toRegions.toString());
            Bukkit.getServer().getPluginManager().callEvent(new RegionChangeEvent(event.getPlayer(), regionManager.convertList(fromRegions), regionManager.convertList(toRegions)));
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
        List<String> regions = plugin.getWorldGuard().getRegionsFromLocation(e.getPlayer().getLocation());
        plugin.getRegionManager().put(e.getPlayer().getUniqueId(), plugin.getRegionManager().convertList(regions));
    }

    @EventHandler
    public void onRegionChange(RegionChangeEvent event)
    {
        List<RegionWrapper> oldRegions = event.getOldApplicableRegions();
        List<RegionWrapper> newRegions = event.getNewApplicableRegions();
        plugin.getRegionManager().put(event.getPlayer().getUniqueId(), newRegions);
    }

}
