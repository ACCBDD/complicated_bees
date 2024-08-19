package com.accbdd.complicated_bees.registry;

import com.accbdd.complicated_bees.genetics.GeneticHelper;
import com.accbdd.complicated_bees.genetics.Species;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

//custom registry for species
public class SpeciesRegistration {
    public static final ResourceKey<Registry<Species>> SPECIES_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(MODID, "species"));

    public static Species getFromResourceLocation(ResourceLocation resourceLocation) {
        return GeneticHelper.getRegistryAccess().registry(SPECIES_REGISTRY_KEY).get().get(resourceLocation);
    }

    public static ResourceLocation getResourceLocation(Species species) {
        return GeneticHelper.getRegistryAccess().registry(SPECIES_REGISTRY_KEY).get().getKey(species);
    }
}
