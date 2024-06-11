package com.accbdd.complicated_bees.genetics;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.function.Supplier;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

//custom registry for species
public class SpeciesRegistry {
    public static final ResourceKey<Registry<Species>> SPECIES_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(MODID, "species"));
    public static final Registry<Species> SPECIES_REGISTRY = new RegistryBuilder<>(SPECIES_REGISTRY_KEY)
            .create();

    public static final DeferredRegister<Species> SPECIES = DeferredRegister.create(SPECIES_REGISTRY, "builtin");

    public static final Supplier<Species> FOREST = SPECIES.register("forest", () -> new Species(0x03FCE8));
    public static final Supplier<Species> MEADOWS = SPECIES.register("meadows", () -> new Species(0xFC030F));

}
