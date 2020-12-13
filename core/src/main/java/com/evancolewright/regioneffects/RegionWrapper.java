package com.evancolewright.regioneffects;

import lombok.Getter;

import java.util.List;

@Getter
public class RegionWrapper
{
    private final String world;
    private final String regionName;
    private final List<RegionEffectType> effects;

    public RegionWrapper(String world, String regionName, List<RegionEffectType> effects)
    {
        this.world = world;
        this.regionName = regionName;
        this.effects = effects;
    }

    public boolean hasEffect(RegionEffectType effectType)
    {
        return effects.contains(effectType);
    }
}
