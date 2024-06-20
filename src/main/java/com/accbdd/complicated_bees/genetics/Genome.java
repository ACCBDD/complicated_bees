package com.accbdd.complicated_bees.genetics;

import com.accbdd.complicated_bees.genetics.gene.Gene;
import com.accbdd.complicated_bees.registry.GeneRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Genome {
    public static final String GENOME_TAG = "genome";

    private Map<ResourceLocation, Gene> genes;

    public Genome() {
        this.genes = new HashMap<>();
    }

    public Genome(Species species) {
        //TODO: IMPLEMENT
    }

    public Genome(Map<ResourceLocation, Gene> genes) {
        this.genes = genes;
    }

    public Map<ResourceLocation, Gene> getGenes() {
        return genes;
    }

    public void setGenes(Map<ResourceLocation, Gene> genes) {
        this.genes = genes;
    }

    public Gene getGene(ResourceLocation id) {
        return this.genes.getOrDefault(id, null);
    }

    public Genome setGene(ResourceLocation id, Gene gene) {
        this.genes.put(id, gene);
        return this;
    }

    public static CompoundTag serialize(Genome genome) {
        CompoundTag tag = new CompoundTag();
        Map<ResourceLocation, Gene> genes = genome.getGenes();
        for (ResourceLocation key : genes.keySet()) {
            tag.put(key.toString(), genes.get(key).serialize());
        }
        return tag;
    }

    public static Genome deserialize(CompoundTag tag) {
        Map<ResourceLocation, Gene> genes = new HashMap<>();
        for (Map.Entry<ResourceKey<Gene>, Gene> entry: GeneRegistry.GENE_REGISTRY.entrySet()) {
            ResourceLocation id = entry.getKey().location();
            Tag geneData = tag.get(id.toString());
            if (geneData != null) {
                genes.put(id, Objects.requireNonNull(GeneRegistry.GENE_REGISTRY.get(id)).deserialize(geneData));
            }
        }
        return new Genome(genes);
    }
}
