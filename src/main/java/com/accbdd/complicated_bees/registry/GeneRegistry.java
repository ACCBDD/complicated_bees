package com.accbdd.complicated_bees.registry;

import com.accbdd.complicated_bees.genetics.gene.Gene;
import com.accbdd.complicated_bees.genetics.gene.GeneLifespan;
import com.accbdd.complicated_bees.genetics.gene.GeneSpecies;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.function.Supplier;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class GeneRegistry {
    public static final ResourceKey<Registry<Gene>> GENE_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(MODID, "gene"));
    public static final Registry<Gene> GENE_REGISTRY = new RegistryBuilder<>(GENE_REGISTRY_KEY)
            .create();

    //every registered gene should be registered as a 'default' value
    public static final DeferredRegister<Gene> GENES = DeferredRegister.create(GENE_REGISTRY, MODID);
    public static final Supplier<GeneSpecies> SPECIES_GENE = GENES.register(GeneSpecies.TAG, () -> new GeneSpecies());
    public static final Supplier<GeneLifespan> LIFESPAN_GENE = GENES.register(GeneLifespan.TAG, () -> new GeneLifespan());
}
