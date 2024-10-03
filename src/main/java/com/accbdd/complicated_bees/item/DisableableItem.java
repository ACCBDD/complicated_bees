package com.accbdd.complicated_bees.item;

import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;

public class DisableableItem extends Item {
    private final ForgeConfigSpec.ConfigValue<Boolean> configValue;

    public DisableableItem(Properties pProperties, ForgeConfigSpec.ConfigValue<Boolean> configValue) {
        super(pProperties);
        this.configValue = configValue;
    }

    @Override
    public boolean isEnabled(FeatureFlagSet pEnabledFeatures) {
        return configValue.get();
    }
}
