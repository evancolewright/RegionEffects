package com.evancolewright.worldguard;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionQuery;

import java.util.ArrayList;
import java.util.List;

public class WorldGuard_7 implements IWorldGuard
{

    @Override
    public List<String> getRegionsFromLocation(org.bukkit.Location location)
    {
        Location toWGLocation = BukkitAdapter.adapt(location);
        RegionQuery query = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();

        ApplicableRegionSet regions = query.getApplicableRegions(toWGLocation);
        List<String> regionList = new ArrayList<>();

        for (ProtectedRegion region : regions)
        {
            regionList.add(region.getId());
        }
        return regionList;
    }
}
