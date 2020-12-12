package com.evancolewright.worldguard;

import org.bukkit.Location;

import java.util.List;

public interface IWorldGuard
{
    public List<String> getRegionsFromLocation(Location location);
}

