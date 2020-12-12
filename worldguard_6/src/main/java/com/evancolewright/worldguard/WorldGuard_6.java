package com.evancolewright.worldguard;

import com.evancolewright.worldguard.IWorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;


public class WorldGuard_6 implements IWorldGuard
{

    public List<String> getRegionsFromLocation(Location location)
    {
        final WorldGuardPlugin worldGuardPlugin = WorldGuardPlugin.getPlugin(WorldGuardPlugin.class);
        RegionManager regionManager = worldGuardPlugin.getRegionManager(location.getWorld());

        ApplicableRegionSet regions = regionManager.getApplicableRegions(location);
        List<String> regionList = new ArrayList<>();

        for (ProtectedRegion region : regions)
        {
            regionList.add(region.getId());
        }

        return regionList;
    }
}
