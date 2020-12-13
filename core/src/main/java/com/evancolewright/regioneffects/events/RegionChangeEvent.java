package com.evancolewright.regioneffects.events;

import com.evancolewright.regioneffects.RegionWrapper;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;

/**
 *
 * Custom Event that is called when a player's regions(s) change
 *
 */

@Getter
public class RegionChangeEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final List<RegionWrapper> oldApplicableRegions;
    private final List<RegionWrapper> newApplicableRegions;
    private boolean cancelled;

    public RegionChangeEvent(Player player, List<RegionWrapper> oldApplicableRegions, List<RegionWrapper> newApplicableRegions)
    {
        this.player = player;
        this.newApplicableRegions = newApplicableRegions;
        this.oldApplicableRegions = oldApplicableRegions;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }
}
