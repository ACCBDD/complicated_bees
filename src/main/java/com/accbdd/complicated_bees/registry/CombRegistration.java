package com.accbdd.complicated_bees.registry;

import com.accbdd.complicated_bees.genetics.Comb;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class CombRegistration {
    public static final ResourceKey<Registry<Comb>> COMB_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(MODID, "comb"));
}
