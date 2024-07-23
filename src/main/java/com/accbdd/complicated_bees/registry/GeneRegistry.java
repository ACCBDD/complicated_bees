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
    public static final ResourceKey<Registry<Gene<?>>> GENE_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(MODID, "gene"));
    public static final Registry<Gene<?>> GENE_REGISTRY = new RegistryBuilder<>(GENE_REGISTRY_KEY)
            .create();

    //every registered gene should be registered as a 'default' value
    public static final DeferredRegister<Gene<?>> GENES = DeferredRegister.create(GENE_REGISTRY, MODID);
    public static final Supplier<GeneSpecies> SPECIES_GENE = GENES.register(GeneSpecies.TAG, GeneSpecies::new);
    public static final Supplier<GeneLifespan> LIFESPAN_GENE = GENES.register(GeneLifespan.TAG, GeneLifespan::new);
    public static final Supplier<GeneTemperature> TEMPERATURE_GENE = GENES.register(GeneTemperature.TAG, GeneTemperature::new);
    public static final Supplier<GeneHumidity> HUMIDITY_GENE = GENES.register(GeneHumidity.TAG, GeneHumidity::new);
    public static final Supplier<GeneFlower> FLOWER_GENE = GENES.register(GeneFlower.TAG, GeneFlower::new);
    public static final Supplier<GeneFertility> FERTILITY_GENE = GENES.register(GeneFertility.TAG, GeneFertility::new);
    public static final Supplier<GeneProductivity> PRODUCTIVITY_GENE = GENES.register(GeneProductivity.TAG, GeneProductivity::new);
}
