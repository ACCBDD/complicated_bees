package com.accbdd.complicated_bees.genetics;

import com.accbdd.complicated_bees.ComplicatedBees;
import com.accbdd.complicated_bees.genetics.gene.Gene;
import com.accbdd.complicated_bees.genetics.gene.IGene;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Chromosome {
    private Map<ResourceLocation, IGene<?>> genes;

    public Chromosome() {
        this.genes = new HashMap<>();
        if (ComplicatedBees.GENE_REGISTRY.get() != null)
            for (Map.Entry<ResourceKey<IGene<?>>, IGene<?>> entry : ComplicatedBees.GENE_REGISTRY.get().getEntries()) {
                genes.put(entry.getKey().location(), ComplicatedBees.GENE_REGISTRY.get().getValue(entry.getKey().location()));
            }
    }

    public Chromosome(CompoundTag genomeAsTag) {
        this.genes = new HashMap<>();
        for (Map.Entry<ResourceKey<IGene<?>>, IGene<?>> entry : ComplicatedBees.GENE_REGISTRY.get().getEntries()) {
            ResourceLocation geneLocation = entry.getKey().location();
            if (genomeAsTag.contains(geneLocation.toString())) {
                CompoundTag serializedGene = genomeAsTag.getCompound(geneLocation.toString());
                if (!serializedGene.contains(Gene.DOMINANT))
                    serializedGene.putBoolean(Gene.DOMINANT, true);
                genes.put(geneLocation, entry.getValue().deserialize(serializedGene));
            } else {
                genes.put(entry.getKey().location(), ComplicatedBees.GENE_REGISTRY.get().getValue(entry.getKey().location()));
            }
        }
    }

    public Chromosome(Species species) {
        this.genes = species.getDefaultChromosome().getGenes();
    }

    public Chromosome(Map<ResourceLocation, IGene<?>> genes) {
        this.genes = genes;
    }

    public Map<ResourceLocation, IGene<?>> getGenes() {
        return genes;
    }

    public Chromosome copy() {
        return new Chromosome(this.serialize());
    }

    public Chromosome setGenes(Map<ResourceLocation, IGene<?>> genes) {
        this.genes = genes;
        return this;
    }

    public IGene<?> getGene(ResourceLocation id) {
        return this.genes.getOrDefault(id, ComplicatedBees.GENE_REGISTRY.get().getValue(id));
    }

    public Chromosome setGene(ResourceLocation id, IGene<?> gene) {
        this.genes.put(id, gene);
        return this;
    }

    public Chromosome removeGene(ResourceLocation id) {
        this.genes.remove(id);
        return this;
    }

    public CompoundTag serialize() {
        CompoundTag tag = new CompoundTag();
        Map<ResourceLocation, IGene<?>> genes = this.getGenes();
        for (ResourceLocation key : genes.keySet()) {
            tag.put(key.toString(), genes.get(key).serialize());
        }
        return tag;
    }

    public static Chromosome deserialize(CompoundTag tag) {
        Map<ResourceLocation, IGene<?>> genes = new HashMap<>();
        for (Map.Entry<ResourceKey<IGene<?>>, IGene<?>> entry : ComplicatedBees.GENE_REGISTRY.get().getEntries()) {
            ResourceLocation id = entry.getKey().location();
            CompoundTag geneData = tag.getCompound(id.toString());
            if (!geneData.equals(new CompoundTag())) {
                if (!geneData.contains(Gene.DOMINANT))
                    geneData.putBoolean(Gene.DOMINANT, true);
                genes.put(id, Objects.requireNonNull(ComplicatedBees.GENE_REGISTRY.get().getValue(id)).deserialize(geneData));

            }
        }
        return new Chromosome(genes);
    }
}
