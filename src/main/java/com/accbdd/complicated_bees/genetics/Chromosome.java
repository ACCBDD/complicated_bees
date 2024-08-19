package com.accbdd.complicated_bees.genetics;

import com.accbdd.complicated_bees.genetics.gene.IGene;
import com.accbdd.complicated_bees.registry.GeneRegistration;
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
        for (Map.Entry<ResourceKey<IGene<?>>, IGene<?>> entry : GeneRegistration.GENE_REGISTRY.entrySet()) {
            genes.put(entry.getKey().location(), GeneRegistration.GENE_REGISTRY.get(entry.getKey()));
        }
    }

    public Chromosome(CompoundTag genomeAsTag) {
        this.genes = new HashMap<>();
        for (Map.Entry<ResourceKey<IGene<?>>, IGene<?>> entry : GeneRegistration.GENE_REGISTRY.entrySet()) {
            ResourceLocation geneLocation = entry.getKey().location();
            if (genomeAsTag.contains(geneLocation.toString())) {
                genes.put(geneLocation, entry.getValue().deserialize(genomeAsTag.getCompound(geneLocation.toString())));
            } else {
                genes.put(entry.getKey().location(), GeneRegistration.GENE_REGISTRY.get(entry.getKey()));
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
        return new Chromosome(this.genes);
    }

    public Chromosome setGenes(Map<ResourceLocation, IGene<?>> genes) {
        this.genes = genes;
        return this;
    }

    public IGene<?> getGene(ResourceLocation id) {
        return this.genes.getOrDefault(id, GeneRegistration.GENE_REGISTRY.get(id));
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
        for (Map.Entry<ResourceKey<IGene<?>>, IGene<?>> entry : GeneRegistration.GENE_REGISTRY.entrySet()) {
            ResourceLocation id = entry.getKey().location();
            CompoundTag geneData = tag.getCompound(id.toString());
            if (!geneData.equals(new CompoundTag())) {
                genes.put(id, Objects.requireNonNull(GeneRegistration.GENE_REGISTRY.get(id)).deserialize(geneData));
            }
        }
        return new Chromosome(genes);
    }
}
