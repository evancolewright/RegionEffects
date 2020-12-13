package com.evancolewright.regioneffects;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public final class RegionManager
{
    private final RegionEffects plugin;
    private final FileConfiguration config;
    private Set<RegionWrapper> regions = new HashSet<>();

    @Getter
    private Map<UUID, List<RegionWrapper>> playerRegionsMap = new HashMap<>();

    protected RegionManager(RegionEffects plugin)
    {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    protected void loadRegions()
    {
        for (String world : config.getConfigurationSection("regions").getKeys(false))
        {
            for (String region : config.getConfigurationSection("regions." + world).getKeys(false))
            {
                List<RegionEffectType> effects = this.parseEffects(config.getStringList("regions." + world + "." + region + ".effects"));

                this.regions.add(new RegionWrapper(world, region, effects));
            }
        }
    }

    public void put(UUID playerUUID, List<RegionWrapper> regions)
    {
        playerRegionsMap.put(playerUUID, regions);
    }

    public void applyEffects(Player player, List<RegionWrapper> regions)
    {
        for (RegionWrapper region : regions)
        {
            for (RegionEffectType effect : region.getEffects())
            {
                if (effect == RegionEffectType.JUMP)
                {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 2));
                }
            }
        }
    }

    public void unapplyEffects(Player player)
    {

    }

    public List<RegionWrapper> convertList(List<String> regionsList)
    {
        List<RegionWrapper> regionsListt = new ArrayList<>();
        for (String region : regionsList)
        {
            if (getRegionWrapperByName(region) != null)
                regionsListt.add(getRegionWrapperByName(region));
        }
        return regionsListt;
    }

    private RegionWrapper getRegionWrapperByName(String regionName)
    {
        for (RegionWrapper regionWrapper : regions)
        {
            if (regionWrapper.getRegionName().equalsIgnoreCase(regionName))
            {
                return regionWrapper;
            }
        }
        return null;
    }

    private List<RegionEffectType> parseEffects(List<String> toConvert)
    {
        List<RegionEffectType> toReturnTypes = new ArrayList<>();

        for (String effect : toConvert)
        {
            toReturnTypes.add(RegionEffectType.valueOf(effect));
        }

        return toReturnTypes;
    }


}
