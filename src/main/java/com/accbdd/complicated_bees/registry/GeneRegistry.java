package com.accbdd.complicated_bees.registry;

import com.accbdd.complicated_bees.genetics.gene.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.function.Supplier;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class GeneRegistry {
    public static final ResourceKey<Registry<IGene<?>>> GENE_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(MODID, "gene"));
    public static final Registry<IGene<?>> GENE_REGISTRY = new RegistryBuilder<>(GENE_REGISTRY_KEY)
            .create();

    //every registered gene should be registered as a 'default' value
    public static final DeferredRegister<IGene<?>> GENES = DeferredRegister.create(GENE_REGISTRY, MODID);
    public static final Supplier<GeneSpecies> SPECIES = GENES.register(GeneSpecies.TAG, GeneSpecies::new);
    public static final Supplier<GeneLifespan> LIFESPAN = GENES.register(GeneLifespan.TAG, GeneLifespan::new);
    public static final Supplier<GeneTemperature> TEMPERATURE = GENES.register(GeneTemperature.TAG, GeneTemperature::new);
    public static final Supplier<GeneHumidity> HUMIDITY = GENES.register(GeneHumidity.TAG, GeneHumidity::new);
    public static final Supplier<GeneFlower> FLOWER = GENES.register(GeneFlower.TAG, GeneFlower::new);
    public static final Supplier<GeneFertility> FERTILITY = GENES.register(GeneFertility.TAG, GeneFertility::new);
    public static final Supplier<GeneProductivity> PRODUCTIVITY = GENES.register(GeneProductivity.TAG, GeneProductivity::new);

    public static final Supplier<GeneBoolean> DIURNAL = GENES.register("diurnal", () -> new GeneBoolean(true, true));
    public static final Supplier<GeneBoolean> NOCTURNAL = GENES.register("nocturnal", () -> new GeneBoolean(false, true));
    public static final Supplier<GeneBoolean> CAVE_DWELLING = GENES.register("cave_dwelling", () -> new GeneBoolean(false, true));
    public static final Supplier<GeneBoolean> WEATHERPROOF = GENES.register("weatherproof", () -> new GeneBoolean(false, true));
}
