package com.accbdd.complicated_bees.item;

import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.ModConfigSpec;

public class DisableableItem extends Item {
    private final ModConfigSpec.ConfigValue<Boolean> configValue;

    public DisableableItem(Properties pProperties, ModConfigSpec.ConfigValue<Boolean> configValue) {
        super(pProperties);
        this.configValue = configValue;
    }

    @Override
    public boolean isEnabled(FeatureFlagSet pEnabledFeatures) {
        return configValue.get();
    }
}
